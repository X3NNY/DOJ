package pers.dreamer.dto;

public class StatusDto {

    private Integer pid;
    private String username;
    private Integer res;
    private String lang;
    private Integer num;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRes() {
        return res;
    }

    public void setRes(Integer res) {
        this.res = res;
    }


    public Integer getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "StatusDto{" +
                "pid=" + pid +
                ", username='" + username + '\'' +
                ", res=" + res +
                ", lang='" + lang + '\'' +
                ", num=" + num +
                '}';
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }


}
