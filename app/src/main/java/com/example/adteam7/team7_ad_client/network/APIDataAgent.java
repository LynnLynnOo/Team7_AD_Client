package com.example.adteam7.team7_ad_client.network;

import com.example.adteam7.team7_ad_client.data.ManageDepRep;

/**
 * Created by dodo
 **/
public interface APIDataAgent {

    boolean login(String nam,String pss);

    void getEmpList();
    ManageDepRep delegateDepHeadGet();
    void delegateDepHeadSet();
    void assignDepRep();


}
