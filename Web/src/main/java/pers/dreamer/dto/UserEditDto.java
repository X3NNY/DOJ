package pers.dreamer.dto;

public class UserEditDto {

    private String password;
    private String newpassword;
    private String school;
    private String note;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "UserEditDto{" +
                "password='" + password + '\'' +
                ", newpassword='" + newpassword + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
