package pers.dreamer.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.dreamer.bean.Paste;
import pers.dreamer.dao.PasteMapper;
import pers.dreamer.service.PasteService;

@Service
public class PasteServiceImpl implements PasteService {
    @Autowired
    PasteMapper pasteMapper;

    @Transactional
    @Override
    public void insertPaste(Paste paste) {
        pasteMapper.insert(paste);
    }

    @Override
    public Paste findPasteById(String id) {
        return pasteMapper.selectByPrimaryKey(id);
    }
}
