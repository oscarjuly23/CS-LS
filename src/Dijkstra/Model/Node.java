package Dijkstra.Model;


import List.ListPaed;

public class Node{
    private final int idNode;
    private final ListPaed<Adjacent> adjacents;
    private boolean visitat;

    public Node(Room room) {
        this.idNode = room.getId();
        this.adjacents = new ListPaed<>();
        this.visitat = false;
    }

    public int getIdNode() {
        return idNode;
    }

    public ListPaed<Adjacent> getAdjacents() {
        return adjacents;
    }

    public void addAdjacent(Adjacent adjacent){
        this.adjacents.add(adjacent);
    }

    public boolean isVisitat() {
        return visitat;
    }

    public void setVisitat(boolean visitat) {
        this.visitat = visitat;
    }
}