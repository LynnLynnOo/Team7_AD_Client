package com.example.adteam7.team7_ad_client.data;

import java.time.LocalDateTime;
import java.util.List;

public class DelegateDepHeadApiModel {
    private LocalDateTime StartDate;
    private LocalDateTime EndDate;
    private String UserId;
    private String DelegatedDepartmentHeadName;
    private String DepartmentName;
    private List<EmployeeDto> Employees;

    public DelegateDepHeadApiModel(LocalDateTime startDate, LocalDateTime endDate, String userId, String delegatedDepartmentHeadName, String departmentName, List<EmployeeDto> employees) {
        StartDate = startDate;
        EndDate = endDate;
        UserId = userId;
        DelegatedDepartmentHeadName = delegatedDepartmentHeadName;
        DepartmentName = departmentName;
        Employees = employees;
    }

    public LocalDateTime getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        StartDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDateTime endDate) {
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
