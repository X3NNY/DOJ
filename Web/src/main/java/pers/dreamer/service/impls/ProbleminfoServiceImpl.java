package pers.dreamer.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.dreamer.bean.Probleminfo;
import pers.dreamer.bean.Submitlist;
import pers.dreamer.bean.SubmitlistExample;
import pers.dreamer.dao.ProbleminfoMapper;
import pers.dreamer.dao.SubmitlistMapper;
import pers.dreamer.service.ProbleminfoService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProbleminfoServiceImpl implements ProbleminfoService {

    @Autowired
    ProbleminfoMapper probleminfoMapper;

    @Autowired
    SubmitlistMapper submitlistMapper;

    @Override
    public Integer findAll_cntByPid(Integer pid) {
        Probleminfo probleminfo = probleminfoMapper.selectByPrimaryKey(pid);
        return probleminfo == null ? 0 :
                probleminfo.getAcCnt()
                +probleminfo.getCeCnt()
                +probleminfo.getMleCnt()
                +probleminfo.getOleCnt()
                +probleminfo.getReCnt()
                +probleminfo.getSeCnt()
                +probleminfo.getTleCnt()
                +probleminfo.getWaCnt()
                +probleminfo.getPeCnt();
    }

    @Override
    public Integer findPass_numByPid(Integer pid) {
        return submitlistMapper.findPassNumByPid(pid);
    }

    @Override
    public Integer findSubmit_numByPid(Integer pid) {
        return submitlistMapper.findSubmitNumByPid(pid);
    }

    @Override
    public Probleminfo findProblemInfoByPid(Integer pid) {
        return probleminfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public void updateProblemInfo(Probleminfo probleminfo) {
        probleminfoMapper.updateByPrimaryKeySelective(probleminfo);
    }

    @Transactional
    @Override
    public void insertProblemInfoByPid(Integer pid) {
        Probleminfo probleminfo = new Probleminfo();
        probleminfo.setPid(pid);
        probleminfoMapper.insertSelective(probleminfo);
    }

    @Transactional
    @Override
    public void upVote(Integer pid) {
        probleminfoMapper.upVote(pid);
    }

    @Transactional
    @Override
    public void downVote(Integer pid) {
        probleminfoMapper.downVote(pid);
    }


}
