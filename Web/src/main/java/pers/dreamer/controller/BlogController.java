package pers.dreamer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.dreamer.bean.*;
import pers.dreamer.dto.BlogReplyDto;
import pers.dreamer.service.AchievementService;
import pers.dreamer.service.BlogService;
import pers.dreamer.service.MessageService;
import pers.dreamer.service.UserService;
import pers.dreamer.util.UserUtil;
import pers.dreamer.util.idworker.Sid;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

import static java.lang.Math.max;

@RestController
@CrossOrigin
public class BlogController {

    @Autowired
    BlogService blogService;

    @Autowired
    UserService userService;

    @Autowired
    DateFormat dateFormat;

    @Autowired
    MessageService messageService;

    @Autowired
    AchievementService achievementService;

    @Autowired
    Sid sid;

    @GetMapping("/api/blog/toindex")
    public Map<String,Object> findBlogsToindex(){
        Map<String,Object> ret = new HashMap<>();
        List<Blog> blogs = blogService.findBlogsByPgeAndNameAndSatr(0,10,null,null,null);
        List<Map<String,Object>> lists = new ArrayList<>();
        for(Blog blog : blogs){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("bid",blog.getBid());
            tmp.put("title",blog.getTitle());
            tmp.put("author", UserUtil.getNoAchiStyleNameByUid(blog.getUid(),userService));
            lists.add(tmp);
        }
        ret.put("blogset",lists);
        return ret;
    }

    @GetMapping("/api/blog/set")
    public Map<String,Object> findBlogsByPgeAndNameAndSatr(Integer page, String name, Integer star, HttpSession session){
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        List<Blog> blogs = new ArrayList<>();
        if (page == null || page <= 0) {
            page = 1;
        }
        if (page == 1 && (name==null || name.length()==0) && (star==null || star <= 0)) {
            blogs.addAll(blogService.findBlogByState(1));
        }
        int len = blogs.size();
        blogs.addAll(len<15?blogService.findBlogsByPgeAndNameAndSatr(page-1,15 - len,name,star,user != null?user.getUid():null):null);
        List<Map<String,Object>> lists = new ArrayList<>();
        boolean isUserNull = user == null;
        for(Blog blog : blogs){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("bid",blog.getBid());
            tmp.put("title",blog.getTitle());
            tmp.put("author", UserUtil.getStyleNameByUid(blog.getUid(),userService,achievementService));
            tmp.put("date",dateFormat.format(blog.getDate()));
            tmp.put("up_vote",blog.getUpVote());
            tmp.put("down_vote",blog.getDownVote());
            tmp.put("state",blog.getState());
            tmp.put("my_vote",isUserNull?0:blogService.findMyVoteByBidAndUid(blog.getBid(),user.getUid()));
            lists.add(tmp);
        }
        ret.put("pages",len < 15?(blogService.findBlogsTotalByNameAndSatr(name,star,isUserNull?null:user.getUid())+14)/15:1);
        ret.put("blogset",lists);
        return ret;
    }

    @PostMapping("/api/blog/reply")
    public Map<String,Object> blogReply(@RequestBody BlogReplyDto replyDto,Integer bid,Integer cid, HttpSession session) {
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null) ret.put("state",1);
        else {
            userService.addLivenessAndLevelByUid(user.getUid(),5);
            int rcid = blogService.insertBlogReply(bid,cid,user.getUid(),replyDto.getText());

            Blog blog = blogService.findBlogByBid(bid);
            if (!user.getUid().equals(blog.getUid())) {
                Message message = new Message();
                message.setStatus(0);
                message.setDate(new Date());
                message.setSenduid(-2);
                if (cid == null || cid <= 0) {
                    message.setTargetuid(blog.getUid());
                    message.setTitle("博客评论提醒");
                    message.setContent("您的博客<a href=\"blog.jsp?bid=" + bid + "#" + (rcid) + "\">" + blog.getTitle() + "</a>中有新评论了，请点击查看");
                } else {
                    Blogcomments blogcomments = blogService.findBlogCommentsById(cid);
                    message.setTargetuid(blogcomments.getUid());
                    message.setTitle("评论回复提醒");
                    message.setContent("您在博客<a href=\"blog.jsp?bid=" + bid + "#" + cid + "\">" + blog.getTitle() + "</a>中的评论有人回复了，请点击查看");
                }
                messageService.insertMessage(message);
            }
            if (cid == null || cid <= 0) {
                blog.setNum(blog.getNum() + 1);
                blogService.updateBlog(blog);
            }
            ret.put("state",0);
        }
        return ret;
    }

    @PostMapping("/api/blog/edit/{bid}")
    public Map<String,Integer> blogEdit(@RequestBody Blog blog,@PathVariable("bid") Integer bid, HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Blog blog1 = blogService.findBlogByBid(bid);
        if (user == null || !blog1.getUid().equals(user.getUid())) {
            ret.put("state",1);
        } else {
            blog1.setText(blog.getText());
            blog1.setTitle(blog.getTitle());
            blogService.updateBlog(blog1);
            ret.put("state",0);
        }
        return ret;
    }

    @PostMapping("/api/blog/comment/edit")
    public Map<String,Integer> blogCEdit(@RequestBody BlogReplyDto replyDto, Integer cid, HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Blogcomments blogcomments = blogService.findBlogCommentsById(cid);
        if (user == null || !blogcomments.getUid().equals(user.getUid())) {
            ret.put("state",1);
        } else {
            blogcomments.setText(replyDto.getText());
            blogService.updateBlogComments(blogcomments);
            ret.put("state",0);
        }
        return ret;
    }

    @PostMapping("/api/blog/delete")
    public Map<String,Object> deleteBlogOrComment(Integer bid,Integer cid,HttpSession session) {
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        ret.put("state",0);
        if (cid == null || cid <= 0) {
            Blog blog = blogService.findBlogByBid(bid);
            if (user == null || !blog.getUid().equals(user.getUid())) {
                ret.put("state",1);
            } else {
                blogService.deleteBlogByBid(bid);
            }
        } else {
            Blogcomments blogcomments = blogService.findBlogCommentsById(cid);
            if (user == null || !blogcomments.getUid().equals(user.getUid())) {
                ret.put("state",1);
            } else {
                blogService.deleteCommentById(cid);
                Blog blog = blogService.findBlogByBid(bid);
                blog.setNum(blog.getNum()-1);
                blogService.updateBlog(blog);
            }
        }
        return ret;
    }

    @GetMapping("/api/blog/info/{bid}")
    public Map<String,Object> blogInfo(@PathVariable("bid")Integer bid,HttpSession session){
        Map<String,Object> ret = new HashMap<>();
        Blog blog = blogService.findBlogByBid(bid);
        User u = (User) session.getAttribute("user");
        User user = userService.findUserByUid(blog.getUid());
        if (u !=null && u.getUid().equals(blog.getUid())) {
            ret.put("is_author",1);
        } else {
            ret.put("is_author",0);
        }
        ret.put("title", blog.getTitle());
        ret.put("text",blog.getText());
        ret.put("iscollect",false);
        ret.put("username",UserUtil.getStyleNameByUser(user,achievementService));
        if (u != null && blogService.findBlogCollectionsStatusByBidAndUid(bid,u.getUid())) {
            ret.put("iscollect",true);
        }
        ret.put("image",user!=null?user.getImage():null);
        ret.put("date",dateFormat.format(blog.getDate()));
        ret.put("up_vote",blog.getUpVote());
        ret.put("down_vote",blog.getDownVote());
        ret.put("my_vote",u != null?blogService.findMyVoteByBidAndUid(blog.getBid(),u.getUid()):0);
        ret.put("num",blog.getNum());
        return ret;
    }

    @GetMapping("/api/blog/comments/{bid}")
    public Set<Map<String,Object>> blogcommentsByBid(@PathVariable("bid") Integer bid, HttpSession session){
        Set<Map<String,Object>> ret = new TreeSet<>(Comparator.comparing(o -> ((String) o.get("date"))));
        User user = (User) session.getAttribute("user");
        List<Blogcomments> blogcomments = blogService.findBlogCommentsByBid(bid);
        for(Blogcomments blogcomment : blogcomments){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("cid",blogcomment.getId());
            User u = userService.findUserByUid(blogcomment.getUid());
            if (user != null && user.getUid().equals(u.getUid())) {
                tmp.put("is_author",1);
            } else {
                tmp.put("is_author",0);
            }
            tmp.put("username",UserUtil.getStyleNameByUser(u,achievementService));
            tmp.put("image",u!=null?u.getImage():null);
            tmp.put("precid",blogcomment.getPreid());
            tmp.put("text",blogcomment.getText());
            tmp.put("date",dateFormat.format(blogcomment.getDate()));
            tmp.put("up_vote",blogcomment.getUpVote());
            tmp.put("down_vote",blogcomment.getDownVote());
            tmp.put("my_vote",user!=null?blogService.findMyVoteByCommentIdAndUid(blogcomment.getId(),user.getUid()):0);
            ret.add(tmp);
        }
        return ret;
    }

    @PostMapping("/api/blog/upvote")
    public Map<String,Object> upVoteOnByBid(Integer bid,HttpSession session){
        Map<String,Object> ret= new HashMap<>();
        ret.put("state",0);
        User user = (User) session.getAttribute("user");
        if(user ==null) ret.put("state",1);
        else {
            Blogvote blogvote = new Blogvote();
            blogvote.setCommentid(-1);
            blogvote.setBid(bid);
            blogvote.setUid(user.getUid());
            blogvote.setCreatetime(new Date());
            blogvote.setVote(1);
            if(!blogService.insertBlogVote(blogvote)) {
                ret.put("state",1);
            } else {
                Blog blog = blogService.findBlogByBid(bid);
                blog.setUpVote(blog.getUpVote() + 1);
                blogService.updateBlog(blog);
                user = userService.findUserByUid(blog.getUid());
                user.setLiveness(user.getLiveness()+5);
                user.setLevel(user.getLevel()+5);
                userService.updateUserInfo(user);
            }
        }
        return ret;
    }

    @PostMapping("/api/blog/downvote")
    public Map<String,Object> downVoteOnByBid(Integer bid,HttpSession session){
        Map<String,Object> ret= new HashMap<>();
        ret.put("state",0);
        User user = (User) session.getAttribute("user");
        if(user ==null) ret.put("state",1);
        else {
            Blogvote blogvote = new Blogvote();
            blogvote.setCommentid(-1);
            blogvote.setBid(bid);
            blogvote.setUid(user.getUid());
            blogvote.setCreatetime(new Date());
            blogvote.setVote(-1);
            if(!blogService.insertBlogVote(blogvote)) {
                ret.put("state", 1);
            } else {
                Blog blog = blogService.findBlogByBid(bid);
                blog.setDownVote(blog.getDownVote() + 1);
                blogService.updateBlog(blog);
                user = userService.findUserByUid(blog.getUid());
                user.setLiveness(max(500,user.getLiveness()-5));
                user.setLevel(max(0,user.getLevel()-5));
                userService.updateUserInfo(user);
            }
        }
        return ret;
    }

    @PostMapping("/api/blog/comments/upvote")
    public Map<String,Object> upVoteOnByCommentId(Integer bid,Integer id,HttpSession session){
        Map<String,Object> ret= new HashMap<>();
        ret.put("state",0);
        User user = (User) session.getAttribute("user");
        if(user ==null) ret.put("state",1);
        else {
            Blogvote blogvote = new Blogvote();
            blogvote.setBid(bid);
            blogvote.setCommentid(id);
            blogvote.setUid(user.getUid());
            blogvote.setCreatetime(new Date());
            blogvote.setVote(1);
            if(!blogService.insertBlogVote(blogvote)) {
                ret.put("state",1);
            } else {
                Blogcomments blogcomments = blogService.findBlogCommentsById(id);
                blogcomments.setUpVote(blogcomments.getUpVote() + 1);
                blogService.updateBlogComments(blogcomments);
            }
        }
        return ret;
    }

    @PostMapping("/api/blog/comments/downvote")
    public Map<String,Object> downVoteOnByCommentId(Integer bid,Integer id,HttpSession session){
        Map<String,Object> ret= new HashMap<>();
        ret.put("state",0);
        User user = (User) session.getAttribute("user");
        if(user ==null) ret.put("state",1);
        else {
            Blogvote blogvote = new Blogvote();
            blogvote.setBid(bid);
            blogvote.setCommentid(id);
            blogvote.setUid(user.getUid());
            blogvote.setCreatetime(new Date());
            blogvote.setVote(-1);
            if(!blogService.insertBlogVote(blogvote)) {
                ret.put("state",1);
            } else {
                Blogcomments blogcomments = blogService.findBlogCommentsById(id);
                blogcomments.setDownVote(blogcomments.getDownVote() + 1);
                blogService.updateBlogComments(blogcomments);
            }
        }
        return ret;
    }

    @PostMapping("/api/upload/img")
    public Map<String,String> uploadImg(MultipartFile file, HttpSession session){
        Map<String,String> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if(user == null) {
            ret.put("location","上传失败");
        }
        String fileName = sid.nextShort();
        String path = session.getServletContext().getRealPath("/media/img/");
        File dir = new File(path);
        if(!dir.exists()) dir.mkdirs();
        try {
            file.transferTo(new File(path+fileName+file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ret.put("location",fileName+file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.')));
        return ret;
    }

    @PostMapping("/api/blog/new")
    public Map<String,Integer> newBlog(@RequestBody Blog blog,HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getLiveness() < 1000) {
            ret.put("state",1);
        }else {
            user.setLiveness(user.getLiveness()+10);
            user.setLevel(user.getLevel()+10);
            userService.updateUserInfo(user);
            blog.setUid(user.getUid());
            blog.setUpVote(0);
            blog.setDownVote(0);
            blog.setState(0);
            blog.setDate(new Date());
            blog.setNum(0);
            blogService.insertBlog(blog);
            ret.put("state", 0);
        }
        return ret;
    }

    @GetMapping("/api/blog/getcollect")
    public List<Map<String,Object>> getBlogCollect(Integer uid) {
        List<Map<String,Object>> ret = new ArrayList<>();
        User user = userService.findUserByUid(uid);
        if (user == null) {
            return ret;
        }
        List<Blogcollection> blogcollections = blogService.findBlogCollectionsByUid(uid);
        for (Blogcollection blogcollection:blogcollections) {
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("id",blogcollection.getId());
            tmp.put("bid",blogcollection.getBid());
            Blog blog = blogService.findBlogByBid(blogcollection.getBid());
            tmp.put("title",blog.getTitle());
            tmp.put("date",blogcollection.getDate());
            ret.add(tmp);
        }
        return ret;
    }

    @PutMapping("/api/blog/collect/{bid}")
    public Map<String,Integer> blogCollect(@PathVariable("bid")Integer bid,HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null || !blogService.isExistBlogByBid(bid)) {
            ret.put("state",1);
            return ret;
        }
        Blogcollection blogcollection = blogService.findBlogCollectionsByBidAndUid(bid,user.getUid());
        if (blogcollection == null) {
            blogcollection = new Blogcollection();
            blogcollection.setBid(bid);
            blogcollection.setDate(new Date());
            blogcollection.setUid(user.getUid());
            blogService.insertBlogcollection(blogcollection);
            ret.put("state",0);
        } else {
            blogService.deleteBlogcollection(blogcollection);
            ret.put("state",2);
        }
        return ret;
    }
}
