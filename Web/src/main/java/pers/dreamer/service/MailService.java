package pers.dreamer.service;

import org.springframework.mail.MailMessage;

public interface MailService {

    boolean sendSimpleMail(String to, String subject, String content);

    boolean sendHtmlMail(String to, String subject, String content);

    void setMailMessage(MailMessage mailMessage);
}