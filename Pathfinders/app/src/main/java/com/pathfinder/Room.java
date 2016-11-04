package com.pathfinder;

import org.json.JSONObject;

/**
 * Class representing a room in a building
 * Connor Reeder
 */

public class Room implements FilterableItem {
    private Long id;
    private Long buildingId;
    private String roomNumber;

    public Room (long id, long buildingId, String roomNumber) {
        this.id = id;
        this.buildingId = buildingId;
        this.roomNumber = roomNumber;;
    }

    public Room (JSONObject jsonObject) {
        try {
            this.id = jsonObject.getLong("id");
            this.buildingId = jsonObject.getLong("buildingId");
            this.roomNumber = jsonObject.getString("roomNumber");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String valueToFiler() {
        return roomNumber;
    }
}
