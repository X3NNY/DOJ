package pers.dreamer.oj.judge.util;

public class JudgeResult {
    private int sid;
    private int res;
    private int index = -1; //如果错误 第几组
    private int maxTime;
    private int maxMemory;
    private double score;
    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public int getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(int maxMemory) {
        this.maxMemory = maxMemory;
    }

    @Override
    public String toString() {
        return "JudgeResult [index=" + index + ", maxMemory=" + maxMemory + ", maxTime=" + maxTime + ", res=" + res
                + ", score=" + score + ", sid=" + sid + "]";
    }




}