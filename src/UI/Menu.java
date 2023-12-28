package UI;

import ArbreCoords.ArbreCoords;
import ArbrePreus.ArbrePreus;
import Dijkstra.Grafs_System;
import HashMap.HashMap;

import java.util.Scanner;

public class Menu {
    public void start() {

        int option;
        boolean out = false;
        Scanner sn = new Scanner(System.in);

        while (!out) {
            System.out.println("1. Dijkstra");
            System.out.println("2. Arbre de preus botiga");
            System.out.println("3. Arbre coordenades");
            System.out.println("4. HashMap");
            System.out.println("5. Sortir");
            System.out.println();
            System.out.println("Selecciona una opció:");
            option = sn.nextInt();

            switch (option) {
                case 1: // Dijkstra
                    Grafs_System gs = new Grafs_System();
                    gs.start();
                    break;

                case 2: // B Tree - Preus botiga
                    ArbrePreus arbrePreus = new ArbrePreus();
                    arbrePreus.start();
                    break;

                case 3: // R Tree - Coordenades objectes
                    ArbreCoords ac = new ArbreCoords();
                    ac.start();
                    break;

                case 4: // HashMap
                    HashMap hm = new HashMap();

                    hm.start();
                    break;

                case 5: // Sortir
                    out = true;
                    //System.exit(0);
                    break;

                default:
                    System.out.println("Has de introduir un número entre 1 i 5");
            }
        }
    }
}