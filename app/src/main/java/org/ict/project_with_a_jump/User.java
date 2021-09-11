package org.ict.project_with_a_jump;

public class User {
    String name; // 사용자의 이름
    String num; // 사용자의 개인안심번호
    String address; // 사용자의 간단한 거주지 주소

    public User() {
    }

    public User(String name, String num, String address) { // 사용자 정보 저장
        this.name = name;
        this.num = num;
        this.address = address;
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

    public void setNum(String num) {
        this.num = num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString() {
        return "User{" +
                "Name='" + name + '\'' +
                ", Number='" + num + '\'' +
                ", Address='" + address + '\'' +
                '}';
    }
}