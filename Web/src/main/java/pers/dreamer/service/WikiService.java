package pers.dreamer.service;

import pers.dreamer.bean.Wiki;

import java.util.List;

public interface WikiService {
    Wiki findWikiByName(String name);

    List<Wiki> findWikiByPreId(Integer preId);
}
