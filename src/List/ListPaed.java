package List;

public class ListPaed <T> {
    private T[] list;
    private Integer listSize;

    /**
     * Creamos un constructor que crea un array de Objetos vacío de la clase que le pasamos
     */
    public ListPaed() {
        list = (T[]) new Object[0];
        listSize = 0;
    }

    /**
     * Creamos un constructor que crea un array de Objetos del tamaño que le pasan de la clase que le pasamos
     */
    public ListPaed(Integer size) {
        list = (T[]) new Object[size];
        listSize = size;
    }

    /**
     * Añadimos un objeto de una clase a nuestra lista
     * @param t Clase que queremos añadir a esta clase
     */
    public void add(T t) {
        T[] aux = (T[]) new Object[list.length + 1];

        for (int i = 0; i < list.length; i++) {
            aux[i] = list[i];
        }
        aux[list.length] = t;

        list = aux;
        listSize = list.length;
    }

    /**
     * Añadimos un objeto de una clase a nuestra lista
     * @param t Clase que queremos añadir a esta clase
     */
    public void addAll(ListPaed<T> t) {

        T[] aux = (T[]) new Object[list.length + t.listSize];

        for (int i = 0; i < list.length; i++) {
            aux[i] = list[i];
        }
        for (int i = 0; i < t.listSize; i++) {
            aux[list.length + i] = t.get(i);
        }

        list = aux;
        listSize = list.length;
    }

    public void addAll2(T[] t) {

        T[] aux = (T[]) new Object[list.length + t.length];

        for (int i = 0; i < list.length; i++) {
            aux[i] = list[i];
        }

        for (int i = 0; i < t.length; i++) {
            aux[list.length + i] = t[i];
        }
        list = aux;
        listSize = list.length;
    }

    /**
     *
     * @param position posicion que queremos devolver
     * @return damos el objeto que esta en la posición que nos han pedido
     */
    public T get(int position) { return list[position]; }

    /**
     * Eliminamos el objeto que nos piden
     * @param position posicion donde esta el objeto que se quiere eliminar
     */
    public void remove(int position) {
        if (position < 0 || position >= list.length) return;

        T[] aux = (T[]) new Object[list.length - 1];

        boolean found = false;

        for (int i = 0; i < list.length; i++) {
            if (i == position) {
                found = true;
                continue;
            }
            aux[i - (found ? 1 : 0)] = list[i];
        }

        list = aux;
        listSize = list.length;
    }

    public Integer size() { return listSize; }

    public void clear() {
        Integer size = listSize;
        if (size > 0) {
            for (int i = size - 1; i >= 0; i--) {
                remove(i);
            }
        }
    }
    public void put (T t, int i) {
        list[i] = t;
    }
    public void put (Integer i, int i2) {
        list[i2] = (T) i;
    }

    public boolean contains(T t) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(t)){
                return true;
            }
        }
        return false;
    }
}