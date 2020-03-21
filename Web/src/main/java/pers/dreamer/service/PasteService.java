package pers.dreamer.service;

import pers.dreamer.bean.Paste;

public interface PasteService {

    void insertPaste(Paste paste);

    Paste findPasteById(String id);
}
