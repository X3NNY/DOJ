package pers.dreamer.bean;

import java.io.Serializable;

public class Code implements Serializable {
    private Integer sid;

    private String text;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }
}