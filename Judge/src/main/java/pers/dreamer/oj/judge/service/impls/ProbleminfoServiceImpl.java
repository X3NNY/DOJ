package pers.dreamer.oj.judge.service.impls;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.dreamer.oj.judge.dao.ProbleminfoMapper;
import pers.dreamer.oj.judge.pojo.Probleminfo;
import pers.dreamer.oj.judge.pojo.ProbleminfoExample;
import pers.dreamer.oj.judge.service.ProbleminfoService;

import javax.annotation.Resource;

@Service
public class ProbleminfoServiceImpl implements ProbleminfoService {

    @Resource
    ProbleminfoMapper probleminfoMapper;

    @Override
    public void insertProblemInfoByPid(Integer pid) {
        Probleminfo probleminfo = new Probleminfo();
        probleminfo.setPid(pid);
        probleminfoMapper.insertSelective(probleminfo);
    }

    @Transactional
    @Override
    public void deleteProbleminfoByPid(Integer pid) {
        if(pid == null) return;
        ProbleminfoExample probleminfoExample = new ProbleminfoExample();
        probleminfoExample.createCriteria().andPidEqualTo(pid);
        probleminfoMapper.deleteByExample(probleminfoExample);

    }
}
