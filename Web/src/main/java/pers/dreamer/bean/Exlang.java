package pers.dreamer.bean;

import java.io.Serializable;

public class Exlang implements Serializable {
    private Integer id;

    private Integer pid;

    private String exlang;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getExlang() {
        return exlang;
    }

    public void setExlang(String exlang) {
        this.exlang = exlang == null ? null : exlang.trim();
    }
}