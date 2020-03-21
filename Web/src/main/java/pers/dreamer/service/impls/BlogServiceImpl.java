package pers.dreamer.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.dreamer.bean.*;
import pers.dreamer.dao.BlogMapper;
import pers.dreamer.dao.BlogcollectionMapper;
import pers.dreamer.dao.BlogcommentsMapper;
import pers.dreamer.dao.BlogvoteMapper;
import pers.dreamer.service.BlogService;
import pers.dreamer.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    UserService userService;

    @Autowired
    BlogcollectionMapper blogcollectionMapper;

    @Autowired
    BlogvoteMapper blogvoteMapper;

    @Autowired
    BlogcommentsMapper blogcommentsMapper;

    @Override
    public List<Blog> findBlogsByPgeAndNameAndSatr(Integer page, Integer limitSize, String name, Integer star,Integer uid) {
        BlogExample blogExample = pattern1(name,star,uid);
        if(page!=null) blogExample.setPage(page*limitSize);
        blogExample.setLimitSize(limitSize);
        blogExample.setOrderByClause("date desc");
        return blogMapper.selectByExample(blogExample);
    }

    @Override
    public List<Blog> findBlogsByProblem(String s) {
        BlogExample blogExample = new BlogExample();
        blogExample.createCriteria().andTitleLike(s+"%");
        return blogMapper.selectByExample(blogExample);
    }

    @Override
    public List<Blogcollection> findBlogCollectionsByUid(Integer uid) {
        if(uid == null) return null;
        BlogcollectionExample blogcollectionExample = new BlogcollectionExample();
        blogcollectionExample.createCriteria().andUidEqualTo(uid);
        return blogcollectionMapper.selectByExample(blogcollectionExample);
    }

    @Override
    public Long findBlogsTotalByNameAndSatr(String name, Integer star, Integer uid) {
        return blogMapper.countByExample(pattern1(name,star,uid));
    }

    @Override
    public Integer findMyVoteByBidAndUid(Integer bid, Integer uid) {
        if(uid == null||bid==null) return 0;
        Integer vote = blogvoteMapper.findVoteByBidAndUid(bid,uid);
        return vote==null?0:vote;
    }

    @Override
    public Blog findBlogByBid(Integer bid) {
        if(bid == null) return null;
        return blogMapper.selectByPrimaryKey(bid);
    }

    @Override
    public List<Blogcomments> findBlogCommentsByBid(Integer bid) {
        return blogcommentsMapper.selectByExample(pattern2(bid));
    }

    @Override
    public Integer findMyVoteByCommentIdAndUid(Integer id, Integer uid) {
        if(uid==null) return 0;
        BlogvoteExample blogvoteExample = new BlogvoteExample();
        blogvoteExample.createCriteria().andCommentidEqualTo(id).andUidEqualTo(uid);
        List<Blogvote> blogvotes = blogvoteMapper.selectByExample(blogvoteExample);
        return blogvotes.size() > 0 ?blogvotes.get(0).getVote():0;
    }

    @Transactional
    @Override
    public void insertBlog(Blog blog) {
        blogMapper.insertSelective(blog);
    }

    @Transactional
    @Override
    public boolean insertBlogVote(Blogvote blogvote) {
        if(blogvote.getUid() == null || blogvote.getBid() == null) return false;
        BlogvoteExample blogvoteExample = new BlogvoteExample();
        BlogvoteExample.Criteria criteria = blogvoteExample.createCriteria().andUidEqualTo(blogvote.getUid());
        if(blogvote.getBid()!=null) criteria.andBidEqualTo(blogvote.getBid());
        criteria.andCommentidEqualTo(blogvote.getCommentid());
        List<Blogvote> blogvotes = blogvoteMapper.selectByExample(blogvoteExample);
//        for (Blogvote blogvote1:blogvotes) System.out.println(blogvote1.toString());
        if(blogvotes.size() > 0) return false;
        blogvoteMapper.insertSelective(blogvote);
        return true;
    }

    @Transactional
    @Override
    public Integer insertBlogReply(Integer bid, Integer cid,Integer uid, String text) {
        Blogcomments blogcomments = new Blogcomments();
        blogcomments.setBid(bid);
        blogcomments.setPreid((cid!=null&&cid>0)?cid:-1);
        blogcomments.setText(text);
        blogcomments.setUid(uid);
        blogcommentsMapper.insertSelective(blogcomments);
        return blogcomments.getId();
    }

    @Transactional
    @Override
    public void deleteBlogByBid(Integer bid) {
        blogMapper.deleteByPrimaryKey(bid);
        BlogcommentsExample example = new BlogcommentsExample();
        example.createCriteria().andBidEqualTo(bid);
        blogcommentsMapper.deleteByExample(example);
        BlogvoteExample example1 = new BlogvoteExample();
        example1.createCriteria().andBidEqualTo(bid);
        blogvoteMapper.deleteByExample(example1);
        BlogcollectionExample example2 = new BlogcollectionExample();
        example2.createCriteria().andBidEqualTo(bid);
        blogcollectionMapper.deleteByExample(example2);
    }

    @Transactional
    @Override
    public void deleteCommentById(Integer cid) {
        BlogcommentsExample blogcommentsExample = new BlogcommentsExample();
        blogcommentsExample.createCriteria().andPreidEqualTo(cid);
        blogcommentsMapper.deleteByPrimaryKey(cid);
        blogcommentsMapper.deleteByExample(blogcommentsExample);
        BlogvoteExample example = new BlogvoteExample();
        example.createCriteria().andCommentidEqualTo(cid);
        blogvoteMapper.deleteByExample(example);
    }

    @Override
    public List<Blog> findBlogByState(int i) {
        BlogExample blogExample = new BlogExample();
        blogExample.setOrderByClause("date desc");
        blogExample.createCriteria().andStateEqualTo(1);
        return blogMapper.selectByExample(blogExample);
    }

    @Override
    public Blogcomments findBlogCommentsById(Integer id) {
        return blogcommentsMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public void updateBlogComments(Blogcomments blogcomments) {
        blogcommentsMapper.updateByPrimaryKeySelective(blogcomments);
    }

    @Transactional
    @Override
    public void updateBlog(Blog blog) {
        blogMapper.updateByPrimaryKeySelective(blog);
    }

    private BlogcommentsExample pattern2(Integer bid){
        BlogcommentsExample blogcommentsExample = new BlogcommentsExample();
        blogcommentsExample.createCriteria().andBidEqualTo(bid);
        return blogcommentsExample;
    }

    private boolean validNumber(String name) {
        if(name==null||name.equals("")) return  false;
        for(int i = 0;i < name.length();i++) if(!Character.isDigit(name.charAt(i))) return false;
        return name.length() > 0;
    }

    @Override
    public Blogcollection findBlogCollectionsByBidAndUid(Integer bid, Integer uid) {
        BlogcollectionExample example = new BlogcollectionExample();
        example.createCriteria().andBidEqualTo(bid).andUidEqualTo(uid);
        List<Blogcollection> blogcollections = blogcollectionMapper.selectByExample(example);
        return blogcollections.isEmpty()?null:blogcollections.get(0);
    }

    @Override
    public Boolean findBlogCollectionsStatusByBidAndUid(Integer bid, Integer uid) {
        BlogcollectionExample example = new BlogcollectionExample();
        example.createCriteria().andBidEqualTo(bid).andUidEqualTo(uid);
        return blogcollectionMapper.countByExample(example)>0;
    }

    @Override
    public Boolean isExistBlogByBid(Integer bid) {
        BlogExample example = new BlogExample();
        example.createCriteria().andBidEqualTo(bid);
        return blogMapper.countByExample(example)>0;
    }

    @Transactional
    @Override
    public void insertBlogcollection(Blogcollection blogcollection) {
        blogcollectionMapper.insertSelective(blogcollection);
    }

    @Transactional
    @Override
    public void deleteBlogcollection(Blogcollection blogcollection) {
        blogcollectionMapper.deleteByPrimaryKey(blogcollection.getId());
    }

    private BlogExample pattern1(String name, Integer star, Integer uid){
        BlogExample blogExample = new BlogExample();
        if(name != null && name.length() != 0){
            Integer u = userService.findUidByUsername(name);
            if(u != null) blogExample.or().andUidEqualTo(u).andStateEqualTo(0);
            if(validNumber(name)) blogExample.or().andBidEqualTo(Integer.parseInt(name)).andStateEqualTo(0);
            blogExample.or().andTitleLike("%"+name+"%").andStateEqualTo(0);
        }
        if(star != null && star == 1 && uid != null){
            List<Integer> bids = findBlogCollectionsIdByUid(uid);
            if(bids.size() > 0) blogExample.createCriteria().andBidIn(bids).andStateEqualTo(0);
        }
        return blogExample;
    }

    private List<Integer> findBlogCollectionsIdByUid(Integer uid) {
        return blogcollectionMapper.findBidByUid(uid);
    }
}
