package pers.dreamer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pers.dreamer.bean.Blog;
import pers.dreamer.bean.User;
import pers.dreamer.bean.Wiki;
import pers.dreamer.service.BlogService;
import pers.dreamer.service.UserService;
import pers.dreamer.service.WikiService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class WikiController {
    @Autowired
    UserService userService;

    @Autowired
    WikiService wikiService;

    @Autowired
    BlogService blogService;

    @GetMapping("/api/wiki")
    public List<Map<String,String>> getWiki() {
        List<Map<String,String>> ret = new ArrayList<>();
        List<Wiki> wikis = wikiService.findWikiByPreId(-1);
        for (Wiki wiki:wikis) {
            Map<String,String> tmp = new HashMap<>();
            tmp.put("name",wiki.getName());
            tmp.put("title",wiki.getTitle());
            tmp.put("desc",wiki.getDescr());
            ret.add(tmp);
        }
        return ret;
    }

    @GetMapping("/api/wiki/{name}")
    public List<Map<String,Object>> getWikiByName(@PathVariable("name")String name, HttpSession session) {
        List<Map<String,Object>> ret = new ArrayList<>();
        Wiki w = wikiService.findWikiByName(name);
        User u = (User) session.getAttribute("user");
        List<Wiki> wikis = wikiService.findWikiByPreId(w.getId());
        for (Wiki wiki:wikis) {
            Map<String,Object> tmp = new HashMap<>();
            if (wiki.getBid() != -1) {
                tmp.put("bid", wiki.getBid());
                Blog blog = blogService.findBlogByBid(wiki.getBid());
                tmp.put("up_vote",blog.getUpVote());
                tmp.put("down_vote",blog.getDownVote());
                tmp.put("state",blogService.findMyVoteByBidAndUid(blog.getBid(),u==null?null:u.getUid()));
            }
            tmp.put("title",wiki.getTitle());
            tmp.put("desc",wiki.getDescr());
            tmp.put("name",wiki.getName());
            ret.add(tmp);
        }
        return ret;
    }
}
