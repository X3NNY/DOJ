package pers.dreamer.oj.judge.service.impls;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.dreamer.oj.judge.dao.UserMapper;
import pers.dreamer.oj.judge.pojo.User;
import pers.dreamer.oj.judge.service.UserService;

import javax.annotation.Resource;

@Service
public class UserSeriveImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public User findUser(Integer uid) {
        if(uid == null || uid < 0) return null;
        return userMapper.selectByPrimaryKey(uid);
    }

    @Transactional
    @Override
    public boolean updateUserInfo(User user) {
        return userMapper.updateByPrimaryKeySelective(user) > 0;
    }
}
