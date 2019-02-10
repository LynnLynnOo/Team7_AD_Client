package com.example.adteam7.team7_ad_client.model;

/**
 * Created by Kay Thi Swe Tun
 **/
public class Disbursement {


    private String DisbursementNo;
    private String DepartmentId;
    private String DepartmentName;
    private String EmployeeName;
    private String CollectionDescription;
    private String AcknowledgedBy;
    private String DisbursedBy;
    private String Status;
    private String OTP;

    public Disbursement() {
    }

    public String getDisbursementNo() {
        return DisbursementNo;
    }

    public void setDisbursementNo(String disbursementNo) {
        DisbursementNo = disbursementNo;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String departmentId) {
        DepartmentId = departmentId;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getCollectionDescription() {
        return CollectionDescription;
    }

    public void setCollectionDescription(String collectionDescription) {
        CollectionDescription = collectionDescription;
    }

    public String getAcknowledgedBy() {
        return AcknowledgedBy;
    }

    public void setAcknowledgedBy(String acknowledgedBy) {
        AcknowledgedBy = acknowledgedBy;
    }

    public String getDisbursedBy() {
        return DisbursedBy;
    }

    public void setDisbursedBy(String disbursedBy) {
        DisbursedBy = disbursedBy;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public Disbursement(String disbursementNo, String departmentId, String departmentName, String employeeName, String collectionDescription, String acknowledgedBy, String disbursedBy, String status, String OTP) {
        DisbursementNo = disbursementNo;
        DepartmentId = departmentId;
        DepartmentName = departmentName;
        EmployeeName = employeeName;
        CollectionDescription = collectionDescription;
        AcknowledgedBy = acknowledgedBy;
        DisbursedBy = disbursedBy;
        Status = status;
        this.OTP = OTP;
    }
}
