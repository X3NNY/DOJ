package pers.dreamer.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class NoticeDto {
/**
 * "num": int, //题目序号,额外项-1 代表针对本场比赛也不是单个题
 *     "question": str,
 *     "date": str,//
 */
    private Integer num;
    private String question;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date date;
    private String answer;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "NoticeDto{" +
                "num=" + num +
                ", question='" + question + '\'' +
                ", date=" + date +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
