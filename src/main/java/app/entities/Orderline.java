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

    public String getBottom() {
        return bottom;
    }

    public String getTopping() {
        return topping;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
