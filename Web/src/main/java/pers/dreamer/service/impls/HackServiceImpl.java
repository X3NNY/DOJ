package pers.dreamer.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.dreamer.bean.Hack;
import pers.dreamer.bean.HackExample;
import pers.dreamer.bean.User;
import pers.dreamer.dao.HackMapper;
import pers.dreamer.service.HackService;
import pers.dreamer.service.SubmitlistService;

import java.util.List;

@Service
public class HackServiceImpl implements HackService {
    @Autowired
    HackMapper hackMapper;

    @Autowired
    SubmitlistService submitlistService;

    @Override
    public long findHackTotals() {
        return hackMapper.countByExample(new HackExample());
    }

    @Override
    public List<Hack> findHacksByPageAndPidAndHachAndCommitAndResult(Integer page, Integer pid, User hack, User commit, Integer result) {
        HackExample example = new HackExample();
        example.setOrderByClause(String.format("date desc limit %d,%d",20*page,20));
        HackExample.Criteria criteria = example.createCriteria();
        if (result!=null&&result!=-1) {
            criteria.andResultEqualTo(result);
        }
        if (hack!=null) {
            criteria.andUidEqualTo(hack.getUid());
        }
        if (commit!=null) {
            List<Integer> sids = submitlistService.findSidsByUid(commit.getUid());
            criteria.andSidIn(sids);
        }
        return hackMapper.selectByExample(example);
    }

    @Override
    public Hack findHackById(Integer id) {
        return hackMapper.selectByPrimaryKey(id);
    }
}
