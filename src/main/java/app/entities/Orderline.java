package app.entities;

public class Orderline {

    Bottom bottom;
    Topping topping;
    int quantity;
    double totalPrice;

    public Orderline(Bottom bottom, Topping topping, int quantity, double totalPrice) {
        this.bottom = bottom;
        this.topping = topping;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Bottom getBottom() {
        return bottom;
    }

    public Topping getTopping() {
        return topping;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
