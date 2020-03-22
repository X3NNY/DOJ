package pers.dreamer.oj.judge.service;
import pers.dreamer.oj.judge.pojo.Message;

public interface MessageService {
    public void insertMessage(Message message);

    public Long findMessagesTotalByUidAndTid(Integer uid, Integer tid);

    public boolean updateMessage(Message message);
}
