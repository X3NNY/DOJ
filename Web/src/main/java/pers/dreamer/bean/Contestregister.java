package pers.dreamer.bean;

import java.io.Serializable;
import java.util.Date;

public class Contestregister implements Serializable {
    private Integer id;

    private Integer uid;

    private Integer cid;

    private Date date;

    private Integer rank;

    private Integer status;

    private Integer lastrating;

    private Integer role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLastrating() {
        return lastrating;
    }

    public void setLastrating(Integer lastrating) {
        this.lastrating = lastrating;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}