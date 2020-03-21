package pers.dreamer.service;

import java.util.List;

public interface ExlangService {
    List<String> findSubmitLimitByLangAndPid(Integer pid);

    boolean findLangInExlangsByPidAndLang(Integer pid,String lang);
}
