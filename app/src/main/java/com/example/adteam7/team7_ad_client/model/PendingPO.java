package com.example.adteam7.team7_ad_client.model;

import java.util.HashMap;

public class PendingPO extends HashMap<String, String> {

    //region Zan Tun Khine

//    static String host = "192.168.1.71";
//    static String baseURL;
//
//    static {
//        baseURL = String.format("http://%s/team7ad/api", host);
//    }


//    private String PONo;
//    private String SupplierId;
//    private String Status;
//    private String OrderedBy; // Need to confirm
//    private String Date;
//    private Double Amount;
//    private String ApprovedBy;
//
//    public PendingPO() {
//    }

    public PendingPO(String PONo, String SupplierId, String Status, String OrderedBy, String Date, String Amount, String ApprovedBy) {

//        this.PONo = PONo;
//        this.SupplierId = SupplierId;
//        this.Status = Status;
//        this.OrderedBy = OrderedBy;
//        this.Date = Date;
//        this.Amount = Amount;
//        this.ApprovedBy = ApprovedBy;
        put("PONo", PONo);
        put("SupplierId", SupplierId);
        put("Status", Status);
        put("OrderedBy", OrderedBy);
        put("Date", Date);
        put("Amount", Amount);
        put("ApprovedBy", ApprovedBy);
    }

//    public String getPONo() {
//        return PONo;
//    }
//
//    public void setPONo(String PONo) {
//        this.PONo = PONo;
//    }
//
//    public String getStatus() {
//        return Status;
//    }
//
//    public void setStatus(String status) {
//        Status = status;
//    }
//
//    public String getDate() {
//        return Date;
//    }
//
//    public String getSupplierId() {
//        return SupplierId;
//    }
//
//    public void setSupplierId(String supplierId) {
//        SupplierId = supplierId;
//    }
//
//    public String getOrderedBy() {
//        return OrderedBy;
//    }
//
//    public void setOrderedBy(String orderedBy) {
//        OrderedBy = orderedBy;
//    }
//
//    public Double getAmount() {
//        return Amount;
//    }
//
//    public void setAmount(Double amount) {
//        Amount = amount;
//    }
//
//    public String getApprovedBy() {
//        return ApprovedBy;
//    }
//
//    public void setApprovedBy(String approvedBy) {
//        ApprovedBy = approvedBy;
//    }
//
//    public static List<PendingPO> GetPendingPO() {
//        String url = String.format("%s/pendingpo", baseURL);
//        List<PendingPO> listPO = new ArrayList<>();
//        try {
//            JSONArray a = JSONParser.getJSONArrayFromUrl(url);
//            for (int i = 0; i < a.length(); i++) {
//                JSONObject b = a.getJSONObject(i);
//                listPO.add(new PendingPO(
//                        b.getString("PONo"),
//                        b.getString("SupplierId"),
//                        b.getString("Status"),
//                        b.getString("OrderedBy"),
//                        b.getString("Date"),
//                        b.getString("Amount"),
//                        b.getString("ApprovedBy")));
//            }
//        } catch (Exception e) {
//            Log.e("PendingPO", "JSONArray error");
//        }
//        return (listPO);
//    }
//
//
//    public static void ApproveRejectPO(PendingPO po, int btn) {
//        JSONObject jpo = new JSONObject();
//
//        try {
//            jpo.put("PONo", po.get("PONo"));
//            //jpo.put("SupplierId",po.get("SupplierId"));
//            //jpo.put("Status",po.get("Status"));
//            //jpo.put("OrderedBy",po.get("OrderedBy"));
//            //jpo.put("Date",po.get("Date"));
//            //jpo.put("Amount",po.get("Amount"));
//            jpo.put("ApprovedBy", po.get("ApprovedBy"));
//
//        } catch (Exception e) {
//            Log.e("PendingPO", "Error");
//        }
//
//        if (btn == R.id.poButtonApprove)
//            JSONParser.postStream1(baseURL + "/pendingpo/approve", jpo.toString());
//
//        else if (btn == R.id.poButtonReject)
//            JSONParser.postStream1(baseURL + "/pendingpo/reject", jpo.toString());
//    }
    //endregion
}
