package pers.dreamer.dto;

public class TagDto {
    private String tag;

    private int cnt;

    private int state; // 是否当前用户添加

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void addCnt() {
        this.cnt++;
    }
}
