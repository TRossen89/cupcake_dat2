package app.entities;

public class Orderline {


    public int id;
    public Bottom bottom;
    public Topping topping;
    public int quantity;
    public double totalPrice;

    public Orderline(int id, String bottom, String topping, int quantity, double totalPrice) {
        this.id = id;

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
 
    @Override
    public String toString() {
        return "Orderline [id=" + id + ", bottom=" + bottom.getName() + ", topping=" + topping.getName() + ", quantity=" + quantity
                + ", totalPrice=" + totalPrice + "]";
    }
    

}
