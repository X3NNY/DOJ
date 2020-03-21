package pers.dreamer.service;

import pers.dreamer.bean.*;

import java.util.List;

public interface ProblemService {
    Problem findProblemByPid(Integer pid,User user);

    List<Problem> findProblemsByPageAndNameAndTagAndStar(Integer page, Integer size, String name, String tag, Integer star,User user);

    Integer findProblemVoteByUidAndPid(Integer uid, Integer pid);

    void insertProblemVoteyByUidAndPid(Integer uid, Integer pid, int i);

    User findAuthorByPid(Integer pid);

    Long findProblemsSizeByPageAndNameAndTagAndStar(String name, String tag, Integer star,User user);

    Boolean insertProblem(Problem problem);

    Problem findProblemByTitle(String title);

    boolean insertProblemTag(Problemtag problemtag);

    Problemtag findProblemtagsByUidAndPid(Integer uid, Integer pid);

    void changeProblemCollectionByPidAndUid(Integer pid, Integer uid);

    boolean delTagsByUidAndPidAndTag(Integer uid, Integer pid, String tag);

    boolean editProblem(Problem problem);

    boolean updateProblemStatusByPidAndStatus(Integer pid, Integer status);

    List<Problem> findProblemsToIndex();

    void updateProblem(Problem problem);

    Problem findProblemByPidWithoutUid(Integer pid);

    boolean isVoteExist(Integer uid, Integer pid);

    Integer findAuthorIdByPid(Integer pid);

    boolean isProblemCanBeViewed(Integer pid, User user);

    String findProblemTitleByPid(Integer pid);

    boolean isExistProblem(Integer pid);

    Integer findTidByTagname(String tag);

    String findTagnameByTid(Integer tid);

    List<Problemtag> findProblemtagsUidByPid(Integer pid);

    List<Tags> findTags();

    boolean isExistTag(Integer tid);
}
