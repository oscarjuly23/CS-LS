package Dijkstra.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Room extends Pathable {

    @SerializedName("room_name")
    @Expose
    private String roomName;

    public String getRoomName() {
        return roomName;
    }
}