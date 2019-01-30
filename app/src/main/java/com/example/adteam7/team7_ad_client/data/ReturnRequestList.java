package com.example.adteam7.team7_ad_client.data;

import java.util.List;

/**
 * Created by dodo
 **/
public class ReturnRequestList {
    private String RequestId;
    private String DepartmentName;
    private List<ReturnItem> detaillist;

    public ReturnRequestList(String requestId, String departmentName, List<ReturnItem> detaillist) {
        RequestId = requestId;
        DepartmentName = departmentName;
        this.detaillist = detaillist;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public List<ReturnItem> getDetaillist() {
        return detaillist;
    }

    public void setDetaillist(List<ReturnItem> detaillist) {
        this.detaillist = detaillist;
    }
}
