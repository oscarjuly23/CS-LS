package ArbrePreus;

public class Object {
    private int price;
    private String name;

    public Object(int price, String name) {
        this.price = price;
        this.name = name;
    }

    public int getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Object{" +
                "price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}