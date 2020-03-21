package pers.dreamer.dto;

public class CodeDto {

    private String lang;
    private Integer pid;
    private Integer timeLimit;
    private Integer memoryLimit;
    private Boolean special;
    private Integer res;
    private Integer index; //如果错误 第几组
    private Integer maxTime;
    private Integer maxMemory;
    private Double score;
    private Integer sid;
    private String msg;
    private String code;
    private Integer kind;

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getRes() {
        return res;
    }

    public void setRes(Integer res) {
        this.res = res;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Integer maxTime) {
        this.maxTime = maxTime;
    }

    public Integer getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(Integer maxMemory) {
        this.maxMemory = maxMemory;
    }

    public Double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "CodeDto{" +
                "lang='" + lang + '\'' +
                ", pid=" + pid +
                ", timeLimit=" + timeLimit +
                ", memoryLimit=" + memoryLimit +
                ", special=" + special +
                ", res=" + res +
                ", index=" + index +
                ", maxTime=" + maxTime +
                ", maxMemory=" + maxMemory +
                ", score=" + score +
                ", sid='" + sid + '\'' +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", kind=" + kind +
                '}';
    }

    public void setScore(Double score) {
        this.score = score;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSpecial() {
        return special;
    }

    public void setSpecial(Boolean special) {
        this.special = special;
    }
    public String getLang() {
        return lang;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(Integer memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }
}
