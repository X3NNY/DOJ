package pers.dreamer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.dreamer.bean.Paste;
import pers.dreamer.bean.User;
import pers.dreamer.service.PasteService;
import pers.dreamer.service.UserService;
import pers.dreamer.util.idworker.Sid;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class BetaController {

    @Autowired
    PasteService pasteService;

    @Autowired
    UserService userService;

    @Autowired
    Sid sid;

    @PostMapping("/beta/paste")
    public Map<String,Object> insertPasteCode(@RequestBody Paste paste, HttpSession session) {
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            ret.put("state",1);
        } else {
            String id = sid.nextShort();
            paste.setUid(user.getUid());
            paste.setDate(new Date());
            paste.setId(id);
            pasteService.insertPaste(paste);
            ret.put("state",0);
            ret.put("id",id);
        }
        return ret;
    }

    @GetMapping("/beta/paste/getcode")
    public Map<String,Object> getPasteCode(String id) {
        Map<String,Object> ret = new HashMap<>();
        Paste paste = pasteService.findPasteById(id);
        if (paste == null) {
            ret.put("state",1);
        } else {
            ret.put("state",0);
            ret.put("code",paste.getCode());
            ret.put("date",paste.getDate());
            User u = userService.findUserByUid(paste.getUid());
            ret.put("username",u.getUsername());
        }
        return ret;
    }
}
