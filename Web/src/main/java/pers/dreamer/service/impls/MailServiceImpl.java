package pers.dreamer.service.impls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pers.dreamer.service.MailService;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class MailServiceImpl implements MailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    static final String DELIM_STR = "{}";
    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromAddress}")
    private String from;

    MailMessage mailMessage;

    @Override
    public boolean sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            logger.info("简单邮件已经发送。");
            return  true;
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
            return  false;
        }

    }

    @Override
    public boolean sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress(from,"Dreamer Online Judge"));
            message.setSubject(subject,"utf-8");
            //设置防垃圾邮件处理代码--start
            message.addHeader("X-Priority","3");
            message.addHeader("X-MSMail-Priority","Normal");
            message.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");//伪装成outlook发送邮件，防止被当成垃圾邮件处理
            message.addHeader("X-MimeOLE","Produced By Microsoft MimeOLE V6.00.2900.2869");
            message.addHeader("X-ReturnReceipt","1");
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSentDate(new Date());
            helper.setText(content, true);
            mailSender.send(message);
            logger.info("html邮件发送成功");
            return  true;
        } catch (Exception e) {
            logger.error("发送html邮件时发生异常！", e);
            return  false;
        }
    }

    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);

            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        }
    }

    public MailMessage getMailMessage() {
        return mailMessage;
    }

    public void setMailMessage(MailMessage mailMessage) {
        this.mailMessage = mailMessage;
    }

    public boolean sendHtml(String content, String to,String mailSubiect,String mailBody) throws IOException {
        MimeMessage mimeMsg = mailSender.createMimeMessage();
        try {
            // 设置发信人
            mimeMsg.setFrom(new InternetAddress(from,"admin"));//设置发送人别称
            // 设置收信人
            if (to != null) {
                mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress
                        .parse(to));
            }
            // 设置邮件主题
            mimeMsg.setSubject(mailSubiect, "utf-8");
            // 设置邮件内容，将邮件body部分转化为HTML格式
            mimeMsg.setContent(mailBody, "text/html;charset=utf-8");
            //mimeMsg.setDataHandler(new javax.activation.DataHandler(
            //	new StringDataSource(MailBody, "text/html")));
            //设置防垃圾邮件处理代码--start
            mimeMsg.addHeader("X-Priority","3");
            mimeMsg.addHeader("X-MSMail-Priority","Normal");
            mimeMsg.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");//伪装成outlook发送邮件，防止被当成垃圾邮件处理
            mimeMsg.addHeader("X-MimeOLE","Produced By Microsoft MimeOLE V6.00.2900.2869");
            mimeMsg.addHeader("X-ReturnReceipt","1");
            //--end---
            // 发送邮件
            Transport.send(mimeMsg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
