package pers.dreamer.bean;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private Integer cid;

    private Integer uid;

    private String content;

    private Date date;

    private Integer preuid;

    private Integer upVote;

    private Integer downVote;

    private Integer pid;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPreuid() {
        return preuid;
    }

    public void setPreuid(Integer preuid) {
        this.preuid = preuid;
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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}