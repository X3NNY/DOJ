package pers.dreamer.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.dreamer.bean.Exlang;
import pers.dreamer.bean.ExlangExample;
import pers.dreamer.dao.ExlangMapper;
import pers.dreamer.service.ExlangService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExlangServicelmpl implements ExlangService {

    @Autowired
    ExlangMapper exlangMapper;

    @Override
    public List<String> findSubmitLimitByLangAndPid(Integer pid) {
        ExlangExample exlangExample = new ExlangExample();
        exlangExample.createCriteria().andPidEqualTo(pid);
        List<Exlang> exlangs = exlangMapper.selectByExample(exlangExample);
        List<String> ret = new ArrayList<>();
        for(Exlang exlang : exlangs) ret.add(exlang.getExlang());
        return ret;
    }

    @Override
    public boolean findLangInExlangsByPidAndLang(Integer pid, String lang) {
        if(pid == null || lang == null || lang.equals("")) return true;
        ExlangExample exlangExample = new ExlangExample();
        exlangExample.createCriteria().andPidEqualTo(pid).andExlangEqualTo(lang);
        return exlangMapper.countByExample(exlangExample) > 0;
    }
}
