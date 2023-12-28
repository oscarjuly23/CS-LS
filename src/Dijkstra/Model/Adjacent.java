package Dijkstra.Model;

import Dijkstra.Grafs_System;

public class Adjacent {
    private final int idNode;
    private final int idConnection;
    private final double probability;

    public Adjacent(int idNode, int idConnection, double probability) {
        this.idNode = idNode;
        this.idConnection = idConnection;
        this.probability = probability;
    }

    public int getIdNode() {
        return idNode;
    }

    public double getProbability() {
        return probability;
    }

    public Pathable getNodeAdj() {
        return Grafs_System.rooms[idNode];
    }

    public Pathable getConAdj() {
        return Grafs_System.connections[idConnection];
    }
}
