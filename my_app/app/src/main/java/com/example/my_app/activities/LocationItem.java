package com.example.my_app.activities;

public class LocationItem {
    private String city, street, house, type, workingHours;

    public LocationItem(String city, String street, String house, String type, String workingHours) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.type = type;
        this.workingHours = workingHours;
    }

    public String getCity() { return city; }
    public String getStreet() { return street; }
    public String getHouse() { return house; }
    public String getType() { return type; }
    public String getWorkingHours() { return workingHours; }
}
