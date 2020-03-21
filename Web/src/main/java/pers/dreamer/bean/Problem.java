package pers.dreamer.bean;

import java.io.Serializable;

public class Problem implements Serializable {
    private Integer pid;

    private String title;

    private String description;

    private String input;

    private String output;

    private String sampleinput;

    private String sampleoutput;

    private Integer state;//controller 层返回值 扩展

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "pid=" + pid +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", input='" + input + '\'' +
                ", output='" + output + '\'' +
                ", sampleinput='" + sampleinput + '\'' +
                ", sampleoutput='" + sampleoutput + '\'' +
                ", timelimit=" + timelimit +
                ", memorylimit=" + memorylimit +
                ", type=" + type +
                ", uid=" + uid +
                ", status=" + status +
                ", special=" + special +
                ", hint='" + hint + '\'' +
                '}';
    }

    private Integer timelimit;

    private Integer memorylimit;

    private Integer type;

    private Integer uid;

    private Integer status;

    private Integer special;

    private String hint;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input == null ? null : input.trim();
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output == null ? null : output.trim();
    }

    public String getSampleinput() {
        return sampleinput;
    }

    public void setSampleinput(String sampleinput) {
        this.sampleinput = sampleinput == null ? null : sampleinput.trim();
    }

    public String getSampleoutput() {
        return sampleoutput;
    }

    public void setSampleoutput(String sampleoutput) {
        this.sampleoutput = sampleoutput == null ? null : sampleoutput.trim();
    }

    public Integer getTimelimit() {
        return timelimit;
    }

    public void setTimelimit(Integer timelimit) {
        this.timelimit = timelimit;
    }

    public Integer getMemorylimit() {
        return memorylimit;
    }

    public void setMemorylimit(Integer memorylimit) {
        this.memorylimit = memorylimit;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSpecial() {
        return special;
    }

    public void setSpecial(Integer special) {
        this.special = special;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint == null ? null : hint.trim();
    }
}