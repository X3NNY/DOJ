package pers.dreamer.controller;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import pers.dreamer.bean.Message;
import pers.dreamer.bean.User;
import pers.dreamer.dto.MessageDto;
import pers.dreamer.service.MessageService;
import pers.dreamer.service.UserService;
import pers.dreamer.util.UserUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.*;

@RestController
@CrossOrigin
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    DateFormat dateFormat;

    @Autowired
    UserService userService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    private CorrelationData corrData=new CorrelationData(String.valueOf(System.currentTimeMillis()));

    @Async
    public void sendMsgForEmail(String content) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("desc", "邮件发送");
        messageProperties.getHeaders().put("type", "mail");
        org.springframework.amqp.core.Message message = new org.springframework.amqp.core.Message(content.getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.send("mail_topic_exchange","mail.send", message,corrData);
    }

    @PostMapping("/api/message/send")
    public Map<String,Object> sendMessage(@RequestBody MessageDto messageDto, HttpSession session) throws UnsupportedEncodingException {
        Map<String,Object> ret =new HashMap<>();
        ret.put("state",0);
        User user = (User) session.getAttribute("user");
        Message message = new Message();

        User u = null;
        try {
            int uid = -1;
            if (messageDto.getName().length() != 0) {
                uid = Integer.parseInt(messageDto.getName());
            }
            u = userService.findUserByUid(uid);
        } catch (Exception ignored) {

        }

        if (u == null) {
            u = userService.findUserByName(messageDto.getName());
        }
        if (user == null || messageDto.getName().length() == 0 || u == null) {
            ret.put("state",1);
        } else {
            message.setTargetuid(u.getUid());
            message.setTitle(messageDto.getTitle());
            message.setSenduid(user.getRole() >= 7?-1:user.getUid());
            message.setDate(new Date());
            message.setStatus(0);
            message.setContent(messageDto.getText());
            messageService.insertMessage(message);

            String content = (u.getEmail()+"|DOJ消息通知|您的好友 "+user.getUsername()+" 向您发送了信息，请登陆查看。系统自动发送，请勿回复。");
            sendMsgForEmail(content);
        }
        return ret;
    }

    @PostMapping("/api/message/reply/{mid}")
    public Map<String,Integer> replyMessage(@RequestBody Message message, @PathVariable("mid")Integer mid, HttpSession session) throws UnsupportedEncodingException {
        Map<String,Integer> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Message m = messageService.findMessagesByMid(mid);
        if (user == null || !m.getTargetuid().equals(user.getUid()) || m.getSenduid() == -1 || message.getContent().length() == 0) {
            ret.put("state",1);
        } else {
            message.setTargetuid(m.getSenduid());
            message.setSenduid(user.getUid());
            message.setStatus(0);
            message.setTitle(m.getTitle()+"的回复");
            message.setDate(new Date());
            messageService.insertMessage(message);

            User user1 = userService.findUserByUid(m.getSenduid());
            String content = (user1.getEmail()+"|DOJ消息通知|您的好友 "+user.getUsername()+" 回复了您发送的信息，请登陆查看。系统自动发送，请勿回复。");
            sendMsgForEmail(content);

            ret.put("state",0);
        }
        return ret;
    }

    @GetMapping("/api/message/receive/{tid}/{page}")
    public List<Map<String,Object>> receiveByTidAndPage(@PathVariable("tid") Integer tid,@PathVariable("page") Integer page,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null || tid == null) return null;
        List<Message> messages = messageService.findMessagesByPageAndUidAndSid(page==null||page <= 0 ? 0 : page - 1, 10, user.getUid(), tid != null&& tid==-2?null:tid);
        List<Map<String,Object>> ret = new ArrayList<>();
        for(Message message : messages){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("mid",message.getMid());
            tmp.put("title",message.getTitle());
            tmp.put("content",message.getContent());
            tmp.put("date",dateFormat.format(message.getDate()));
            tmp.put("status",message.getStatus());
            tmp.put("tid",message.getSenduid());
            ret.add(tmp);
        }
        return ret;
    }

    @GetMapping("/api/message/page/{tid}")
    public Map<String,Object> getMessagePagesByTid(@PathVariable("tid") Integer tid,HttpSession session){
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        ret.put("pageSize",0);
        if(user != null) ret.put("pageSize",(messageService.findMessagesTotalByUidAndTid(user.getUid(),tid != null&& tid==-2?null:tid)+9)/10);
        return ret;
    }

    @PutMapping("/api/message/status/{mid}")
    public Map<String,Object> updateStatusMessage(@PathVariable("mid") Integer mid,HttpSession session){
        Map<String,Object> ret =new HashMap<>();
        ret.put("state",0);
        User user = (User) session.getAttribute("user");
        if(user == null) ret.put("state",1);
        else if(!messageService.updateMessage(mid,user.getUid())) ret.put("state",1);
        return ret;
    }

    @GetMapping("/api/message/about/{page}")
    public List<Map<String, Object>> getMyMessages(@PathVariable("page") Integer page, HttpSession session){
        List<Map<String,Object>> ret = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        if (user == null) return ret;
        List<Message> messages = messageService.findMessagesByPageAndUidAndSid(page-1, 10,user.getUid(), -2);
        return getMaps(ret, messages);
    }

    @GetMapping("/api/message/friend/{page}")
    public List<Map<String, Object>> getMyMessagesFromFriends(@PathVariable("page") Integer page, HttpSession session){
        List<Map<String,Object>> ret = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        if (user == null) return ret;
        List<Message> messages = messageService.findMessagesByPageAndUidAndSid(page-1, 10, user.getUid(), null);
        for(Message message : messages){
            if(message.getSenduid() <= 0) continue;;
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("mid",message.getMid());
            User u = userService.findUserByUid(message.getSenduid());
            tmp.put("username",u==null?"null":u.getUsername()+"|"+u.getRating());
            tmp.put("title",message.getTitle());
            tmp.put("date",dateFormat.format(message.getDate()));
            tmp.put("status",message.getStatus());
            ret.add(tmp);
        }
        return ret;
    }

    @GetMapping("/api/message/system/{page}")
    public List<Map<String, Object>> getMyMessagesFromSystem(@PathVariable("page") Integer page, HttpSession session){
        List<Map<String,Object>> ret = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        if (user == null) return ret;
        List<Message> messages = messageService.findMessagesByPageAndUidAndSid(page-1, 10,user.getUid(), -1);
        return getMaps(ret, messages);
    }

    @GetMapping("/api/message/order/{page}")
    public List<Map<String, Object>> getMyMessagesFromOrder(@PathVariable("page") Integer page, HttpSession session){
        List<Map<String,Object>> ret = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 6) return ret;
        List<Message> messages = messageService.findMessagesByPageAndUidAndSid(page-1, 10,-1, -3);
        return getMaps(ret, messages);
    }

    private List<Map<String, Object>> getMaps(List<Map<String, Object>> ret, List<Message> messages) {
        for(Message message : messages){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("mid",message.getMid());
            tmp.put("title",message.getTitle());
            tmp.put("date",dateFormat.format(message.getDate()));
            tmp.put("status",message.getStatus());
            ret.add(tmp);
        }
        return ret;
    }

    @PostMapping("/api/message/status/all/{type}")
    public Map<String,Integer> changeAllStatus(@PathVariable("type") Integer type, HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            ret.put("state",1);
            return ret;
        }
        if (type == 0) {
            messageService.updateMessageBySidAndTid(-1,user.getUid());
        } else if (type == 1) {
            messageService.updateMessageBySidAndTid(-2,user.getUid());
        } else if (type == 2) {
            messageService.updateMessageBySidAndTid(null,user.getUid());
        }
        ret.put("state",0);
        return ret;
    }

    @GetMapping("/api/message/content/{mid}")
    public Map<String,String> getContentByMid(@PathVariable("mid") Integer mid,HttpSession session){
        Map<String,String> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Message message = messageService.findMessagesByMid(mid);
        if (user == null || message == null || (!message.getTargetuid().equals(user.getUid()) && !message.getSenduid().equals(user.getUid()) && (message.getTargetuid()==-1 && user.getRole()<6))) {
            ret.put("state","0");
            return ret;
        }
        ret.put("content", message.getContent());
        return ret;
    }

    @GetMapping("/api/message/send/{page}")
    public List<Map<String, Object>> getMyMessagesFromSend(@PathVariable("page") Integer page, HttpSession session){
        List<Map<String,Object>> ret = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        if (user == null) return ret;
        List<Message> messages = messageService.findMessagesByPageAndUidAndSid(page-1, 10, null, user.getUid());
        for(Message message : messages){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("mid",message.getMid());
            tmp.put("title",message.getTitle());
            tmp.put("username", UserUtil.getNoAchiStyleNameByUid(message.getTargetuid(),userService));
            tmp.put("date",dateFormat.format(message.getDate()));
            tmp.put("status",message.getStatus());
            ret.add(tmp);
        }
        return ret;
    }
}
