package com.example.adteam7.team7_ad_client.model;

import java.util.List;

public class SetRetrievalApiModel {
    public String UserId;
    List<StationeryRetrievalApiModel> ApiModelList;

    public SetRetrievalApiModel(String userId, List<StationeryRetrievalApiModel> apiModelList) {
        UserId = userId;
        this.ApiModelList = apiModelList;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public List<StationeryRetrievalApiModel> getApiModelList() {
        return ApiModelList;
    }

    public void setApiModelList(List<StationeryRetrievalApiModel> apiModelList) {
        this.ApiModelList = apiModelList;
    }
}
