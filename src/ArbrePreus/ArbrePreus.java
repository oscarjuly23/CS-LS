package ArbrePreus;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

// B Tree
public class ArbrePreus {
    private int num = 0;
    private int preuCerca;
    private Node arrel;
    private Object objecteCerca;
    private Object[] jsonDataArr;
    private boolean flagNodeEliminat = false;

    public void start() {
        importData();
        omplirArbre();
        inordre(arrel);
        while (preuCerca != -1) {
            preuCerca = 0;
            objecteCerca = null;
            System.out.print("\n\n--------------------------------- Shop ---------------------------------\n Introdueix -1 per tornar al menu principal. Si es torna al menu i posteriorment es torna a entrar a la opcio 2 larbre es tornara a omplir.\n Introdueix el preu de l'objecte que vols: ");
            Scanner scanner = new Scanner(System.in);
            preuCerca = scanner.nextInt();
            long inicio = System.currentTimeMillis();
            if (preuCerca != -1) {
                if (arrel != null) {
                    System.out.println("Inordre aband de treure l'element:");
                    num = 0;
                    inordre(arrel);
                    if (num == 0){
                        System.out.println("L'arbre esta buit");
                    }
                    buscarNode(arrel);
                    if (objecteCerca == null) {
                        System.out.println("No s'ha trobat cap element amb aquest preu.\n");
                    } else {
                        System.out.println("\nL'objecte que has comprat es: " + objecteCerca.toString() + "\n");
                        System.out.println("Inordre despres de treure l'element:");
                        if (num != 1) {
                            num = 0;
                            inordre(arrel);
                        } else {
                            System.out.println("L'arbre esta buit");
                        }
                    }
                    objecteCerca = null;
                    flagNodeEliminat = false;
                } else {
                    System.out.println("L'arbre esta buit");
                }
            }
            long fin = System.currentTimeMillis();
            double tiempo = (double) ((fin - inicio));
            System.out.println(tiempo +" milisegundos");
        }
    }

    private void inordre (Node node){
        if(node.getFillEsquerra() != null){
            inordre(node.getFillEsquerra());
        }
        if (node.getObject() != null) {
            System.out.println("\t" + num + "\t" + node.getObject().toString());
            System.out.println("\t\t - Pare:" +  (node.getPare() == null ? null : node.getPare().getObject().toString()));
            System.out.println("\t\t - Altura: " + node.getAltura());
            num++;
        }
        if(node.getFillDret() != null){
            inordre(node.getFillDret());
        }
    }

    private void setAltures (Node node){
        if(node.getFillEsquerra() != null){
            setAltures(node.getFillEsquerra());
        }
        if(node.getFillDret() != null){
            setAltures(node.getFillDret());
        }
        updateHeight(node);
    }

    void updateHeight(Node n) {
        n.setAltura(1 + Math.max(height(n.getFillEsquerra()), height(n.getFillDret())));
    }

    int height(Node n) {
        return n == null ? -1 : n.getAltura();
    }

    private void importData() {
        try {
            Gson gson = new Gson();
            BufferedReader bufferedReaderConnection = new BufferedReader(new FileReader("./data/dataset_ObjectS.json"));
            jsonDataArr = gson.fromJson(bufferedReaderConnection, Object[].class);
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void omplirArbre(){
        arrel = new Node(jsonDataArr[0]);
        for (int i = 1; i < jsonDataArr.length; i++) {
            addObject(jsonDataArr[i], arrel);
        }
        setAltures(arrel);
    }

    private void addObject(Object object, Node arrel){
        Node aux = arrel;
        boolean flag = false;
        while (!flag){
            if(object.getPrice() < aux.getPrice() && aux.getFillEsquerra() == null){
                aux.setFillEsquerra(new Node(object, aux));
                aux.getFillEsquerra().setPare(aux);
                flag = true;
            } else if (object.getPrice() < aux.getPrice()){
                aux = aux.getFillEsquerra();
            }
            if (!flag && object.getPrice() > aux.getPrice() && aux.getFillDret() == null){
                aux.setFillDret(new Node(object, aux));
                aux.getFillDret().setPare(aux);
                flag = true;
            } else if(!flag && object.getPrice() > aux.getPrice()){
                aux = aux.getFillDret();
            }
        }
    }

    private void buscarNode(Node node) {
        if (node.getPrice() == preuCerca){
            objecteCerca = node.getObject();
            eliminarNode(node);
            flagNodeEliminat = true;
            setAltures(arrel);
        } else if(node.getPrice() > preuCerca && !flagNodeEliminat){
            if (node.getFillEsquerra() != null)
                buscarNode(node.getFillEsquerra());
        } else if (node.getPrice() < preuCerca && !flagNodeEliminat){
            if (node.getFillDret() != null)
                buscarNode(node.getFillDret());
        }
    }

    private void eliminarNode(Node node) {
        Node auxPare = node.getPare();
        if(node.getFillEsquerra() == null && node.getFillDret() == null){
            if (auxPare != null) {
                if (auxPare.getFillEsquerra() != null && auxPare.getFillEsquerra().getPrice() == preuCerca) {
                    auxPare.setFillEsquerra(null);
                } else {
                    auxPare.setFillDret(null);
                    node.setObject(null);
                }
            } else {
                arrel = null;
            }
            node = null;
        } else if (node.getFillEsquerra() != null && node.getFillDret() == null) {
            if (auxPare != null) {
                if (auxPare.getFillEsquerra() != null && auxPare.getFillEsquerra().getPrice() == preuCerca) {
                    auxPare.setFillEsquerra(node.getFillEsquerra());
                } else {
                    auxPare.setFillDret(node.getFillEsquerra());
                }
                node.getFillEsquerra().setPare(auxPare);
            } else {
                arrel = node.getFillEsquerra();
                arrel.setPare(null);
            }
            node = null;
        } else if(node.getFillEsquerra() == null && node.getFillDret() != null) {
            if (auxPare != null) {
                if (auxPare.getFillEsquerra() != null && auxPare.getFillEsquerra().getPrice() == preuCerca) {
                    auxPare.setFillEsquerra(node.getFillDret());
                } else {
                    auxPare.setFillDret(node.getFillDret());
                }
                node.getFillDret().setPare(auxPare);
            } else {
                arrel = node.getFillDret();
                arrel.setPare(null);
            }
            node = null;
        } else if (node.getFillEsquerra() != null && node.getFillDret() != null){
            Object objSubstitucio = getSuccessorInordre(node.getFillDret());
            node.setObject(objSubstitucio);
        }
    }

    private Object getSuccessorInordre(Node node) {
        if(node.getFillEsquerra() != null){
            return getSuccessorInordre(node.getFillEsquerra());
        }
        Node auxPare = node.getPare();
        if (node.getFillDret() == null) {
            if (auxPare.getFillDret() != null && auxPare.getFillDret().getPrice() == node.getPrice()) {
                auxPare.setFillDret(null);
            } else {
                auxPare.setFillEsquerra(null);
            }
        } else {
            if (auxPare.getFillDret() != null && auxPare.getFillDret().getPrice() == node.getPrice()) {
                auxPare.setFillDret(node.getFillDret());
            } else {
                auxPare.setFillEsquerra(node.getFillDret());
            }
            node.getFillDret().setPare(auxPare);
        }
        Object object = node.getObject();
        node = null;
        return object;
    }
}