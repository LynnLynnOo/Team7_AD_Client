package com.example.adteam7.team7_ad_client.data;

import java.util.List;

/**
 * Created by dodo
 **/
public class ManageDepRep {


    String departmentId;
    String departmentname;
    String departmentRepName;
    String departmentRepId;

    List<Employee> employees;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public String getDepartmentRepName() {
        return departmentRepName;
    }

    public void setDepartmentRepName(String departmentRepName) {
        this.departmentRepName = departmentRepName;
    }

    public String getDepartmentRepId() {
        return departmentRepId;
    }

    public void setDepartmentRepId(String departmentRepId) {
        this.departmentRepId = departmentRepId;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
