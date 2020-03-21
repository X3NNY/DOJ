package pers.dreamer.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pers.dreamer.bean.Contest;
import pers.dreamer.bean.User;
import pers.dreamer.service.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.Math.max;

@Component
@EnableAsync
public class ScheduledTaskHandler {

    static Logger logger = LoggerFactory.getLogger(ScheduledTaskHandler.class);

    @Autowired
    UserService userService;

    @Autowired
    ProblemService problemService;

    @Autowired
    BlogService blogService;

    @Autowired
    ContestService contestService;

    @Autowired
    MessageService messageService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0 0 0 * * ?")
    public void task(){
        //System.out.println("task ing ..............");
        List<User> users = userService.findAllUsersByLiveness(500);
        Set<User> set = new TreeSet<>((o1, o2) -> o1.getLiveness() > (o2.getLiveness()) ? -1 : 1);
        for(User user : users){
            set.add(user);
        }
        int i = 0;
        User u = null;
        for(User user : set){
            u = user;
            if(++i == 10) break;
        }
        if(u != null){
             i = 0;
            for(User user : set){
                int addCoins = max((user.getLiveness() - u.getLiveness())/37,5);
                user.setCoins(user.getCoins()+addCoins);
                pers.dreamer.bean.Message message = new pers.dreamer.bean.Message();
                message.setTitle("昨日活跃度奖励");
                message.setContent("获得DC： +"+addCoins+"，现在共有"+user.getCoins()+"DC，继续保持噢！");
                message.setStatus(0);
                message.setTargetuid(user.getUid());
                message.setSenduid(-1);
                message.setDate(new Date());
                messageService.insertMessage(message);
                if(++i == 10) break;
            }
        }
        for(User user : set) {
            user.setLiveness(max(user.getLiveness()-200,500));
            userService.updateUserInfo(user);
        }

        //mail|title|text
        Contest contest = contestService.findContestByNow();
        if(contest != null){
            for(User user : users) {
                try {
                    sendMsgForEmail(user.getEmail()+"|DOJ今日比赛推荐~|Hello,"+user.getUsername()+".\nDOJ今日比赛"+contest.getTitle()+"开始于"+contest.getStarttime()+",期待您的参与，Good luck & high rating!");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //CorrelationData中包含一个id(我们可以自定义，将其与发送的消息相关联)，当开启了confirm机制后，会收到包含该参数的回调，用于消息可靠性投递，可选的
    private CorrelationData corrData=new CorrelationData(String.valueOf(System.currentTimeMillis()));

    @Async
    public void sendMsgForEmail(String content) throws UnsupportedEncodingException {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("desc", "邮件发送");
        messageProperties.getHeaders().put("type", "mail");
        Message message = new Message(content.getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.send("mail_topic_exchange","mail.send", message,corrData);
    }
}
