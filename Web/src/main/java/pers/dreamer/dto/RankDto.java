package pers.dreamer.dto;

public class RankDto{
    private String name;
    private int[] pid;
    private long[] date;
    private int penalty;
    private int cnt; // ICPC/SC/MT->pass_num, OI/IOI->sum_score*100
    private int state;
    private int uid;
    private double[] score;

    public RankDto() {
    }

    public RankDto(int num) {
        this.pid = new int[num];
        this.date = new long[num];
        this.score = new double[num];
    }

    public void addData(int id,long time,int num,int res) {
        if (res == 0) {
            if (this.date[id] == 0) {
                this.date[id] = time;
                this.cnt++;
                this.penalty += time + (num - 1) * 1200;
            }
        }
        this.pid[id] += num;
    }

    public void addOIData(int id, long time, double score, int res) {
        this.cnt += score*100;
        this.score[id] = score;
        this.penalty += time;
        this.pid[id] = 1;
    }

    public void addSCData(int id, int size, int res) {
        if (res == 0) {
            this.cnt++;
            this.penalty += size;
            this.pid[id] = size;
        } else {
            this.pid[id] = -1;
        }
        this.date[id] = 1;
    }

    public double[] getScore() {
        return score;
    }

    public void setScore(double[] score) {
        this.score = score;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getPid() {
        return pid;
    }

    public void setPid(int[] pid) {
        this.pid = pid;
    }

    public long[] getDate() {
        return date;
    }

    public void setDate(long[] date) {
        this.date = date;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
