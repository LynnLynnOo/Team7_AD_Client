package com.example.adteam7.team7_ad_client.data;

import java.util.Date;

/**
 * Created by dodo
 **/
public class Disbursement {


   private String DisbursementId;
   private String DisbursementNo;
   private String DepartmentId;
   private String DepartmentName;
   private String AcknowledgedBy;
   private String DisbursedBy;
   private Date Date;
   private String RequestId;
   private String Status;
   private String OTP;


    public Disbursement(String disbursementId, String disbursementNo, String departmentId, String departmentName, String acknowledgedBy, String disbursedBy, Date date, String requestId, String status, String OTP) {
        DisbursementId = disbursementId;
        DisbursementNo = disbursementNo;
        DepartmentId = departmentId;
        DepartmentName = departmentName;
        AcknowledgedBy = acknowledgedBy;
        DisbursedBy = disbursedBy;
        Date = date;
        RequestId = requestId;
        Status = status;
        this.OTP = OTP;
    }

    public Disbursement() {
    }

    public String getDisbursementId() {
        return DisbursementId;
    }

    public void setDisbursementId(String disbursementId) {
        DisbursementId = disbursementId;
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

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
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
}
