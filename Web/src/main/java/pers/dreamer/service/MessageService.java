package pers.dreamer.service;

import pers.dreamer.bean.Message;

import java.util.List;

public interface MessageService {
    void insertMessage(Message message);

    List<Message> findMessagesByPageAndUidAndSid(Integer page,Integer limitSize, Integer uid, Integer sid);

    Long findMessagesTotalByUidAndTid(Integer uid, Integer tid);

    boolean updateMessage(Integer mid,Integer uid);

    Message findMessagesByMid(Integer mid);

    void updateMessageBySidAndTid(Integer sid, Integer tid);

    long findMessagesByUidAndStatus(Integer uid, Integer status);
}
