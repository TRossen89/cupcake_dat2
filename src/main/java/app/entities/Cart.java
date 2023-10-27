package app.entities;

import java.util.List;

public class Cart {

    private List<Orderline> orderlineList;


    public Cart(List<Orderline> orderlineList) {
        this.orderlineList = orderlineList;

    }

    public double getTotalPriceOfCart(){

        double totalPriceOfCart = 0;
        for(Orderline o: orderlineList){

            totalPriceOfCart += o.getTotalPrice();
        }
        return totalPriceOfCart;

    }

    public void addToCart(Orderline orderline){
        orderlineList.add(orderline);
    }

    public List<Orderline> getOrderlineList() {
        return orderlineList;
    }

    public void clearCart(){

        orderlineList.clear();
    }
}
