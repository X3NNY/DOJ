package pers.dreamer.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.dreamer.bean.Message;
import pers.dreamer.bean.MessageExample;
import pers.dreamer.dao.MessageMapper;
import pers.dreamer.service.MessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Transactional
    @Override
    public void insertMessage(Message message) {
        messageMapper.insertSelective(message);
    }

    @Override
    public List<Message> findMessagesByPageAndUidAndSid(Integer page, Integer limitSize, Integer uid, Integer sid) {
        MessageExample messageExample = pattern1(uid, sid);
        messageExample.setOrderByClause("date desc");
        if(page != null) messageExample.setPage(page*limitSize);
        messageExample.setLimitSize(limitSize);
        return messageMapper.selectByExample(messageExample);
    }

    @Override
    public Long findMessagesTotalByUidAndTid(Integer uid, Integer tid) {
        if(uid == null) return 0L;
        return messageMapper.countByExample(pattern1(uid, tid));
    }

    @Transactional
    @Override
    public boolean updateMessage(Integer mid,Integer uid) {
        if(mid == null || uid == null) return false;
        Message message = new Message();
        message.setStatus(1);
        MessageExample messageExample = new MessageExample();
        messageExample.createCriteria().andTargetuidEqualTo(uid);
        return messageMapper.updateByExampleSelective(message,messageExample) > 0;
    }

    @Transactional
    @Override
    public void updateMessageBySidAndTid(Integer sid, Integer tid) {
        MessageExample example = pattern1(tid,sid);
        Message message = new Message();
        message.setStatus(1);
        messageMapper.updateByExampleSelective(message,example);
    }

    @Override
    public Message findMessagesByMid(Integer mid) {
        if(mid == null) return  null;
        return messageMapper.selectByPrimaryKey(mid);
    }

    @Override
    public long findMessagesByUidAndStatus(Integer uid, Integer status) {
        MessageExample example = new MessageExample();
        example.createCriteria().andStatusEqualTo(status).andTargetuidEqualTo(uid);
        return messageMapper.countByExample(example);
    }

    private MessageExample pattern1(Integer uid, Integer sid){
        MessageExample messageExample = new MessageExample();
        MessageExample.Criteria criteria = messageExample.createCriteria();
        if (uid != null) criteria.andTargetuidEqualTo(uid);
        if(sid != null) criteria.andSenduidEqualTo(sid);
        else criteria.andSenduidGreaterThan(0);
        return messageExample;
    }
}
