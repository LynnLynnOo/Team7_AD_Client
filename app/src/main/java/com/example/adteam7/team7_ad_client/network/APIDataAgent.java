package com.example.adteam7.team7_ad_client.network;

import com.example.adteam7.team7_ad_client.model.AckDeliveryDetails;
import com.example.adteam7.team7_ad_client.model.DelegateDepHeadApiModel;
import com.example.adteam7.team7_ad_client.model.Disbursement;
import com.example.adteam7.team7_ad_client.model.DisbursementSationeryItem;
import com.example.adteam7.team7_ad_client.model.ManageDepRep;
import com.example.adteam7.team7_ad_client.model.PendingPO;
import com.example.adteam7.team7_ad_client.model.PendingPODetails;
import com.example.adteam7.team7_ad_client.model.ReturnItemPostBack;
import com.example.adteam7.team7_ad_client.model.StationeryRequestApiModel;
import com.example.adteam7.team7_ad_client.model.StationeryRetrievalApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kay thi swe tun
 **/
public interface APIDataAgent {

    String login(String nam,String pss);
    ManageDepRep delegateDepHeadGet();
    String assignDepRep(ManageDepRep dep);
    List<Disbursement> getDisbbyDept();
    List<DisbursementSationeryItem> getDisbDetail(String disbno);

    String voidDisbursement(String list);

    String ackDisbursement(List<DisbursementSationeryItem> items);

    String returnSingleItem(ReturnItemPostBack item);

    String returnAllItem(List<ReturnItemPostBack> item);

    //region Author Teh Li Heng
    String delegateActingDepHeadSet(DelegateDepHeadApiModel del);
    DelegateDepHeadApiModel delegateActingDepHeadGet();

    String delegateActingDepHeadRevoke();
    ArrayList<StationeryRetrievalApiModel> RetrievalListGet();

    String RetrievalListSet(List<StationeryRetrievalApiModel> models);
    //endregion

    //region Author GJX
    /*method for approve request*/
    StationeryRequestApiModel GetStationeryRequest(String requestId);
    String ApproveStationeryRequest(StationeryRequestApiModel request);
    String RejectStationeryRequest(StationeryRequestApiModel request);
    List<StationeryRequestApiModel> ReadStationeryRequest();

    //endregion

    //region Author Zan Tun Khine

    List<PendingPODetails> GetPendingPODetails(String pono);

    List<String> GetPendingPOList();

    List<PendingPO> GetPendingPO();

    void ApproveRejectPO(PendingPO po, int btn);

    void ConfirmDO(List<AckDeliveryDetails> ackDOList);


    //endregion
}
