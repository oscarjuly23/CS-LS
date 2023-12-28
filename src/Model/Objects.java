package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Objects {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private int price;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}