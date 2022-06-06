package ArbreCoords;

import List.ListPaed;
import Model.BoundingBox;
import Model.Position;

import java.util.Arrays;
import java.util.Scanner;

// R Tree
public class ArbreCoords {
    private Integer maxY = 0;
    private Integer maxX = 0;
    private ListPaed<Position> positions = new ListPaed<>();
    private Boolean checkInsertion;

    private BoundingBox BB = new BoundingBox(true);
    private Double worstDistance = 0.0;
    private Integer smallestArea = 10000000;
    private Integer bestJ;
    private Integer bestK;

    private static Integer delete;
    private static Integer delete2;
    private Boolean empty = false;
    private Boolean deleted = false;
    private Integer count = 0;
    private static Integer id;

    private static Boolean ok = false;

    public void start() {
        BB.addLimits(0, 0, 0, 0);
        readJson();
        init();
        MenuR(BB);
    }

    public ArbreCoords() {

    }

    private void readJson() {
        //Json guardado como array
        Position[] position = Import.Json();
        //Json guardado en lista
        for (Position p : position) {
            p.calculateMP();
            positions.add(p);
            if (p.getY1() > maxY) {
                maxY = p.getY1();
            }
            if (p.getX2() > maxX) {
                maxX = p.getX2();
            }
        }
    }

    /**
     * Insertamos todos los objetos del json en el r-Tree
     */
    private void init() {
        Boolean check;
        long inicio = System.currentTimeMillis();
        for (int i = 0; i < positions.size(); i++) {
            //Insertamos una posición en la BB
            check = addPosition(positions.get(i), BB);
            //Si se ha insertado mal
            if (!check) {
                //creamos una BB nueva que no sea final
                BoundingBox BB1 = new BoundingBox(false);
                //calculamos el limite de la BB que teniamos
                ListPaed<Integer[]> list = calcularLimites2(BB);
                BB.addLimits(list.get(0)[0], list.get(0)[1], list.get(1)[0], list.get(1)[1]);
                //añadimos la nueva BB a la antigua y calculamos los nuevos limites
                BB1.addPosition(BB);
                BB1.setLimits(BB.getLimits());
                //añadimos la posicion en la nueva BB para que se guarde bien
                addPosition(positions.get(i), BB1);
            }
            //calculamos los limites de BB
            ListPaed<Integer[]> list = calcularLimites2(BB);
            BB.addLimits(list.get(0)[0], list.get(0)[1], list.get(1)[0], list.get(1)[1]);
            //actualizamos la id del objeto que insertamos
            id = i + 1;
        }
        long fin = System.currentTimeMillis();
        double tiempo = (double) ((fin - inicio));
        System.out.println("Temps en inserir el json " + tiempo +" milisegons\t");
    }

    public void MenuR(BoundingBox BB) {
        int n;
        int option;
        boolean out = false;
        Scanner sn = new Scanner(System.in);

        while (!out) {
            System.out.println("1. Inserir");
            System.out.println("2. Buscar y eliminar");
            System.out.println("3. Mostrar R-Tree");
            System.out.println("4. Enrrere");
            System.out.println();
            System.out.println("Selecciona una opció:");
            option = sn.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Introdueix les dades del objecte");
                    System.out.print("X1: ");
                    Integer X1 = sn.nextInt();
                    System.out.print("Y1: ");
                    Integer Y1 = sn.nextInt();
                    System.out.print("X2: ");
                    Integer X2 = sn.nextInt();
                    System.out.print("Y2: ");
                    Integer Y2 = sn.nextInt();
                    //creamos e insertamos el nuevo objeto
                    Position p = new Position(++id, X1, Y1, X2, Y2);
                    long inicio = System.currentTimeMillis();
                    this.addPosition(p, BB);
                    long fin = System.currentTimeMillis();
                    double tiempo = (double) ((fin - inicio));
                    System.out.println("Temps en insertar un objecte " + tiempo +" milisegons\t");
                    break;

                case 2:
                    System.out.println("Introdueix les coordenades del objecte");
                    System.out.print("X: ");
                    Integer X = sn.nextInt();
                    System.out.print("Y: ");
                    Integer Y = sn.nextInt();
                    inicio = System.currentTimeMillis();
                    //buscamos las coordenadas que nos dan
                    doSearch(X,Y,BB);
                    fin = System.currentTimeMillis();
                    tiempo = (double) ((fin - inicio));
                    System.out.println("Temps en buscar i eliminar un objecte " + tiempo +" milisegons\t");
                    //si se ha encontrado indicamos que se ha encontrado y se ha eliminado
                    if (ok) {
                        System.out.println("\nHit!\n" +
                                "Eliminació amb èxit!\n");

                    } else {
                        System.out.println("\nMiss!\n");
                    }
                    ok = false;
                    deleted = false;
                    empty = false;
                    break;

                case 3:
                    //mostramos el arbol por pantalla
                    inicio = System.currentTimeMillis();
                    mostrarArbol(BB, true);
                    System.out.println("|-----------------------------------------------------");
                    fin = System.currentTimeMillis();
                    tiempo = (double) ((fin - inicio));
                    System.out.println("Temps en mostrar l'arbre " + tiempo +" milisegons\t");
                    break;

                case 4:
                    out = true;
                    break;

                default:
                    System.out.println("Has de introduir un número entre 1 i 4");
                    break;
            }
        }
    }

    /**
     *
     * @param position objeto que se quiere insertar
     * @param bb BoundingBox donde se quiere insertar el objeto
     * @return un Boolean que indica si el objeto se ha insertado bien o no
     */
    private Boolean addPosition (Position position, BoundingBox bb) {
        if (!bb.getIsFinal()) {
            int posicion = 0;
            for (int i = 0; i < bb.getPositions().size(); i++) {
                //miramos en que BB el area crece menos
                if (smallestArea > calculateNewArea((BoundingBox) bb.getPositions().get(i), position)) {
                    smallestArea = calculateNewArea((BoundingBox) bb.getPositions().get(i), position);
                    posicion = i;
                }
                //--------------------------------------------------------------------------------------------
            }
            //reiniciamos el area
            smallestArea = 10000000;
            //miramos si podemos añadir la posicion el la posicion de abajo recursivamente
            checkInsertion = addPosition(position, (BoundingBox) bb.getPositions().get(posicion));
            if (!checkInsertion) {
                //miramos si la bb de abajo es final
                if (((BoundingBox) bb.getPositions().get(posicion)).getIsFinal()) {
                    if (bb.getPositions().size() == 2) {
                        //si ya tiene dos BB creamos dos nuevas BB
                        BoundingBox auxBB;
                        BoundingBox bbnew1 = new BoundingBox(true);
                        //copiamos el interior de la de abajo en una auxiliar
                        auxBB = copyValue(((BoundingBox) bb.getPositions().get(posicion)));
                        //reiniciamos la informacion de la BB de abajo
                        ((BoundingBox) bb.getPositions().get(posicion)).getPositions().clear();
                        ((BoundingBox) bb.getPositions().get(posicion)).getLimits().clear();
                        //añadimos la posicion a la BB auxiliar para poder comparar los objetos
                        auxBB.addPosition(position);
                        //calculamos los limites de esta BB
                        ListPaed<Integer[]> list = calcularLimites2(auxBB);
                        auxBB.addLimits(list.get(0)[0], list.get(0)[1], list.get(1)[0], list.get(1)[1]);
                        //calculamos los nodos mas alejados de la BB
                        calculateDistance(auxBB);
                        //añado un objeto de los que estan mas alejados a la bbnew1
                        bbnew1.addPosition(auxBB.getPositions().get(bestJ));
                        //actualizo los limites
                        bbnew1.addLimits(((Position) auxBB.getPositions().get(bestJ)).getX1(), ((Position) auxBB.getPositions().get(bestJ)).getY1(),
                                ((Position) auxBB.getPositions().get(bestJ)).getX2(), ((Position) auxBB.getPositions().get(bestJ)).getY2());

                        //añado el otro objeto a la BB de abajo
                        ((BoundingBox) bb.getPositions().get(posicion)).addPosition(auxBB.getPositions().get(bestK));
                        //actualizo los limites
                        ((BoundingBox) bb.getPositions().get(posicion)).addLimits(((Position) auxBB.getPositions().get(bestK)).getX1(), ((Position) auxBB.getPositions().get(bestK)).getY1(),
                                ((Position) auxBB.getPositions().get(bestK)).getX2(), ((Position) auxBB.getPositions().get(bestK)).getY2());

                        //miro para que BB el objeto a insetar hace que si area crezca menos
                        for (int n = 0; n < auxBB.getPositions().size(); n++) {
                            if (auxBB.getPositions().get(n) == auxBB.getPositions().get(bestJ) || auxBB.getPositions().get(n) == auxBB.getPositions().get(bestK)) {

                            } else {
                                if (calculateNewArea(bbnew1, (Position) auxBB.getPositions().get(n)) > (calculateNewArea(((BoundingBox) bb.getPositions().get(posicion)), (Position) auxBB.getPositions().get(n)))) {
                                    //añado la nueva posicion a la BB
                                    ((BoundingBox) bb.getPositions().get(posicion)).addPosition(auxBB.getPositions().get(n));
                                    //actualizo limites
                                    list = calcularLimites2((BoundingBox) bb.getPositions().get(posicion));
                                    ((BoundingBox) bb.getPositions().get(posicion)).addLimits(list.get(0)[0], list.get(0)[1], list.get(1)[0], list.get(1)[1]);
                                } else {
                                    //añado la nueva posicion a la BB
                                    bbnew1.addPosition(auxBB.getPositions().get(n));
                                    //actualizo limites
                                    list = calcularLimites2(bbnew1);
                                    bbnew1.addLimits(list.get(0)[0], list.get(0)[1], list.get(1)[0], list.get(1)[1]);
                                }
                            }
                        }
                        //actualizo los limites de las BB
                        list = calcularLimites2((BoundingBox) bb.getPositions().get(posicion));
                        ((BoundingBox) bb.getPositions().get(posicion)).addLimits(list.get(0)[0], list.get(0)[1], list.get(1)[0], list.get(1)[1]);

                        list = calcularLimites2(bbnew1);
                        bbnew1.addLimits(list.get(0)[0], list.get(0)[1], list.get(1)[0], list.get(1)[1]);
                        worstDistance = 0.0;
                        //añado la bbnew1 a la BB donde estoy
                        bb.addPosition(bbnew1);
                        bb.getLimits().clear();
                        //actualizamos los limites
                        list = calcularLimites2(bb);
                        bb.addLimits(list.get(0)[0], list.get(0)[1], list.get(1)[0], list.get(1)[1]);
                    } else {
                        ListPaed<Integer[]> list;
                        //cambiamos el tipo de bb en la que estamos a false ya que no sigue siendo una bb que contiene positions
                        ((BoundingBox) bb.getPositions().get(posicion)).setFinal(false);
                        //creamos dos cajas nuevas que sean finales que es donde los objetos van a ir
                        BoundingBox bbnew1 = new BoundingBox(true);
                        BoundingBox bbnew2 = new BoundingBox(true);
                        //copiamos la BB de abajo en una BB aux
                        BoundingBox auxBB = copyValue(((BoundingBox) bb.getPositions().get(posicion)));
                        //añadimos la posicion a esta lista
                        auxBB.addPosition(position);
                        //actualizamos los limites
                        list = calcularLimites2(auxBB);
                        auxBB.addLimits(list.get(0)[0], list.get(0)[1], list.get(1)[0], list.get(1)[1]);
                        //calculamos los nodos mas alejados
                        calculateDistance(auxBB);
                        //añado el nuevo objeto alejado a la bb1 creada
                        bbnew1.addPosition(auxBB.getPositions().get(bestJ));
                        //actualizo los limites de bbnew1
                        bbnew1.addLimits(((Position) auxBB.getPositions().get(bestJ)).getX1(), ((Position) auxBB.getPositions().get(bestJ)).getY1(),
                                ((Position) auxBB.getPositions().get(bestJ)).getX2(), ((Position) auxBB.getPositions().get(bestJ)).getY2());
                        //Lo mismo pero con bb2
                        bbnew2.addPosition(auxBB.getPositions().get(bestK));
                        bbnew2.addLimits(((Position) auxBB.getPositions().get(bestK)).getX1(), ((Position) auxBB.getPositions().get(bestK)).getY1(),
                                ((Position) auxBB.getPositions().get(bestK)).getX2(), ((Position) auxBB.getPositions().get(bestK)).getY2());


                        //miro para que BB el objeto a insetar hace que si area crezca menos
                        for (int n = 0; n < auxBB.getPositions().size(); n++) {
                            if (auxBB.getPositions().get(n) == auxBB.getPositions().get(bestJ) ||
                                    auxBB.getPositions().get(n) == auxBB.getPositions().get(bestK)) {

                            } else {
                                if (calculateNewArea(bbnew1, (Position) auxBB.getPositions().get(n)) > (calculateNewArea(bbnew2, (Position) auxBB.getPositions().get(n)))) {
                                    //añado la nueva posicion a la BB
                                    bbnew2.addPosition(auxBB.getPositions().get(n));
                                    //actualizo limites
                                    list = calcularLimites2(bbnew2);
                                    bbnew2.addLimits(list.get(0)[0], list.get(0)[1], list.get(1)[0], list.get(1)[1]);
                                } else {
                                    //añado la nueva posicion a la BB
                                    bbnew1.addPosition(auxBB.getPositions().get(n));
                                    //actualizo limites
                                    list = calcularLimites2(bbnew1);
                                    bbnew1.addLimits(list.get(0)[0], list.get(0)[1], list.get(1)[0], list.get(1)[1]);
                                }
                            }
                        }

                        //Limpiamos los objetos que tiene la bb y añadimos las nuevas
                        ((BoundingBox) bb.getPositions().get(posicion)).getPositions().clear();
                        ((BoundingBox) bb.getPositions().get(posicion)).getLimits().clear();
                        ((BoundingBox) bb.getPositions().get(posicion)).addPosition(bbnew1);
                        ((BoundingBox) bb.getPositions().get(posicion)).addPosition(bbnew2);
                        list = calcularLimites2(((BoundingBox) bb.getPositions().get(posicion)));
                        ((BoundingBox) bb.getPositions().get(posicion)).addLimits(list.get(0)[0], list.get(0)[1], list.get(1)[0], list.get(1)[1]);
                        worstDistance = 0.0;
                    }
                }
            }
            return true;
        } else {
            if (!bb.isFull()) {
                //añadimos la posicion
                bb.getPositions().add(position);
                //actualizamos las posiciones
                ListPaed<Integer[]> list = calcularLimites2(bb);
                bb.addLimits(list.get(0)[0], list.get(0)[1], list.get(1)[0], list.get(1)[1]);
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     *
     * @param bb BoundingBox donde se quiere insertar el objeto
     * @param position objeto que se quiere insertar
     * @return nueva area de la BB con el objeto a insertar
     */

    private Integer calculateNewArea (BoundingBox bb, Position position) {
        Integer baseBB;
        Integer alturaBB;
        ListPaed<Integer[]> provisional = new ListPaed<Integer[]>();
        provisional.add(new Integer[]{bb.getLimits().get(0)[0], bb.getLimits().get(0)[1]});
        provisional.add(new Integer[]{bb.getLimits().get(1)[0], bb.getLimits().get(1)[1]});

        //miramos donde esta el nuevo cuadrado, derecha, izquierda, arriba o abajo y cambiamos los limites de la caja para despues calcular el area
        if (position.getX2() > provisional.get(1)[0]) {
            provisional.get(1)[0] = position.getX2();
        }
        if (position.getX1() < provisional.get(0)[0]) {
            provisional.get(0)[0] = position.getX1();
        }
        if (position.getY2() < provisional.get(1)[1]) {
            provisional.get(1)[1] = position.getY2();
        }
        if (position.getY1() > provisional.get(0)[1]) {
            provisional.get(0)[1] = position.getY1();
        }

        baseBB = Math.abs(provisional.get(1)[0] - provisional.get(0)[0]);
        alturaBB = Math.abs(provisional.get(0)[1] - provisional.get(1)[1]);

        //calculamos la nueva area y la devolvemos
        return calculateArea(baseBB, alturaBB);
    }

    /**
     *
     * @param bbToCopy BB que se quiere copiar
     * @return BB copiada
     */
    private BoundingBox copyValue(BoundingBox bbToCopy) {
        BoundingBox bb = new BoundingBox(true);
        for (int i = 0; i < bbToCopy.getPositions().size(); i++) {
            bb.addPosition(bbToCopy.getPositions().get(i));
        }
        return bb;
    }

    private Integer calculateArea (Integer b, Integer h) {
        return b*h;
    }

    /**
     *
     * @param bb BB que se quiere calcular los limites
     * @return Integer[] con los nuevos limites
     */
    private ListPaed<Integer[]> calcularLimites2 (BoundingBox bb) {
        ListPaed<Integer[]> list = new ListPaed<Integer[]>();
        if (bb.getPositions().get(0) instanceof Position) {
            list.add(new Integer[]{((Position) bb.getPositions().get(0)).getX1(), ((Position) bb.getPositions().get(0)).getY1()});
            list.add(new Integer[]{((Position) bb.getPositions().get(0)).getX2(), ((Position) bb.getPositions().get(0)).getY2()});

            //miramos donde esta el nuevo cuadrado, derecha, izquierda, arriba o abajo y cambiamos los limites
            for (int i = 0; i < bb.getPositions().size(); i++) {
                if (((Position) bb.getPositions().get(i)).getX1() < list.get(0)[0]) {
                    list.get(0)[0] = ((Position) bb.getPositions().get(i)).getX1();
                }
                if (((Position) bb.getPositions().get(i)).getY1() > list.get(0)[1]) {
                    list.get(0)[1] = ((Position) bb.getPositions().get(i)).getY1();
                }
                if (((Position) bb.getPositions().get(i)).getX2() > list.get(1)[0]) {
                    list.get(1)[0] = ((Position) bb.getPositions().get(i)).getX2();
                }
                if (((Position) bb.getPositions().get(i)).getY2() < list.get(1)[1]) {
                    list.get(1)[1] = ((Position) bb.getPositions().get(i)).getY2();
                }
            }
        } else {
            list.add(new Integer[]{((BoundingBox) bb.getPositions().get(0)).getLimits().get(0)[0], ((BoundingBox) bb.getPositions().get(0)).getLimits().get(0)[1]});
            list.add(new Integer[]{((BoundingBox) bb.getPositions().get(0)).getLimits().get(1)[0], ((BoundingBox) bb.getPositions().get(0)).getLimits().get(1)[1]});

            //miramos donde esta el nuevo cuadrado, derecha, izquierda, arriba o abajo y cambiamos los limites
            for (int i = 0; i < bb.getPositions().size(); i++) {
                if (((BoundingBox) bb.getPositions().get(i)).getLimits().get(0)[0] < list.get(0)[0]) {
                    list.get(0)[0] = ((BoundingBox) bb.getPositions().get(i)).getLimits().get(0)[0];
                }
                if (((BoundingBox) bb.getPositions().get(i)).getLimits().get(0)[1] > list.get(0)[1]) {
                    list.get(0)[1] = ((BoundingBox) bb.getPositions().get(i)).getLimits().get(0)[1];
                }
                if (((BoundingBox) bb.getPositions().get(i)).getLimits().get(1)[0] > list.get(1)[0]) {
                    list.get(1)[0] = ((BoundingBox) bb.getPositions().get(i)).getLimits().get(1)[0];
                }
                if (((BoundingBox) bb.getPositions().get(i)).getLimits().get(1)[1] < list.get(1)[1]) {
                    list.get(1)[1] = ((BoundingBox) bb.getPositions().get(i)).getLimits().get(1)[1];
                }
            }
        }
        return list;
    }

    /**
     *
     * @param bb BB de la que se quiere calcular los objetos mas alejados
     */

    private void calculateDistance (BoundingBox bb) {
        for (int j = 0; j < bb.getPositions().size(); j++) {
            for (int k = 0; k < bb.getPositions().size(); k++) {
                if (worstDistance.compareTo(((Position) bb.getPositions().get(j)).calculateDistance(((Position) bb.getPositions().get(k)))) < 0) {
                    worstDistance = ((Position) bb.getPositions().get(j)).calculateDistance(((Position) bb.getPositions().get(k)));
                    bestJ = j;
                    bestK = k;
                }
            }
        }
    }

    /**
     * Busqueda por arbol hecha para que se puedan añadir tantas ramas como se quiera
     * @param X posicion X del objeto
     * @param Y posicion Y del objeto
     * @param bb nodo en el que estamos
     */
    private void doSearch(Integer X, Integer Y, BoundingBox bb) {
        if (!bb.getIsFinal()) {
            for (int i = 0; i < bb.getPositions().size(); i++) {
                //miramos si las coordenadas estan dentro de alguna BB
                if (((BoundingBox) bb.getPositions().get(i)).getLimits().get(0)[0] <= X && X <= ((BoundingBox) bb.getPositions().get(i)).getLimits().get(1)[0]) {
                    if (((BoundingBox) bb.getPositions().get(i)).getLimits().get(0)[1] >= Y && Y >= ((BoundingBox) bb.getPositions().get(i)).getLimits().get(1)[1]) {
                        //llamamos a la funcion recursivamente
                        doSearch(X, Y, (BoundingBox) bb.getPositions().get(i));
                        //si lo hemos encontrado y no se ha eliminado
                        if (ok && !deleted) {
                            //mirmaos si la caba de abajo esta vacia al eliminar el objeto
                            empty = delete((BoundingBox) bb.getPositions().get(i));
                            //nos guardamos la posicion de la BB
                            delete2 = i;
                            return;
                        }
                        if (empty) {
                            empty = false;
                            //eliminamos la BB vacia
                            ((BoundingBox) bb.getPositions().get(i)).getPositions().remove(delete2);
                            //si la BB actual esta vacia
                            if (bb.getPositions().size() == 0) {
                                empty = true;
                                return;
                            } else {
                                //actualizamos los limites
                                ListPaed<Integer[]> list = calcularLimites2(bb);
                                bb.addLimits(list.get(0)[0], list.get(0)[1], list.get(1)[0], list.get(1)[1]);
                            }
                        }
                        if (ok) return;
                    }
                }
            }
        } else {
            //miramos si nuestras coordenadas corresponden a un objeto
            for (int j = 0; j < bb.getPositions().size(); j++) {
                if (((Position) bb.getPositions().get(j)).getX1() <= X && X <= ((Position) bb.getPositions().get(j)).getX2()) {
                    if (((Position) bb.getPositions().get(j)).getY1() >= Y && Y >= ((Position) bb.getPositions().get(j)).getY2()) {
                        //nos guardamos la posicion del objeto
                        ok = true;
                        delete = j;
                        return;
                    }
                }
            }
        }
    }

    /**
     *
     * @param prePosition BB que esta por encima del objeto a eliminar
     * @return Boolean por si la BB que tiene abajo esta vacia
     */
    private Boolean delete(BoundingBox prePosition) {
        //eliminamos el objeto de la lista
        prePosition.getPositions().remove(delete);
        deleted = true;
        //miramos si la BB esta vacia sino actualizamos los limites
        if (prePosition.getPositions().size() == 0) {
            empty = true;
        } else {
            ListPaed<Integer[]> list = calcularLimites2(prePosition);
            prePosition.addLimits(list.get(0)[0], list.get(0)[1], list.get(1)[0], list.get(1)[1]);
        }
        return empty;
    }

    /**
     *
     * @param bb BB que mostramos lo que tiene dentro
     * @param init indicamos si somos la BB que es el padre
     */

    private void mostrarArbol(BoundingBox bb, Boolean init) {
        String tabsAux = ("|\t");
        if (bb.getIsFinal()) count--;
        for (int i = 0; i < count; i++) {
            tabsAux += tabsAux;
        }
        if (init) {
            System.out.println("BB" + bb.getIdBB() + ": <" + Arrays.toString(bb.getLimits().get(0)) + ", " + Arrays.toString(bb.getLimits().get(1)) + ">------------------------------");
        }
        if (!bb.getIsFinal()) {
            for (int i = 0; i < bb.getPositions().size(); i++) {
                System.out.println(tabsAux + "BB" + ((BoundingBox) bb.getPositions().get(i)).getIdBB() + ": <" + Arrays.toString(((BoundingBox) bb.getPositions().get(i)).getLimits().get(0)) + ", " + Arrays.toString(((BoundingBox) bb.getPositions().get(i)).getLimits().get(1)) + ">----------");
                count++;
                mostrarArbol((BoundingBox) bb.getPositions().get(i), false);
                //System.out.println(tabsAux + "|-----------------------------------------|");
                System.out.println(tabsAux);
                count--;
            }
        } else {
            for (int i = 0; i < bb.getPositions().size(); i++) {
                System.out.println( tabsAux + "|\tObject " + ((Position) bb.getPositions().get(i)).getId() + ": <" + Arrays.toString(((Position) bb.getPositions().get(i)).getLimits().get(0)) + ", " + Arrays.toString(((Position) bb.getPositions().get(i)).getLimits().get(1)) + ">\t|");
            }
            count++;
        }
    }
}