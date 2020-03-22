package pers.dreamer.oj.judge.service.impls;

import org.springframework.stereotype.Service;
import pers.dreamer.oj.judge.dao.HackMapper;
import pers.dreamer.oj.judge.pojo.Hack;
import pers.dreamer.oj.judge.pojo.HackExample;
import pers.dreamer.oj.judge.service.HackService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HackServiceImpl implements HackService {

    @Resource
    HackMapper hackMapper;

    @Override
    public Hack findHackById(Integer hid) {
        return hackMapper.selectByPrimaryKey(hid);
    }

    @Override
    public void update(Hack hack) {
        hackMapper.updateByPrimaryKeySelective(hack);
    }

    @Override
    public void insertHack(Hack hack) {
        hackMapper.insertSelective(hack);
    }

    @Override
    public Hack findHackByFilename(String filename) {
        HackExample example = new HackExample();
        example.createCriteria().andFilenameEqualTo(filename);
        example.setOrderByClause("id desc");
        List<Hack> hacks = hackMapper.selectByExample(example);
        return hacks.isEmpty()?null:hacks.get(0);
    }
}
