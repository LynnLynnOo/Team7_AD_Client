package com.example.adteam7.team7_ad_client.network;

/**
 * Created by dodo
 **/
public interface APIDataAgent {

    boolean login(String nam,String pss);

    void getEmpList();
    void delegateDepHead();
    void assignDepRep();


}
