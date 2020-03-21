package pers.dreamer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.dreamer.bean.About;
import pers.dreamer.bean.User;
import pers.dreamer.dto.AboutDto;
import pers.dreamer.service.AboutService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class AboutController {

    @Autowired
    AboutService aboutService;

    @GetMapping("/api/index/blog")
    public Map<String,Integer> getIndexBlog() {
        Map<String,Integer> ret = new HashMap<>();
        About about = aboutService.findAboutByName("index_blog_id");
        ret.put("bid",about.getText().length()==0?1:Integer.parseInt(about.getText()));
        return ret;
    }

    @GetMapping("/api/about/{index}")
    public Map<String,Object> findAboutByIndex(@PathVariable("index")String index){
        Map<String,Object> ret = new HashMap<>();
        About about = aboutService.findAboutByName(index);
        ret.put("msg",about != null ? about.getText() : "");
        return ret;
    }

    @PutMapping("/api/about/edit")
    public Map<String,Object> updateAboutByIndex(@RequestBody AboutDto aboutDto, HttpSession session){
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if(user == null || user.getRole() < 6) ret.put("state",1);
        else {
            About about = new About();
            about.setName("index_blog_id");
            about.setText(aboutDto.getBlogid().toString());
            aboutService.updateAboutByName(about);

            about = new About();
            about.setName("notify");
            about.setText(aboutDto.getNotice());
            aboutService.updateAboutByName(about);

            about = new About();
            about.setName("index_thing");
            about.setText(aboutDto.getThing());
            aboutService.updateAboutByName(about);
            ret.put("state",0);
        }
        return ret;
    }
}
