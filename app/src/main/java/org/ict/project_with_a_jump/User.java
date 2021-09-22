package org.ict.project_with_a_jump;


public class User {
    private String name; // 사용자의 이름
    private String num; // 사용자의 개인안심번호
    private String address;// 사용자의 간단한 거주지 주소
    private String emailId;
    private String password;
    private String idToken;

    public User(){

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum (String num) {
        this.num = num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public User(String name, String num, String address,String emailId, String password) { // 사용자 정보 저장
        this.name = name;
        this.num = num;
        this.address = address;
        this.emailId = emailId;
        this.password = password;
        this.idToken = idToken;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}