package app.entities;

public class Orderline {

    String bottom;
    String topping;
    int quantity;
    double totalPrice;

    public Orderline(String bottom, String topping, int quantity, double totalPrice) {
        this.bottom = bottom;
        this.topping = topping;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
