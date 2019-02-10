package com.example.adteam7.team7_ad_client.model;

public class StationeryRetrievalApiModel {
    public String ItemId;

    public String Description;

    public String DepartmentId;

    public String DepartmentName;

    public String Location;

    public String Category;

    public int NeededQuantity;

    public int NewQuantity;

    public int QuantityInWarehouse;

    public String Remarks;

    public StationeryRetrievalApiModel(String itemId, String description, String departmentId, String departmentName, String location, String category, int neededQuantity, int newQuantity, int quantityInWarehouse, String remarks) {
        ItemId = itemId;
        Description = description;
        DepartmentId = departmentId;
        DepartmentName = departmentName;
        Location = location;
        Category = category;
        NeededQuantity = neededQuantity;
        NewQuantity = newQuantity;
        QuantityInWarehouse = quantityInWarehouse;
        Remarks = remarks;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String departmentId) {
        DepartmentId = departmentId;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getNeededQuantity() {
        return NeededQuantity;
    }

    public void setNeededQuantity(int neededQuantity) {
        NeededQuantity = neededQuantity;
    }

    public int getNewQuantity() {
        return NewQuantity;
    }

    public void setNewQuantity(int newQuantity) {
        NewQuantity = newQuantity;
    }

    public int getQuantityInWarehouse() {
        return QuantityInWarehouse;
    }

    public void setQuantityInWarehouse(int quantityInWarehouse) {
        QuantityInWarehouse = quantityInWarehouse;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }
}
