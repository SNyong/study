package test.test.domain;

public class Member {

    private int num;
    private String id;
    private String pw;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    @Override
    public String toString() {
        return "회원 정보 " +
                "num = " + num +
                ", id = " + id +
                ", pw = " + pw;
    }
}
