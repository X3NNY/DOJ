package pers.dreamer.dto;

public class ResultDto { //评测结果
    private Integer sid;
    private Integer result;
    private Integer timeUsed;
    private Integer memoryUsed;
    private Integer pos; //如果错误 第几组
    private Double score;
    private String msg;

    @Override
    public String toString() {
        return "ResultDto{" +
                "sid=" + sid +
                ", result=" + result +
                ", timeUsed=" + timeUsed +
                ", memoryUsed=" + memoryUsed +
                ", pos=" + pos +
                ", score=" + score +
                ", msg='" + msg + '\'' +
                '}';
    }

    public ResultDto() {
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(Integer timeUsed) {
        this.timeUsed = timeUsed;
    }

    public Integer getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(Integer memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
