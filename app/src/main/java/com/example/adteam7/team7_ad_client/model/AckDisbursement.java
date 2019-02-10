package com.example.adteam7.team7_ad_client.model;

import java.util.List;

/**
 * Created by Kay Thi Swe Tun
 **/
public class AckDisbursement {


    public String DisbursedBy;
    public List<DisbursementSationeryItem> AckList;

    public AckDisbursement() {
    }

    public AckDisbursement(String disbursedBy, List<DisbursementSationeryItem> ackList) {
        DisbursedBy = disbursedBy;
        AckList = ackList;
    }

    public String getDisbursedBy() {
        return DisbursedBy;
    }

    public void setDisbursedBy(String disbursedBy) {
        DisbursedBy = disbursedBy;
    }

    public List<DisbursementSationeryItem> getAckList() {
        return AckList;
    }

    public void setAckList(List<DisbursementSationeryItem> ackList) {
        AckList = ackList;
    }
}
