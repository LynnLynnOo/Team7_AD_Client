package com.example.adteam7.team7_ad_client.network;

import com.example.adteam7.team7_ad_client.data.DelegateDepHeadApiModel;
import com.example.adteam7.team7_ad_client.data.Disbursement;
import com.example.adteam7.team7_ad_client.data.DisbursementSationeryItem;
import com.example.adteam7.team7_ad_client.data.ManageDepRep;
import com.example.adteam7.team7_ad_client.data.StationeryRetrievalApiModel;

import java.util.ArrayList;

import java.util.List;

/**
 * Created by dodo
 **/
public interface APIDataAgent {

    String login(String nam,String pss);


    ManageDepRep delegateDepHeadGet();

    String assignDepRep(ManageDepRep dep);
    List<Disbursement> getDisbbyDept();
    List<DisbursementSationeryItem> getDisbDetail(String disbno);

    String delegateActingDepHeadSet(DelegateDepHeadApiModel del);
    DelegateDepHeadApiModel delegateActingDepHeadGet();

    ArrayList<StationeryRetrievalApiModel> RetrievalListGet();
}
