package pers.dreamer.service;

import org.omg.PortableInterceptor.INACTIVE;
import pers.dreamer.bean.*;

import java.util.Date;
import java.util.List;

public interface ContestService {
    Contestproblems findProblemIsExistsOnContestByPid(Integer pid);

    boolean findUserRegisterOnTheContest(Integer uid, Integer cid);

    List<Contest> findContestByPageAndNameAndUid(Integer page, Integer limitSize, String name,Integer uid);

    List<User> findContestForAuthorsByCid(Integer cid);

    Integer findMyVoteByUidAndCid(Integer uid, Integer cid);

    Long findRegisterTotalOnContestByCid(Integer cid);

    Long findContestSizeByPageAndName(String name);

    Integer findStateOnContestByUidAndCid(Integer uid, Integer cid);

    Contest findContestInfoByCid(Integer cid);

    List<Problem> findProblemsByCid(Integer cid);

    Problem findProblemsByCidAndNum(Integer cid, Integer num);

    List<Submitlist> findStatusOnContestByCidAndPageAndUidAndPidAndResAndLang(Integer cid, Integer page, Integer limitSize,Integer uid, Integer pid, Integer res, String lang);

    Integer findNumOnContestByPid(Integer pid);

    List<Contestnotice> findNoticesOnContestByCid(Integer cid);

    void insertNotice(Integer cid, Integer pid, Integer uid,String question);

    Integer findPidByCidAndNum(Integer cid, Integer num);

    Integer findVaildRegistersByCid(Integer cid);

    List<Contestregister> findRestersOnContestByCid(Integer cid);

    Integer findAc_numByUidAndCid(Integer uid, Integer cid);

    Long findPenaltyOnContestByCidAndUid(Integer pid, Integer uid,Integer flag);

    Submitlist findFirstlyAcProblemTimeOnContestByUidAndPid(Integer cid,Integer uid, Integer pid);

    Integer findStatusOnContestByUidAndCid(Integer uid, Integer cid);

    void updateRegisterByUidAndCid(Integer uid, Integer cid);

    Long findContestsTotalByUid(Integer uid);

    List<Contestproblems> findOrderProblemsByCid(Integer cid);

    Long findStatusSizeOnContestByCidAndPageAndUidAndPidAndResAndLang(Integer cid, Integer uid, Integer pid, Integer res, String lang);

    Contestregister findContestReigsterByUidAndCid(Integer uid, Integer cid);

    void insertContestRegister(Contestregister contestregister);

    void delContestRegister(Contestregister contestregister);

    List<Contestregister> findRestersOnContestByCidAndPage(Integer cid,Integer page);

    void insertContest(Contest contest);

    void insertContestProblem(Contestproblems contestproblems);

    void insertMyVoteForContest(Integer cid,Integer uid,boolean up);

    List<Contestregister> findRestersOnContestByUid(Integer uid);

    Long findFormalContestRegisterBy(Integer cid);

    Contestregister findFormalContestRegisterById(Integer id);

    void updateRegister(Contestregister contestregister);

    Contest findContestByNow();

    Integer findFirstBloodOnContestByCidAndPid(Integer cid, Integer pid);

    List<Contest> findAllContestByPage(Integer page, Integer limitSize); //所有正式比赛

    Contest findContestByTitleAndUidAndLevel(String title, Integer uid,Integer level);

    void deleteAllContestproblemByCid(Integer cid);

    void updateContest(Contest contest);

    User findAuthorByCid(Integer cid);

    Contestnotice findNoticeByNid(Integer nid);

    void updateContestnoticeByNid(Contestnotice contestnotice);

    boolean isContestEnds(Integer cid);

    boolean isContestAdminByUid(Integer cid,Integer uid);

    Long findAllPenaltyOnContestByCidAndUid(Integer cid, Integer uid, Date starttime);

    boolean isValidUser(Integer cid, Integer uid);

    Contest findContestTimeAndTypeInfoByCid(Integer cid);

    Date findFirstlyAcTimeOnContestByUidAndPid(Integer cid, Integer uid, Integer pid);

    List<Integer> findRestersUidOnContestByCid(Integer cid);

    List<Contestsubmitinfo> findContestsubmitinfoByCid(Integer cid);

    Integer findProblemsTotalByCid(Integer cid);

    Integer findRegisterStateByCidAndUid(Integer cid, Integer uid);

    List<Problem> findProblemsTitleByCid(Integer cid);

    Integer findProblemAcCntByPid(Integer cid, Integer pid);

    Integer findProblemAllCntByPid(Integer cid,Integer pid);

    List<Contestproblems> findProblemsNumByCid(Integer cid);

    void updateRegisterRankByUid(Integer cid,Integer uid, Integer rank);

    List<Contestregister> findRestersAndRankNotNullOnContestByCid(Integer cid);
}
