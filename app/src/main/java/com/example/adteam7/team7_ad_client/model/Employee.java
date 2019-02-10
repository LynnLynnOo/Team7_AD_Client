package com.example.adteam7.team7_ad_client.model;

/**
 * Created by Kay Thi Swe Tun
 **/
public class  Employee {

    String name;
    String empid;
    String email;
    String phone;

    public Employee(String name, String empid, String email, String phone) {
        this.name = name;
        this.empid = empid;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }
}
