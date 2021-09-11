package org.ict.project_with_a_jump;

/**
 * 사용자 계정 정보 모델 클래스
 */
public class Manage {
    private String idToken;     //Firebase Uid(고유 토큰정보)
    private String emailId;
    private String password;
    private String name;
    private String birth;
    private String daum1;
    private String daum2;
    private String daum3;
    private String companyName;

    public Manage() {
    }

    public Manage(String idToken, String emailId, String password, String name, String birth, String daum1, String daum2, String daum3, String companyName) {
        this.idToken = idToken;
        this.emailId = emailId;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.daum1 = daum1;
        this.daum2 = daum2;
        this.daum3 = daum3;
        this.companyName = companyName;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getDaum1() {
        return daum1;
    }

    public void setDaum1(String daum1) {
        this.daum1 = daum1;
    }

    public String getDaum2() {
        return daum2;
    }

    public void setDaum2(String daum2) {
        this.daum2 = daum2;
    }

    public String getDaum3() {
        return daum3;
    }

    public void setDaum3(String daum3) {
        this.daum3 = daum3;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}