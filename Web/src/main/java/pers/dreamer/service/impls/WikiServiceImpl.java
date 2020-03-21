package pers.dreamer.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.dreamer.bean.Wiki;
import pers.dreamer.bean.WikiExample;
import pers.dreamer.dao.WikiMapper;
import pers.dreamer.service.WikiService;

import java.util.List;

@Service
public class WikiServiceImpl implements WikiService {

    @Autowired
    WikiMapper wikiMapper;

    @Override
    public Wiki findWikiByName(String name) {
        WikiExample example = new WikiExample();
        example.createCriteria().andNameEqualTo(name);
        List<Wiki>  wikis = wikiMapper.selectByExample(example);
        return wikis.isEmpty()?null:wikis.get(0);
    }

    @Override
    public List<Wiki> findWikiByPreId(Integer preId) {
        WikiExample example = new WikiExample();
        example.createCriteria().andPreidEqualTo(preId);
        return wikiMapper.selectByExample(example);
    }
}
