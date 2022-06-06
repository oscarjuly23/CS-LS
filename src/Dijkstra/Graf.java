package Dijkstra;

import Dijkstra.Model.Adjacent;
import Dijkstra.Model.Connection;
import Dijkstra.Model.Node;
import Dijkstra.Model.Room;

public class Graf {
    public int length;
    private final Node[] graf;

    public Graf(int length) {
        graf = new Node[length];
        this.length = length;
    }

    public Node[] getGraf() {
        return graf;
    }

    public void setNode(Room r) {
        graf[r.getId()] = new Node(r);
    }

    public Node getNode (int i){
        return graf[i];
    }

    public void addConnection(Connection c, Integer i) {
        for (Integer a :c.getRoomConnected()){
            if (!a.equals(i)) graf[i].addAdjacent(new Adjacent(a,c.getId(),c.getEnemyProbability()));
        }
    }
}