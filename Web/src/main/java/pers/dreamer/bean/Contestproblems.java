package pers.dreamer.bean;

import java.io.Serializable;

public class Contestproblems implements Serializable {
    private Integer id;

    private Integer cid;

    private Integer pid;

    private Integer num;

    private Integer acCnt;

    private Integer allCnt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAcCnt() {
        return acCnt;
    }

    public void setAcCnt(Integer acCnt) {
        this.acCnt = acCnt;
    }

    public Integer getAllCnt() {
        return allCnt;
    }

    public void setAllCnt(Integer allCnt) {
        this.allCnt = allCnt;
    }
}