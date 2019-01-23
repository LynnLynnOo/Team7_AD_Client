package com.example.adteam7.team7_ad_client.data;

public class EmployeeDto {
    private String Id;
    private String Name;

    public EmployeeDto(String id, String name) {
        Id = id;
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
