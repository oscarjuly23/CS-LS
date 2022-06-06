package Model;

import List.ListPaed;

public class BoundingBox {

    //Ponemos una variable que se encarga de gestionar las id de todas las Bounding Box para que no hayan repetidas
    private static Integer inInc = 0;

    //La id de la Box
    private Integer idBB;
    //Una lista que tiene los limites de esta Box
    private ListPaed<Integer[]> limits;
    //Una lista que tiene todas las Positions dentro de unos limites
    private ListPaed<Object> positions;

    private Boolean isFinal;

    public BoundingBox (Boolean isFinal) {
        //Asignamos la id correspondiente
        idBB = inInc;
        //Aumentamos la id
        inInc++;
        //Creamos una nueva lista de tamaño 2
        limits = new ListPaed<>(2);
        //Creamos una nueva lista de positions
        positions = new ListPaed<>();
        //Declaramos si es raiz o no
        this.isFinal = isFinal;
    }

    public Integer getIdBB() {
        return idBB;
    }
    public ListPaed<Object> getPositions() {
        return positions;
    }
    public ListPaed<Integer[]> getLimits() {
        return limits;
    }
    public Boolean getIsFinal() {return isFinal;}
    public void setFinal(Boolean aFinal) {
        isFinal = aFinal;
    }

    /**
     * Se le pasan los limites que tiene la Bounding Box para crearla y poder añadir las Positions que esten dentro
     * @param X1 Componente X de arriba a la izquierda
     * @param Y1 Componente Y de arriba a la izquierda
     * @param X2 Componente X de abajo a la derecha
     * @param Y2 Componente Y de abajo a la derecha
     */
    public void addLimits(Integer X1, Integer Y1, Integer X2, Integer Y2) {
        ListPaed<Integer[]> coords = new ListPaed<>();
        coords.add(new Integer[]{X1, Y1});
        coords.add(new Integer[]{X2, Y2});
        this.limits = coords;
    }

    public void setLimits (ListPaed<Integer[]> limits) {
        this.limits = limits;
    }
    public void addPosition(Object position) {
        positions.add(position);
    }
    public Boolean isFull() { return positions.size() == 3;}
}