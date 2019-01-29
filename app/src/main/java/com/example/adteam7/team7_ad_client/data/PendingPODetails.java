package com.example.adteam7.team7_ad_client.data;

import android.util.Log;

import com.example.adteam7.team7_ad_client.network.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PendingPODetails extends HashMap<String, String> {

//
//    static String host = "192.168.1.71";
//    static String baseURL;
//
//    static {
//        baseURL = String.format("http://%s/team7ad/api", host);
//    }

    //region From TransactionDetail

//    private String ItemId;
//    private int Quantity;
//    private String Remarks;
//    private String TransactionRef;
//    private Double UnitPrice;

    //endregion

    //region From Pending PO

//    private String PONo;
//    private String SupplierId;
//    private String Status;
//    private String OrderedBy;
//    private String Date;
//    private Double Amount;
//    private Double UnitAmount;

    //endregion

    public PendingPODetails(String Description, String ItemId, String Quantity, String Remarks, String TransactionRef, String UnitPrice, String PONo, String SupplierId, String Status, String OrderedBy, String Date, String Amount, String UnitAmount) {
//        this.ItemId = ItemId;
//        this.Quantity = Quantity;
//        this.Remarks = Remarks;
//        this.TransactionRef = TransactionRef;
//        this.UnitPrice = UnitPrice;
//        this.PONo = PONo;
//        this.SupplierId = SupplierId;
//        this.Status = Status;
//        this.OrderedBy = OrderedBy;
//        this.Date = Date;
//        this.Amount = Amount;
//        this.UnitAmount = UnitAmount;
        put("Description", Description);
        put("ItemId", ItemId);
        put("Quantity", Quantity);
        put("Remarks", Remarks);
        put("TransactionRef", TransactionRef);
        put("UnitPrice", UnitPrice);
        put("PONo", PONo);
        put("SupplierId", SupplierId);
        put("Status", Status);
        put("OrderedBy", OrderedBy);
        put("Date", Date);
        put("Amount", Amount);
        put("UnitAmount", UnitAmount);

    }

//    public String getItemId() {
//        return ItemId;
//    }
//
//    public void setItemId(String itemId) {
//        ItemId = itemId;
//    }
//
//    public int getQuantity() {
//        return Quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        Quantity = quantity;
//    }
//
//    public String getRemarks() {
//        return Remarks;
//    }
//
//    public void setRemarks(String remarks) {
//        Remarks = remarks;
//    }
//
//    public String getTransactionRef() {
//        return TransactionRef;
//    }
//
//    public void setTransactionRef(String transactionRef) {
//        TransactionRef = transactionRef;
//    }
//
//    public Double getUnitPrice() {
//        return UnitPrice;
//    }
//
//    public void setUnitPrice(Double unitPrice) {
//        UnitPrice = unitPrice;
//    }
//
//    public String getPONo() {
//        return PONo;
//    }
//
//    public void setPONo(String PONo) {
//        this.PONo = PONo;
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
//    public String getStatus() {
//        return Status;
//    }
//
//    public void setStatus(String status) {
//        Status = status;
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
//    public String getDate() {
//        return Date;
//    }
//
//    public void setDate(String date) {
//        Date = date;
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
//    public Double getUnitAmount() {
//        return UnitAmount;
//    }
//
//    public void setUnitAmount(Double unitAmount) {
//        UnitAmount = unitAmount;
//    }

//    public static List<PendingPODetails> GetPendingPODetails(String pono) {
//        String url = String.format("%s/pendingpo/%s", baseURL, pono);
//        List<PendingPODetails> listPendingPO = new ArrayList<>();
//        try {
//            JSONArray a = JSONParser.getJSONArrayFromUrl(url);
//            for (int i = 0; i < a.length(); i++) {
//                JSONObject b = a.getJSONObject(i);
//                listPendingPO.add(new PendingPODetails(
//                        b.getString("Description"),
//                        b.getString("ItemId"),
//                        b.getString("Quantity"),
//                        b.getString("Remarks"),
//                        b.getString("TransactionRef"),
//                        String.format("%,.2f", b.getDouble("UnitPrice")),
//                        b.getString("PONo"),
//                        b.getString("SupplierId"),
//                        b.getString("Status"),
//                        b.getString("OrderedBy"),
//                        b.getString("Date"),
//                        String.format("%,.2f", b.getDouble("Amount")),
//                        String.format("%,.2f", b.getDouble("UnitAmount"))));
//            }
//        } catch (Exception e) {
//            Log.e("PendingPODetails", "JSONArray error");
//        }
//        return (listPendingPO);
//    }

}

