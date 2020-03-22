package pers.dreamer.oj.judge.service;

import pers.dreamer.oj.judge.pojo.User;

public interface UserService {
    public User findUser(Integer uid);

    boolean updateUserInfo(User user);
}
