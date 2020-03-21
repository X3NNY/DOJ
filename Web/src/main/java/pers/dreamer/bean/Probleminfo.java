package pers.dreamer.bean;

import java.io.Serializable;

public class Probleminfo implements Serializable {
    private Integer pid;

    private Integer acCnt;

    private Integer peCnt;

    private Integer waCnt;

    private Integer tleCnt;

    private Integer mleCnt;

    private Integer ceCnt;

    private Integer reCnt;

    private Integer oleCnt;

    private Integer seCnt;

    private Integer upVote;

    private Integer downVote;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getAcCnt() {
        return acCnt;
    }

    public void setAcCnt(Integer acCnt) {
        this.acCnt = acCnt;
    }

    public Integer getPeCnt() {
        return peCnt;
    }

    public void setPeCnt(Integer peCnt) {
        this.peCnt = peCnt;
    }

    public Integer getWaCnt() {
        return waCnt;
    }

    public void setWaCnt(Integer waCnt) {
        this.waCnt = waCnt;
    }

    public Integer getTleCnt() {
        return tleCnt;
    }

    public void setTleCnt(Integer tleCnt) {
        this.tleCnt = tleCnt;
    }

    public Integer getMleCnt() {
        return mleCnt;
    }

    public void setMleCnt(Integer mleCnt) {
        this.mleCnt = mleCnt;
    }

    public Integer getCeCnt() {
        return ceCnt;
    }

    public void setCeCnt(Integer ceCnt) {
        this.ceCnt = ceCnt;
    }

    public Integer getReCnt() {
        return reCnt;
    }

    public void setReCnt(Integer reCnt) {
        this.reCnt = reCnt;
    }

    public Integer getOleCnt() {
        return oleCnt;
    }

    public void setOleCnt(Integer oleCnt) {
        this.oleCnt = oleCnt;
    }

    public Integer getSeCnt() {
        return seCnt;
    }

    public void setSeCnt(Integer seCnt) {
        this.seCnt = seCnt;
    }

    public Integer getUpVote() {
        return upVote;
    }

    public void setUpVote(Integer upVote) {
        this.upVote = upVote;
    }

    public Integer getDownVote() {
        return downVote;
    }

    public void setDownVote(Integer downVote) {
        this.downVote = downVote;
    }
}