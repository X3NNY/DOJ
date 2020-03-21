package pers.dreamer.service;

import pers.dreamer.bean.Hack;
import pers.dreamer.bean.User;

import java.util.List;

public interface HackService {
    long findHackTotals();

    List<Hack> findHacksByPageAndPidAndHachAndCommitAndResult(Integer page, Integer pid, User hack, User commit, Integer result);

    Hack findHackById(Integer id);
}
