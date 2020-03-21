package pers.dreamer.bean;

import java.io.Serializable;
import java.util.Date;

public class Submitlist implements Serializable {

    private Integer sid;

    private Date date;

    private Integer pid;

    private Integer cid;

    private Integer uid;

    private String lang;

    private Integer result;

    private Integer timeUsed;

    private Integer memoryUsed;

    private Integer state;

    private Double score;

    private Integer pos;

    private String msg;

    private Integer size;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang == null ? null : lang.trim();
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(Integer timeUsed) {
        this.timeUsed = timeUsed;
    }

    public Integer getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(Integer memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    @Override
    public String toString() {
        return "Submitlist{" +
                "sid=" + sid +
                ", date=" + date +
                ", pid=" + pid +
                ", cid=" + cid +
                ", uid=" + uid +
                ", lang='" + lang + '\'' +
                ", result=" + result +
                ", timeUsed=" + timeUsed +
                ", memoryUsed=" + memoryUsed +
                ", state=" + state +
                ", score=" + score +
                ", pos=" + pos +
                ", msg='" + msg + '\'' +
                ", size=" + size +
                '}';
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}