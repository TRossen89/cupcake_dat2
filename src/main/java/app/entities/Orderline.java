package app.entities;

public class Orderline {

    public int id;
    public String bottom;
    public String topping;
    public int quantity;
    public double totalPrice;

    public Orderline(int id, String bottom, String topping, int quantity, double totalPrice) {
        this.id = id;
        this.bottom = bottom;
        this.topping = topping;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Orderline [id=" + id + ", bottom=" + bottom + ", topping=" + topping + ", quantity=" + quantity
                + ", totalPrice=" + totalPrice + "]";
    }
    
}
