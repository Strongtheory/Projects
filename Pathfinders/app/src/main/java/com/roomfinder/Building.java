package com.roomfinder;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a building on GT Campus
 * Connor Reeder
 */

public class Building implements FilterableItem {
    private static final String TAG = "Building";
    private double latitude;
    private double longitude;
    private String name;
    private String address;
    private long id;
    private String url;
    private Entrance[] entrances;

    public Building(String name, String address, double latitude, double longitude, long id, String url, Entrance[] entrances) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.url = url;
        this.entrances = entrances;
    }
    public Building (JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("name");
            this.address = jsonObject.getString("address");
            this.latitude = jsonObject.getDouble("latitude");
            this.longitude = jsonObject.getDouble("longitude");
            this.id = jsonObject.getInt("id");
            this.url = jsonObject.getString("imageURL");
            try {
                JSONArray arr = jsonObject.getJSONArray("entrances");
                this.entrances = new Entrance[arr.length()];
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject entrObj = arr.getJSONObject(i);
                    entrances[i] = new Entrance(entrObj.getString("node"), entrObj.getString("name"));
                }
            } catch (JSONException ex) {
                //Log.d(TAG, "No entrances for building: " + name);
                this.entrances = new Entrance[0];
            }

        } catch (Exception ex) {
            ex.printStackTrace();
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

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Entrance[] getEntrances() {
        return entrances;
    }

    public void setEntrances(Entrance[] entrances) {
        this.entrances = entrances;
    }

    @Override
    public String valueToFiler() {
        return name;
    }

    @Override
    public String toString() {
        return "{id: " + id + ", name: " + name + ", address: " + address + "}";
    }
}
