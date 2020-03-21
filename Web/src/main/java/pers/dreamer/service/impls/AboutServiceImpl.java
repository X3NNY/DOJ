package pers.dreamer.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.dreamer.bean.About;
import pers.dreamer.bean.AboutExample;
import pers.dreamer.dao.AboutMapper;
import pers.dreamer.service.AboutService;

import java.util.List;

@Service
public class AboutServiceImpl implements AboutService{

    @Autowired
    AboutMapper aboutMapper;

    @Override
    public About findAboutByName(String index) {
        if(index == null) return  null;
        AboutExample aboutExample = new AboutExample();
        aboutExample.createCriteria().andNameEqualTo(index);
        List<About> abouts = aboutMapper.selectByExample(aboutExample);
        return abouts.size() > 0 ? abouts.get(0) : null;
    }

    @Override
    public boolean updateAboutByName(About about) {
        if(about.getName() == null) return false;
        AboutExample example = new AboutExample();
        example.createCriteria().andNameEqualTo(about.getName());
        aboutMapper.updateByExampleSelective(about,example);
        return true;
    }


}
