package pers.dreamer.oj.judge.service;

import pers.dreamer.oj.judge.pojo.Submitlist;

import java.util.List;

public interface SubmitlistService {
    Submitlist findSubmitlistBySid(Integer sid);

    List<Submitlist> findSubmitlistsByCidAndPidAndResult(Integer cid, Integer pid, Integer res);

    public void deleteSubmitlistByPid(Integer pid);

    public List<Submitlist> findSubmitlistsByPid(Integer pid);

    void updateSubmitlist(Submitlist submitlist);

    boolean findSubmitlistOnAcByCidAndUidAndPid(Integer cid, Integer uid, Integer pid);

    Submitlist findSubmitlistLangBySid(Integer sid);
}
