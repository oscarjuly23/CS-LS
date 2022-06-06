package ArbrePreus;

public class Node {
    private Object object;
    private Node fillEsquerra;
    private Node fillDret;
    private Node pare;
    private int altura = 0;

    public Node(Object object) {
        this.object = object;
    }
    public Node(Object object, Node pare) {
        this.object = object;
    }
    public Object getObject() {
        return object;
    }
    public void setObject(Object object) {
        this.object = object;
    }
    public Node getFillEsquerra() {
        return fillEsquerra;
    }
    public void setFillEsquerra(Node fillEsquerra) {
        this.fillEsquerra = fillEsquerra;
    }
    public Node getFillDret() {
        return fillDret;
    }
    public void setFillDret(Node fillDret) {
        this.fillDret = fillDret;
    }
    public int getPrice() {
        return this.object.getPrice();
    }
    public Node getPare() {
        return pare;
    }
    public void setPare(Node pare) {
        this.pare = pare;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    @Override
    public String toString() {
        return "Node{" +
                "object=" + object +
                ", pare=" + pare +
                ", fillEsquerra=" + fillEsquerra +
                ", fillDret=" + fillDret +
                '}';
    }

    public void print(){
        System.out.println("Node{" + "object=" + object + ", fillEsquerra=" + fillEsquerra + ", fillDret=" + fillDret + " , pare=" + pare.getObject() + "}");
    }

    String myToString(Integer nivell, String omplem) {
        for (int i = 0; i < nivell; i++) {
            omplem = omplem.concat("\t");
        }
        omplem = omplem.concat(object.toString() + "\n");
        if (fillEsquerra != null) {
            for (int i = 0; i < nivell; i++) {
                omplem = omplem.concat("\t");
            }
            omplem = omplem.concat("Esquerre: " + fillEsquerra.myToString(++nivell, omplem) + "\n");
        }
        if (fillDret != null) {
            for (int i = 0; i < nivell; i++) {
                omplem = omplem.concat("\t");
            }
            omplem = omplem.concat("Dret: " + fillDret.myToString(++nivell, omplem) + "\n");
        }
        return omplem;
    }
}