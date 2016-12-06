package com.roomfinder;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Class representing a room in a building
 * Connor Reeder
 */

public class Room implements Parcelable, FilterableItem {
    private String node;
    private String roomNumber;

    public Room (String node, String roomNumber) {
        this.node = node;
        this.roomNumber = roomNumber;;
    }

    public Room (JSONObject jsonObject) {
        try {
            this.node = jsonObject.getString("node");
            this.roomNumber = jsonObject.getString("roomNumber");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    public Room (Parcel in) {
        this.node = in.readString();
        this.roomNumber = in.readString();
    }

    public static final Parcelable.Creator<Room> CREATOR
            = new Parcelable.Creator<Room>() {
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(node);
        out.writeString(roomNumber);
    }

    @Override
    public String toString() {
        return "{node: " + node + ", roomNumber: " + roomNumber + "}";
    }
}
