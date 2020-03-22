package pers.dreamer.oj.judge.service.impls;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.dreamer.oj.judge.dao.CodeMapper;
import pers.dreamer.oj.judge.dao.SubmitcollectionMapper;
import pers.dreamer.oj.judge.dao.SubmitlistMapper;
import pers.dreamer.oj.judge.pojo.CodeExample;
import pers.dreamer.oj.judge.pojo.SubmitcollectionExample;
import pers.dreamer.oj.judge.pojo.Submitlist;
import pers.dreamer.oj.judge.pojo.SubmitlistExample;
import pers.dreamer.oj.judge.service.ProbleminfoService;
import pers.dreamer.oj.judge.service.SubmitlistService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SubmitlistServiceImpl implements SubmitlistService {

    @Resource
    SubmitlistMapper submitlistMapper;

    @Resource
    ProbleminfoService probleminfoService;

    @Resource
    CodeMapper codeMapper;

    @Resource
    SubmitcollectionMapper submitcollectionMapper;

    @Override
    public Submitlist findSubmitlistBySid(Integer sid) {
        return submitlistMapper.selectByPrimaryKey(sid);
    }

    @Override
    public List<Submitlist> findSubmitlistsByCidAndPidAndResult(Integer cid, Integer pid, Integer res) {
        SubmitlistExample example = new SubmitlistExample();
        example.createCriteria().andCidEqualTo(cid).andPidEqualTo(pid).andResultEqualTo(res);

        return submitlistMapper.selectByExample(example);
    }

    @Transactional
    @Override
    public void deleteSubmitlistByPid(Integer pid) {
        if(pid == null) return;
        probleminfoService.deleteProbleminfoByPid(pid);
        for(Submitlist submitlist: findSubmitlistsByPid(pid)){
            CodeExample codeExample = new CodeExample();
            codeExample.createCriteria().andSidEqualTo(submitlist.getSid());
            codeMapper.deleteByExample(codeExample);
            SubmitcollectionExample submitcollectionExample = new SubmitcollectionExample();
            submitcollectionExample.createCriteria();
            submitcollectionMapper.deleteByExample(submitcollectionExample);
        }
    }

    @Override
    public List<Submitlist> findSubmitlistsByPid(Integer pid) {
        if(pid == null)  return null;
        SubmitlistExample submitlistExample = new SubmitlistExample();
        submitlistExample.createCriteria().andPidEqualTo(pid);
        return submitlistMapper.selectByExample(submitlistExample);
    }

    @Override
    public void updateSubmitlist(Submitlist submitlist) {
        submitlistMapper.updateByPrimaryKey(submitlist);
    }

    @Override
    public boolean findSubmitlistOnAcByCidAndUidAndPid(Integer cid, Integer uid, Integer pid) {
        SubmitlistExample example = new SubmitlistExample();
        example.createCriteria().andUidEqualTo(uid).andCidEqualTo(cid).andResultEqualTo(0).andPidEqualTo(pid);
        return submitlistMapper.countByExample(example)>0;
    }

    @Override
    public Submitlist findSubmitlistLangBySid(Integer sid) {
        return submitlistMapper.findSubmitlistLangBySid(sid);
    }
}
