package HashMap;

import List.ListPaed;
import UI.Menu;

import java.util.Scanner;

public class HashMap {
    private HashNode[] hashNodes;
    private ListPaed<Player> playersList = new ListPaed<Player>();
    private int size = 0;

    public HashMap() {

    }

    /**
     * Llegim el datasheet que volem
     * @param datasheet Escull el datasheet
     */
    public void init(Integer datasheet) {
        playersList.clear();
        switch (datasheet) {
            case 1:
                playersList.addAll2(Import.JsonS());
                break;

            case 2:
                playersList.addAll2(Import.JsonM());
                break;

            case 3:
                playersList.addAll2(Import.JsonL());
                break;
        }

        long startTime = System.nanoTime();
        for (int i = 0; i < playersList.size(); i++) {
            put(playersList.get(i));
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        if (size != 100) {
            System.out.println("Ha tardat: " + duration / 1000000 + " milisegons en afergir tots els elements a l'array");
        } else {
            System.out.println("Ha tardat: " + duration / 1000 + " microsegons en afergir tots els elements a l'array");
        }
    }

    public void start() {
        int option;
        boolean out = false;

        Scanner sn = new Scanner(System.in);
        while (!out) {
            size=0;

            System.out.println("1. HashMapS");
            System.out.println("2. HashMapM");
            System.out.println("3. HashMapL");
            System.out.println("4. Sortir");
            System.out.println();
            System.out.println("Selecciona una opció:");
            option = sn.nextInt();

            switch (option) {
                case 1:
                    size = 50;
                    this.hashNodes = new HashNode[size];
                    init(1);
                    break;

                case 2:
                    size = 5000;
                    this.hashNodes = new HashNode[size];
                    init(2);
                    break;

                case 3:
                    size = 500000;
                    this.hashNodes = new HashNode[size];
                    init(3);
                    break;

                case 4:
                    out = true;
                    ;
                    break;

                default:
                    System.out.println("Has de introduir un número entre 1 i 4");
            }

            if (size != 0) {
                float suma = 0;
                for (HashNode element : hashNodes) {
                    if (element == null) {
                        suma++;
                    }
                }
                System.out.println(100f - ((suma / (float) size) * 100f) + "% de caselles ocupades");
                System.out.println();
                cerca();
            }
        }
    }

    /**
     * Busquem un jugador en el nostre HashMap
     */
    private void cerca() {
        while (true) {
            System.out.print("Introdueix el nom del jugador per buscar-lo o 'exit' per sortir: ");
            Scanner sn = new Scanner(System.in);
            String playerName = sn.nextLine();
            if (playerName.equals("exit")) {
                return;
            }
            //Busquem si el jugador que ens demanen esta al HashMap
            Player player = get(playerName);
            //Si esta a null significa que no hem trobat al jugador
            if (player == null) {
                System.out.println("No s'ha trobat");
            } else {
                System .out.println(player.toString() + "\n");
            }
        }
    }

    /**
     * Rebem un jugador i el fiquem al HashMap
     * @param player Jugador que volem ficar al HashMap
     */
    public void put(Player player) {
        //Ens guardem el nom del jugador
        String playerName = player.getName();
        //Creem un nou HashNode amb el nom del jugador i el jugador i sense next
        HashNode entry = new HashNode(playerName, player, null);
        //Controlem que la mida sigui 0
        int aux = getSize() == 0 ? 1 : getSize();
        //Mirem a quina posició esta el jugador
        int bucket = calcularValor(playerName) % aux;
        //Agafem el node que esta a la posició on tindrà que anar el jugador
        HashNode actualNode = hashNodes[Math.abs(bucket)];

        //Si esta null significa que no hi han jugadors que comparteixin aquest hash per tant el nostra nou jugador serà l'unic
        if (actualNode == null) {
            hashNodes[Math.abs(bucket)] = entry;
        } else {
            //Si n'hi han més jugadors mirem que el següent no sigui null
            while (actualNode.getNext() != null) {
                //Si el jugador que hem agafar en la iteració i mirem que no sigui el mateix, si ho es el reemplacem
                if (actualNode.getName().equals(playerName)) {
                    actualNode.setPlayer(player);
                    return;
                }
                //si no esta repetit agafem el següent
                actualNode = actualNode.getNext();
            }
            //Mirem si esta repetit, si ho esta el reemplacem
            if (actualNode.getName().equals(playerName)) {
                actualNode.setPlayer(player);
            } else {
                //Si no esta repetit l'afegim al següent del actual
                actualNode.setNext(entry);
            }
        }
    }

    /**
     * Cerquem un jugador i si el trobem el retornem
     * @param playerName Jugador que volem trobar
     * @return L'objecte jugador que hem trobat
     */
    public Player get(String playerName) {
        //Creem un HashNode nou i agafem la posició a la qual pertany la String que rebem
        HashNode hashNode = hashNodes[Math.abs(calcularValor(playerName) % getSize())];
            //Si aquest node no esta null significa que hi ha un jugador, si esta a null no n'hi ha
            while (hashNode != null) {
                //mirem si el jugador que hem trobat es el mateix que el que ens donen
                if (hashNode.getName().equals(playerName)) {
                    //si es el mateix retornem el player que te el hashNode
                    return hashNode.getPlayer();
                }
                hashNode = hashNode.getNext();
            }
        return null;
    }

    /**
     * Calcula el hash d'un string
     * @param string que volem calcular el seu hash
     * @return hash de la cadena en forma de int
     */
    private Integer calcularValor(String string) {
        byte[] input = string.getBytes();
        int aux = 16777619;
        long hash = Integer.MAX_VALUE;

        for (byte b : input) hash = (hash ^ b) * aux;

        hash += hash << 13;
        hash ^= hash >> 3;
        hash += hash << 7;
        hash ^= hash >> 17;
        hash += hash << 5;
        return (int)Math.abs(hash);
    }

    public int getSize() {
        return size;
    }

}
