package com.example.adteam7.team7_ad_client.data;

import java.util.List;

public class DelegateDepHeadApiModel {
    private String StartDate;
    private String EndDate;
    private String UserId;
    private String DelegatedDepartmentHeadName;
    private String DepartmentName;
    private List<EmployeeDto> Employees;

    public DelegateDepHeadApiModel(String startDate, String endDate, String userId, String delegatedDepartmentHeadName, String departmentName, List<EmployeeDto> employees) {
        StartDate = startDate;
        EndDate = endDate;
        UserId = userId;
        DelegatedDepartmentHeadName = delegatedDepartmentHeadName;
        DepartmentName = departmentName;
        Employees = employees;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDelegatedDepartmentHeadName() {
        return DelegatedDepartmentHeadName;
    }

    public void setDelegatedDepartmentHeadName(String delegatedDepartmentHeadName) {
        DelegatedDepartmentHeadName = delegatedDepartmentHeadName;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public List<EmployeeDto> getEmployees() {
        return Employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        Employees = employees;
    }
}
