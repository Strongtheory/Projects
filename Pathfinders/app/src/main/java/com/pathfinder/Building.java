package com.pathfinder;

import org.json.JSONObject;

/**
 * Class representing a building on GT Campus
 * Connor Reeder
 */

public class Building implements FilterableItem {
    private double latitude;
    private double longitude;
    private String name;
    private String address;
    private long id;

    public Building(String name, String address, double latitude, double longitude, int id) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
    }
    public Building (JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("name");
            this.address = jsonObject.getString("address");
            this.latitude = jsonObject.getDouble("latitude");
            this.longitude = jsonObject.getDouble("longitude");
            this.id = jsonObject.getInt("id");
        } catch (Exception ex) {

        }

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String valueToFiler() {
        return name;
    }
}
