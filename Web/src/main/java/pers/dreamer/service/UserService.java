package pers.dreamer.service;

import pers.dreamer.bean.User;
import pers.dreamer.dto.ContestDto;

import java.util.Date;
import java.util.List;

public interface UserService {
    User findUserByPwdAndName(String username, String password) throws Exception;

    User findUserByName(String username);

    boolean insertUser(String email, String username, String password, String school, String invitcode);

    boolean updateUserInfo(User user);

    User findUserByUid(Integer uid);

    Long findAc_cntByUid(Integer uid);

    Long findAll_cntByUid(Integer uid);

    Integer findAc_numByUid(Integer uid);

    List<ContestDto> findContestByUid(Integer uid);

    List<User> findRanksByPageAndName(Integer page, Integer limitSize,  String name);

    Long findUserTotal();

    List<User> findAllUsersByLiveness(Integer limit);

    int findRankOnContestByUid(Integer cid, Integer uid);

    Long findRegisterNumOnDate(Date date, Date tmp);

    Long findUserTotalByNameOrId(String name);

    List<User> findUserByPageAndName(Integer page, String name);

    User findUserStyleNameByUid(Integer uid);

    void addLivenessAndLevelByUid(Integer uid, int i);

    void addLivenessByUid(Integer uid, int i);

    Integer findUidByUsername(String username);

    String findUsernameByUid(Integer uid);

    boolean isExistUserByUid(Integer uid);

    Long findRankByRating(Integer rating);

    Integer findRatingByUid(Integer uid);

    void updateUserInfoNew(User user);

    List<User> findEmailAndName();
}
