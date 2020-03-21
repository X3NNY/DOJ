package pers.dreamer.service;

import pers.dreamer.bean.Code;
import pers.dreamer.bean.Submitcollection;
import pers.dreamer.bean.Submitlist;
import pers.dreamer.dto.CodeDto;
import pers.dreamer.dto.RankDto;
import pers.dreamer.dto.ResultDto;
import pers.dreamer.dto.SubmitDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SubmitlistService {
    Integer findStatusOnProblemByUidAndPid(Integer uid, Integer pid);

    Integer findContestStatusOnPorblemByCidAndUidAndPid(Integer cid, Integer uid, Integer pid);

    Integer insertCodeAndSubmit(CodeDto codeDto, Integer uid,Integer cid);

    List<Submitlist> findSubmitlistByPageAndUidAndPidAndResAndLang(Integer page, Integer limitSize, Integer uid, Integer pid, Integer res, String lang);

    Code findCodeByCodeid(Integer codeid);

    Long findSubmitlistSizeByPageAndUidAndPidAndResAndLang(Integer uid, Integer pid, Integer res, String lang);

    List<Submitlist> findSubmitlistsByCidAndUid(Integer cid, Integer uid);

    List<Submitlist> findSubmitlistsByUidAndPid(Integer uid, Integer pid);

    Submitlist findSubmitlistBySid(Integer sid);

    boolean updateSubmitlistBySid(Submitlist submitlist);

    boolean deleteSubmitlistBySid(Integer sid);

    boolean insertSubmitCollection(Submitcollection submitcollection);

    Submitcollection findSubmitCollectionByUidAndSid(Integer uid,Integer sid);

    boolean deleteSubmitCollectionByUidAndSid(Integer uid, Integer sid);

    boolean findSubmitlistsOnExiststByCidAndUid(Integer cid, Integer uid);

    Long findTotalSizeByUidAndPidAndCid(Integer uid, Integer pid, Integer cid);

    Submitlist findSubmitlistByCidAndUidAndPid(Integer cid, Integer uid, Integer pid);

    List<Submitlist> findSubmitlistsByPid(Integer pid);

    void updateCode(Code code);

    Long findSubmitlistSizeOnDateByResult(Date date1,Date date2, Integer result);

    Long findSubmitlistSizeOnDateByResultAndUid(Date date, Date tmp, Integer result, Integer uid);

    List<Date> findSubmitlistsDateByResultAndUidGroupByPid(Integer result, Integer uid);

    List<Integer> findSidsByUid(Integer uid);

    List<Submitlist> findSubmitlistByCid(Integer cid);

    List<Submitcollection> findSubmitCollectionByUid(Integer uid);

    boolean isExistSubmitTodatByUidAndCid(Integer uid,Integer cid);

    Double findHighScoreSubmitlistByCidAndUidAndPid(Integer cid, Integer uid, Integer pid);

    Submitlist findShortCodeSubmitlistByCidAndUidAndPid(Integer cid, Integer uid, Integer pid);

    Submitlist findMinTimeSubmitlistByCidAndUidAndPid(Integer cid, Integer uid, Integer pid);

    boolean isSubmitCanBeViewedByUid(Integer uid, Integer pid);

    boolean isCollectedByUid(Integer sid, Integer uid);

    boolean isExistSubmitByCid(Integer cid, Integer uid);

    Integer findUidBySid(Integer sid);

    Integer findPidBySid(Integer sid);

    List<SubmitDto> findAllSubmitlistLast30Days();

    List<SubmitDto> findAcSubmitlistLast30Days();

    List<SubmitDto> findAllSubmitlistLast30DaysByUid(Integer uid);

    List<SubmitDto> findAcSubmitlistLast30DaysByUid(Integer uid);

    List<Long> findAllPassDateOnProblemByUid(Integer uid);

    List<SubmitDto> findRegisterLast30Days();

    List<RankDto> findRankByCidAndUid(Integer cid, Integer uid);

    List<Submitlist> findRankSubmitlistByCid(Integer cid);

    Integer findStatusOnProblemByCidAndUidAndPid(Integer cid, Integer uid, Integer pid);

    void updateSubmitByResult(ResultDto resultDto);
}
