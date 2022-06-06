package Dijkstra.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Connection extends Pathable {

    @SerializedName("connection_name")
    @Expose
    private String connectionName;
    @SerializedName("room_connected")
    @Expose
    private Integer[] roomConnected;
    @SerializedName("enemy_probability")
    @Expose
    private Integer enemyProbability;

    public String getConnectionName() {
        return connectionName;
    }

    public Integer[] getRoomConnected() {
        return roomConnected;
    }

    public Integer getEnemyProbability() {
        return enemyProbability;
    }
}