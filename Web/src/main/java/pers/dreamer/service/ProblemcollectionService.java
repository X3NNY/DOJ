package pers.dreamer.service;

import pers.dreamer.bean.Problemcollection;

import java.util.List;

public interface ProblemcollectionService {
    Boolean isUserCollect(Integer pid, Integer uid);

    List<Problemcollection> findCollectsByUid(Integer uid);
}
