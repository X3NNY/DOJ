package pers.dreamer.bean;

import java.io.Serializable;
import java.util.Date;

public class Blogvote implements Serializable {
    private Integer id;

    private Integer bid;

    private Integer uid;

    private Integer commentid;

    private Integer vote;

    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getCommentid() {
        return commentid;
    }

    public void setCommentid(Integer commentid) {
        this.commentid = commentid;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Blogvote{" +
                "id=" + id +
                ", bid=" + bid +
                ", uid=" + uid +
                ", commentid=" + commentid +
                ", vote=" + vote +
                ", createtime=" + createtime +
                '}';
    }
}