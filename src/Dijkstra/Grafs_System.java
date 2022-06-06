package Dijkstra;

import Dijkstra.Model.Connection;
import Dijkstra.Model.Path;
import Dijkstra.Model.Pathable;
import Dijkstra.Model.Room;
import List.ListPaed;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Grafs_System {

    private static int nodeInici;
    private static int nodeDesti;
    public static Connection[] connections;
    public static Room[] rooms;
    private static Graf graf;

    public Grafs_System () {
    }

    public static void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-- CS/LS --\n Punt de sortida (node inicial):" );
        nodeInici = scanner.nextInt();
        System.out.println(" Punt de destí (node desti): ");
        nodeDesti = scanner.nextInt();
        long timeInicial = System.currentTimeMillis();
        importJson();
        treatData();
        long timeFinal = System.currentTimeMillis();
        System.out.println("Importat en " + (timeFinal - timeInicial) + "ms.");
        timeInicial = System.currentTimeMillis();
        Dijkstra grafSystem = new Dijkstra();
        Path bestPath = grafSystem.dijkstra(graf, nodeInici, nodeDesti);
        timeFinal = System.currentTimeMillis();
        System.out.println("Dijkstra en " + (timeFinal - timeInicial) + "ms.");
        readPath(bestPath);
    }


    private static void readPath(Path path){
        ListPaed<Pathable> p = path.getPath();
        System.out.println("\n\nBest Path: " + nodeInici + " -> " + nodeDesti + "\n" + "From " + rooms[nodeInici].getRoomName());
        for (int i = 0; i < p.size(); i++)  {
            if (p.get(i) instanceof Room){
                System.out.println("\t to " + ((Room) p.get(i)).getRoomName());
            } else if (p.get(i) instanceof Connection){
                System.out.println("\t\twith " + ((Connection) p.get(i)).getConnectionName()
                        + "( " + ((Connection) p.get(i)).getEnemyProbability() + "% )");
            }
        }
        System.out.println();
    }

    private static void treatData() {
        Integer[] connected;
        graf = new Graf(rooms.length);
        for (Room r: rooms){
            graf.setNode(r);
        }
        for (Connection c: connections) {
            connected = c.getRoomConnected();
            for (Integer i: connected){
                graf.addConnection(c,i);
            }
        }
    }

    private static void importJson() {
        Gson gson = new Gson();
        String pathConnection = "data/ConnectionS.json";
        String pathRoom = "data/RoomS.json";
        BufferedReader bufferedReaderConnection;
        BufferedReader bufferedReaderRoom;
        try {
            bufferedReaderConnection = new BufferedReader(new FileReader(pathConnection));
            bufferedReaderRoom = new BufferedReader(new FileReader(pathRoom));
            connections = gson.fromJson(bufferedReaderConnection, Connection[].class);
            rooms = gson.fromJson(bufferedReaderRoom, Room[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}