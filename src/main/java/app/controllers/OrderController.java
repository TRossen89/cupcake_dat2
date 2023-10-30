package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OptionsMapper;
import app.persistence.OrderMapper;
import io.javalin.http.Context;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderController {
    public static void placeOrder(Context ctx, ConnectionPool connectionPool) {

        Cart cart = ctx.sessionAttribute("cart");
        List<Orderline> orderlineList = cart.getOrderlineList();
        double totalPriceOfOrder = cart.getTotalPriceOfCart();

        User currentUser = ctx.sessionAttribute("currentUser");


        try{

            // Placing order in DB only if customer has enough money
            boolean orderConfirmed = OrderMapper.checkAndPlaceOrderInDB(orderlineList, totalPriceOfOrder, currentUser, connectionPool);

            if(orderConfirmed){

                // Instantiating a Cart with the orderlist for receipt.html
                List<Orderline> orderlinesForReceipt = new ArrayList<>();
                for (Orderline orderline : cart.getOrderlineList()){
                    orderlinesForReceipt.add(orderline);
                }
                Cart cartForReceipt = new Cart(orderlinesForReceipt);
                ctx.attribute("receipt", cartForReceipt);

                // Clearing cart for cupcakeSelection.html
                cart.clearCart();
                ctx.sessionAttribute("cart", cart);

                ctx.render("/receipt.html");
            }
            else{

                ctx.attribute("noMoney","You don't have enough money to buy that many cupcakes. Control yourself.");
                ctx.render("/cupcakeSelection.html");
            }

        }catch (DatabaseException e){
            ctx.attribute("dbConnectionError", e);
            ctx.render("/cupcakeSelection.html");
        }
    }
}

