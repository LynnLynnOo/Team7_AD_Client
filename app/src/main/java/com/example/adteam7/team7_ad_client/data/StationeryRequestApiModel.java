package com.example.adteam7.team7_ad_client.data;

import java.util.List;

public class StationeryRequestApiModel {
    private String RequestId;
    private String RequestedBy;
    private String ApprovedBy;
    private String DepartmentId;
    private String Status;
    private String RequestDate;
    private List<RequestTransactionDetailApiModel> requestTransactionDetailApiModelList;

    public StationeryRequestApiModel(String requestId, String requestedBy, String approvedBy, String departmentId, String status, String requestDate, List<RequestTransactionDetailApiModel> requestTransactionDetailApiModelList) {
        RequestId = requestId;
        RequestedBy = requestedBy;
        ApprovedBy = approvedBy;
        DepartmentId = departmentId;
        Status = status;
        RequestDate = requestDate;
        this.requestTransactionDetailApiModelList = requestTransactionDetailApiModelList;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getRequestedBy() {
        return RequestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        RequestedBy = requestedBy;
    }

    public String getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedBy(String approvedBy) {
        ApprovedBy = approvedBy;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String departmentId) {
        DepartmentId = departmentId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(String requestDate) {
        RequestDate = requestDate;
    }

    public List<RequestTransactionDetailApiModel> getRequestTransactionDetailApiModelList() {
        return requestTransactionDetailApiModelList;
    }

    public void setRequestTransactionDetailApiModelList(List<RequestTransactionDetailApiModel> requestTransactionDetailApiModelList) {
        this.requestTransactionDetailApiModelList = requestTransactionDetailApiModelList;
    }
}
