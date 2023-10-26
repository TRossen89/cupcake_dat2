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

        List<Topping> allToppings;
        List<Bottom> allBottoms;

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

                // Clearing cart for cupcakeSelection.html
                cart.clearCart();
                ctx.sessionAttribute("cart", cart);

                /*
                // TODO: Delete these and remember to add them before every rendering of cupcakeSelection.html
                allBottoms = OptionsMapper.getAllBottoms(connectionPool);
                allToppings = OptionsMapper.getAllToppings(connectionPool);
                ctx.attribute("allBottoms", allBottoms);
                ctx.attribute("allToppings", allToppings);
                 */

                ctx.attribute("receipt", cartForReceipt);

                //TODO: It should render html page with receipt
                ctx.render("/template.html");
            }
            else{
                // Rendering cupcakeSelection.html if customer doesn't have enough money and showing info message
                allBottoms = OptionsMapper.getAllBottoms(connectionPool);
                allToppings = OptionsMapper.getAllToppings(connectionPool);
                ctx.attribute("allBottoms", allBottoms);
                ctx.attribute("allToppings", allToppings);
                ctx.attribute("noMoney","You don't have enough money to buy that many cupcakes. Control yourself.");
                ctx.render("/cupcakeSelection.html");
            }

        }catch (DatabaseException e){
            ctx.attribute("dbConnectionError", e);
            ctx.render("/cupcakeSelection.html");
        }
    }
}

