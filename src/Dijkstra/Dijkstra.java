package Dijkstra;

import Dijkstra.Model.Adjacent;
import Dijkstra.Model.Node;
import Dijkstra.Model.Path;
import List.ListPaed;


public class Dijkstra {
    public Path dijkstra (Graf graf, int nodeInicial, int nodeFinal){
        // Iniciem variables
        double newProb;
        Node nodeActual = graf.getNode(nodeInicial);
        ListPaed<Node> nextNode = new ListPaed<>();

        //Iniciamos los paths
        Path[] allPaths =  new Path[graf.length];
        for (int i = 0; i < allPaths.length; i++) {
            allPaths[i] = new Path();
        }
        allPaths[nodeInicial].setProbability(0);

        while (!nodesVisitats(graf)){ //Revisem tot el graf fins que estiguin tots els nodes revisats
            ListPaed<Adjacent> adjacents = nodeActual.getAdjacents();
            for (int i = 0; i < adjacents.size(); i++) {
                Adjacent adj = adjacents.get(i); //Mirem tots els adjacents
                Node nodeAdj = graf.getGraf()[adj.getIdNode()];
                if (nodeAdj.equals(graf.getNode(nodeInicial))) continue;
                //Apuntem els nous nodes adjacents per visitarlos mes tard
                if (!nodeAdj.isVisitat() && !nextNode.contains(nodeAdj)) nextNode.add(nodeAdj);
                //Calculem la suma provabilistiac per el nou camí
                newProb = allPaths[nodeActual.getIdNode()].getProbability()
                        + (1 - allPaths[nodeActual.getIdNode()].getProbability() * 0.01) * adj.getProbability();

                if (allPaths[adj.getIdNode()].getProbability() > newProb) { //Revisem si el nou cami es bo
                    allPaths[adj.getIdNode()].setProbability(newProb); //Canviem per la nova provabilitat
                    //Eliminem el cami antic
                    allPaths[adj.getIdNode()].removePaths();
                    //Apuntem el nou path
                    allPaths[adj.getIdNode()].addPaths(allPaths[nodeActual.getIdNode()].getPath());
                    allPaths[adj.getIdNode()].addPath(adj.getNodeAdj());
                    allPaths[adj.getIdNode()].addPath(adj.getConAdj());
                }

            }
            nodeActual.setVisitat(true); //Apuntem els nodes que ja hem visitat
            if (nextNode.size() == 0){ // Mirem quin és el node per revisar
                break;
            }
            nodeActual = nextNode.get(0);
            nextNode.remove(0);

        }
        return allPaths[nodeFinal]; //Retornem el camí final per arribar al node desti
    }


    private boolean nodesVisitats(Graf graf){ //Comprova que tots els nodes del graf hagin sigut revisats
        for (Node n: graf.getGraf()) {
            if (!n.isVisitat()) return false;
        }
        return true;
    }
}