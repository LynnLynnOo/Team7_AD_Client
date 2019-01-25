package com.example.adteam7.team7_ad_client.network;

import com.example.adteam7.team7_ad_client.data.DelegateDepHeadApiModel;
import com.example.adteam7.team7_ad_client.data.ManageDepRep;
import com.example.adteam7.team7_ad_client.data.StationeryRequestApiModel;
import com.example.adteam7.team7_ad_client.data.StationeryRetrievalApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dodo
 **/
public interface APIDataAgent {

    String login(String nam,String pss);


    ManageDepRep delegateDepHeadGet();

    String delegateDepHeadSet(ManageDepRep dep);
    void assignDepRep();

    String delegateActingDepHeadSet(DelegateDepHeadApiModel del);
    DelegateDepHeadApiModel delegateActingDepHeadGet();

    /*method for approve request*//*
    /* Create by GJX*/
    StationeryRequestApiModel GetStationeryRequest(String requestId);
    ArrayList<StationeryRetrievalApiModel> RetrievalListGet();

    List<StationeryRequestApiModel> ReadStationeryRequest();
    String RetrievalListSet(List<StationeryRetrievalApiModel> models);
}
