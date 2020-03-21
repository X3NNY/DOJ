package pers.dreamer.handler;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import pers.dreamer.service.MailService;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@EnableAsync
public class MailSendHandler{

    private static Logger logger = LoggerFactory.getLogger(MailSendHandler.class);

    @Autowired
    MailService mailService;

    @Async
    @RabbitListener(queues = "oj_queue")
    public void onMessage(Message message, Channel channel) throws Exception { //mail|title|content
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        MessageProperties pros = message.getMessageProperties();
        Map<String, Object> headers = pros.getHeaders();
        String routingKey = pros.getReceivedRoutingKey();
        if(routingKey != null && routingKey.equals("mail.send")){
            String[] mgs = msg.split("\\|");
            for (int i = 3; i < mgs.length; i++) {
                mgs[2] += "|"+mgs[i];
            }
            mailService.sendHtmlMail(mgs[0],mgs[1],mgs[2]);
        }
        logger.info("mail:消费者收到消息:{},routingKey:{},headers:{}", msg, routingKey, headers);
        //消费者ack确认
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}