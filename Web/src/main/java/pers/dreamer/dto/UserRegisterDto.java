package pers.dreamer.dto;

public class UserRegisterDto {

    private Integer step;
    private String checkcode;
    private String email;
    private String invitecode;
    private String vercode;
    private String username;
    private String password;
    private String school;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVercode() {
        return vercode;
    }

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }

    public Integer getStep() {
        return step;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRegisterDto{" +
                "step=" + step +
                ", checkcode='" + checkcode + '\'' +
                ", email='" + email + '\'' +
                ", invitecode='" + invitecode + '\'' +
                ", vercode=" + vercode +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", school='" + school + '\'' +
                '}';
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInvitecode() {
        return invitecode;
    }

    public void setInvitecode(String invitecode) {
        this.invitecode = invitecode;
    }
}
