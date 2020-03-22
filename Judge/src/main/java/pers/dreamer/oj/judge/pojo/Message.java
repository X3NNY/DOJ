package pers.dreamer.oj.judge.pojo;

import java.util.Date;

public class Message {
    private Integer mid;

    private Integer senduid;

    private Integer targetuid;

    private Date date;

    private String content;

    private Integer status;

    private String title;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getSenduid() {
        return senduid;
    }

    public void setSenduid(Integer senduid) {
        this.senduid = senduid;
    }

    public Integer getTargetuid() {
        return targetuid;
    }

    public void setTargetuid(Integer targetuid) {
        this.targetuid = targetuid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }
}