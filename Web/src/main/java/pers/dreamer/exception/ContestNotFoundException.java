package pers.dreamer.exception;

public class ContestNotFoundException extends Exception {

    public String message = "无法找到该比赛";

    public ContestNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public ContestNotFoundException() {

    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
