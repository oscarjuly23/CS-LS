package HashMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("KDA")
    @Expose
    private Integer kDA;
    @SerializedName("Games")
    @Expose
    private Integer games;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getKDA() {
        return kDA;
    }
    public void setKDA(Integer kDA) {
        this.kDA = kDA;
    }
    public Integer getGames() {
        return games;
    }
    public void setGames(Integer games) {
        this.games = games;
    }

    @Override
    public String toString() {
      return name + ": \n\t-KDA: " + kDA.toString() +  "\n\t-Games: " + games.toString();
    }
}
