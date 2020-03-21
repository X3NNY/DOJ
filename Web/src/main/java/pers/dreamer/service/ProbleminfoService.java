package pers.dreamer.service;

import pers.dreamer.bean.Probleminfo;

public interface ProbleminfoService {

    Integer findAll_cntByPid(Integer pid);

    Integer findPass_numByPid(Integer pid);

    Integer findSubmit_numByPid(Integer pid);

    Probleminfo findProblemInfoByPid(Integer pid);

    void updateProblemInfo(Probleminfo probleminfo);

    void insertProblemInfoByPid(Integer pid);

    void upVote(Integer pid);

    void downVote(Integer pid);
}
