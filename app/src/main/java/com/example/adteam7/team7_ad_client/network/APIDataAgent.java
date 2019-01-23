package com.example.adteam7.team7_ad_client.network;

import com.example.adteam7.team7_ad_client.data.DelegateDepHeadApiModel;
import com.example.adteam7.team7_ad_client.data.ManageDepRep;

/**
 * Created by dodo
 **/
public interface APIDataAgent {

    String login(String nam,String pss);

    void getEmpList();
    ManageDepRep delegateDepHeadGet();
    String delegateDepHeadSet(ManageDepRep dep);
    void assignDepRep();

    DelegateDepHeadApiModel delegateActingDepHeadGet();


}
