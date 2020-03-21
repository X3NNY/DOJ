package pers.dreamer.dto;

import java.util.Date;

public class NewContestDto {
    private String title;
    private Date startTime;
    private Date endsTime;
    private String desc;
    private Integer level;
    private String password;
    private Integer type;
    private Integer[] problems;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndsTime() {
        return endsTime;
    }

    public void setEndsTime(Date endsTime) {
        this.endsTime = endsTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer[] getProblems() {
        return problems;
    }

    public void setProblems(Integer[] problems) {
        this.problems = problems;
    }
}
