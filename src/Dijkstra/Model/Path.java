package Dijkstra.Model;

import List.ListPaed;

public class Path {
    private ListPaed<Pathable> path;
    private double probability;

    public Path() {
        this.path = new ListPaed<>();
        this.probability = Double.MAX_VALUE;
    }

    public void addPath(Pathable pathable){
        path.add(pathable);
    }

    public void addPaths(ListPaed<Pathable> pathable){
        path.addAll(pathable);
    }

    public void removePaths(){
        path = new ListPaed<>();
    }

    public ListPaed<Pathable> getPath() {
        return path;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

}