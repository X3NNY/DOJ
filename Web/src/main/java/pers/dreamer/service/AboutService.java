package pers.dreamer.service;

import pers.dreamer.bean.About;

public interface AboutService {
    About findAboutByName(String index);

    boolean updateAboutByName(About about);
}
