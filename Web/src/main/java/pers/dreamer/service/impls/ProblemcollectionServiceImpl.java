package pers.dreamer.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.dreamer.bean.Problemcollection;
import pers.dreamer.bean.ProblemcollectionExample;
import pers.dreamer.dao.ProblemcollectionMapper;
import pers.dreamer.service.ProblemcollectionService;

import java.util.List;

@Service
public class ProblemcollectionServiceImpl implements ProblemcollectionService {

    @Autowired
    ProblemcollectionMapper problemcollectionMapper;

    @Override
    public Boolean isUserCollect(Integer pid, Integer uid) {
        ProblemcollectionExample problemcollectionExample = new ProblemcollectionExample();
        problemcollectionExample.createCriteria().andPidEqualTo(pid).andUidEqualTo(uid);
        return problemcollectionMapper.countByExample(problemcollectionExample) != 0;
    }

    @Override
    public List<Problemcollection> findCollectsByUid(Integer uid) {
        ProblemcollectionExample example = new ProblemcollectionExample();
        example.createCriteria().andUidEqualTo(uid);
        return problemcollectionMapper.selectByExample(example);
    }
}
