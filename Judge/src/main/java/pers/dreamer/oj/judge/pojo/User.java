package pers.dreamer.oj.judge.pojo;

import java.util.Date;

public class User {
    private Integer uid;

    private String username;

    private String password;

    private String invitecode;

    private String note;

    private String image;

    private Integer coins;

    private String school;

    private String email;

    private Integer level;

    private Integer status;

    private Integer rating;

    private Date registerdate;

    private Integer maxscore;

    private Date lastvisit;

    private Integer role;

    private Integer liveness;

    private Integer preaid;

    private Integer sufaid;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getInvitecode() {
        return invitecode;
    }

    public void setInvitecode(String invitecode) {
        this.invitecode = invitecode == null ? null : invitecode.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Date getRegisterdate() {
        return registerdate;
    }

    public void setRegisterdate(Date registerdate) {
        this.registerdate = registerdate;
    }

    public Integer getMaxscore() {
        return maxscore;
    }

    public void setMaxscore(Integer maxscore) {
        this.maxscore = maxscore;
    }

    public Date getLastvisit() {
        return lastvisit;
    }

    public void setLastvisit(Date lastvisit) {
        this.lastvisit = lastvisit;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getLiveness() {
        return liveness;
    }

    public void setLiveness(Integer liveness) {
        this.liveness = liveness;
    }

    public Integer getPreaid() {
        return preaid;
    }

    public void setPreaid(Integer preaid) {
        this.preaid = preaid;
    }

    public Integer getSufaid() {
        return sufaid;
    }

    public void setSufaid(Integer sufaid) {
        this.sufaid = sufaid;
    }
}