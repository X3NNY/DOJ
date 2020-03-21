package pers.dreamer.dto;

import java.io.Serializable;
import java.util.Date;

public class SubmitDto implements Serializable {

    private Long num;
    private Date date;

    public SubmitDto() {

    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
