package pers.dreamer.oj.judge.service;

import pers.dreamer.oj.judge.pojo.Hack;

public interface HackService {
    Hack findHackById(Integer hid);

    void update(Hack hack);

    void insertHack(Hack hack);

    Hack findHackByFilename(String filename);
}
