package pers.dreamer.dto;

import java.util.Date;

public class ContestDto {

    private Integer cid;
    private Date starttime;
    private Integer score;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    @Override
    public String toString() {
        return "ContestDto{" +
                "cid=" + cid +
                ", starttime=" + starttime +
                ", score=" + score +
                ", rank=" + rank +
                ", sum=" + sum +
                ", addscore=" + addscore +
                '}';
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getAddscore() {
        return addscore;
    }

    public void setAddscore(Integer addscore) {
        this.addscore = addscore;
    }

    private Integer rank;
    private Integer sum;
    private Integer addscore;

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
