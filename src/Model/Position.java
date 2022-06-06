package Model;

import List.ListPaed;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Position {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("X1")
    @Expose
    private Integer x1;
    @SerializedName("Y1")
    @Expose
    private Integer y1;
    @SerializedName("X2")
    @Expose
    private Integer x2;
    @SerializedName("Y2")
    @Expose
    private Integer y2;

    private Integer[] middlePoint = new Integer[2];

    public Position() {
    }

    public Position(Integer id, Integer x1, Integer y1, Integer x2, Integer y2) {
        this.id = id;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        calculateMP();
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getX1() {
        return x1;
    }
    public Integer getY1() {
        return y1;
    }
    public Integer getX2() {
        return x2;
    }
    public Integer getY2() {
        return y2;
    }

    public void calculateMP() {
        int diffY = (y1-y2)/2 + y2;
        int diffX = (x2-x1)/2 + x1;
        middlePoint[0] = diffX;
        middlePoint[1] = diffY;
    }

    public Double calculateDistance(Position position) {
        return Math.sqrt(Math.pow(this.middlePoint[0]-position.middlePoint[0], 2) + Math.pow(this.middlePoint[1]-position.middlePoint[1], 2));
    }

    public ListPaed<Integer[]> getLimits () {
        ListPaed<Integer[]> list = new ListPaed<>();
        list.add(new Integer[]{this.getX1(), this.getY1()});
        list.add(new Integer[]{this.getX2(), this.getY2()});
        return list;
    }

    @Override
    public String toString() {
        return "Position id=" + id +
                ", x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2;
    }
}