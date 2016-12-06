package com.roomfinder;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Class to represent a single building entrance
 * Connor Reeder
 */

public class Entrance implements Parcelable, FilterableItem{
    private String node;
    private String name;

    public Entrance(String node, String name) {
        this.node = node;
        this.name = name;
    }
    public Entrance(Parcel in) {
        node = in.readString();
        name = in.readString();
    }
    public Entrance(JSONObject jsonObject) {
        try {
            this.node = jsonObject.getString("node");
            this.name = jsonObject.getString("name");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final Parcelable.Creator<Entrance> CREATOR
            = new Parcelable.Creator<Entrance>() {
        public Entrance createFromParcel(Parcel in) {
            return new Entrance(in);
        }

        public Entrance[] newArray(int size) {
            return new Entrance[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(node);
        out.writeString(name);
    }

    @Override
    public String valueToFiler() {
        return name;
    }

    @Override
    public String toString() {
        return "{node: " + node + ", name: " + name + "}";
    }
}
