package pers.dreamer.oj.judge.service.impls;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.dreamer.oj.judge.dao.MessageMapper;
import pers.dreamer.oj.judge.pojo.Message;
import pers.dreamer.oj.judge.pojo.MessageExample;
import pers.dreamer.oj.judge.service.MessageService;

import javax.annotation.Resource;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    MessageMapper messageMapper;

    @Transactional
    @Override
    public void insertMessage(Message message) {
        messageMapper.insertSelective(message);
    }

    @Override
    public Long findMessagesTotalByUidAndTid(Integer uid, Integer tid) {
        if(uid == null) return 0L;
        return messageMapper.countByExample(pattern1(uid, tid));
    }

    @Transactional
    @Override
    public boolean updateMessage(Message message) {
        return message.getMid() != null && messageMapper.selectByPrimaryKey(message.getMid()) != null && (messageMapper.updateByPrimaryKeySelective(message) > 0 || true);
    }

    private MessageExample pattern1(Integer uid, Integer tid){
        MessageExample messageExample = new MessageExample();
        MessageExample.Criteria criteria = messageExample.createCriteria().andTargetuidEqualTo(uid);
        if(tid != null) criteria.andSenduidEqualTo(tid);
        return messageExample;
    }
}
