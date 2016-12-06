package com.roomfinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by connor on 11/16/16.
 */

public class Node {
    private String id;
    private double latitude;
    private double longitude;
    private int floor;
    private String[] rooms;

    public Node(String id, double latitude, double longitude, int floor, String[] rooms) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.floor = floor;
        this.rooms = rooms;
    }
    public Node (JSONObject jsonObject) {
        try {
            try {
                JSONArray roomArr = jsonObject.getJSONArray("rooms");
                String[] rooms = new String[roomArr.length()];
                for (int j = 0; j < roomArr.length(); j++) {
                    rooms[j] = roomArr.getString(j);
                }
                this.rooms = rooms;
            } catch (JSONException ex) {
                this.rooms = new String[0];
            }

            this.id = jsonObject.getString("id");
            this.latitude = jsonObject.getDouble("latitude");
            this.longitude = jsonObject.getDouble("longitude");
            this.floor = jsonObject.getInt("floor");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String[] getRooms() {
        return rooms;
    }

    public void setRooms(String[] rooms) {
        this.rooms = rooms;
    }
}
