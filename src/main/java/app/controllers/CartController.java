package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OptionsMapper;
import app.persistence.OrderMapper;
import app.persistence.OrderlineMapper;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class CartController {

    public static void addToCart(Context ctx, ConnectionPool connectionPool) {

        int bottomId = Integer.parseInt(ctx.formParam("bottom"));
        int toppingId = Integer.parseInt(ctx.formParam("topping"));
        int quantity = Integer.parseInt(ctx.formParam("quantity"));


        Cart cart = ctx.sessionAttribute("cart");

        List<Bottom> allBottoms = ctx.sessionAttribute("allBottoms");
        List<Topping> allToppings = ctx.sessionAttribute("allToppings");

        Bottom bottomChosen = null;
        Topping toppingChosen = null;

        for (Bottom bottom : allBottoms) {
            if (bottom.getId() == bottomId) {
                bottomChosen = bottom;
            }
        }

        for (Topping topping : allToppings) {
            if (topping.getId() == toppingId) {
                toppingChosen = topping;
            }
        }

        double totalPrice = quantity * (bottomChosen.getPrice() + toppingChosen.getPrice());

        Orderline newOrderline = new Orderline(bottomChosen, toppingChosen, quantity, totalPrice);


        cart.addToCart(newOrderline);
        ctx.sessionAttribute("cart", cart);
        ctx.render("/cupcakeSelection.html");


    }


  /*
        try {
/*
            Orderline newOrderline = OrderlineMapper.getOrderline(bottomId, toppingId, quantity, connectionPool);


            cart.addToCart(newOrderline);
            ctx.sessionAttribute("cart", cart);
            ctx.render("/cupcakeSelection.html");

        } catch (DatabaseException e) {

            ctx.attribute("dbErrorMsg");
            ctx.render("/cupcakeSelection.html");
        }
    }

   */

    public static void deleteOrderline(Context ctx, ConnectionPool connectionPool) {

        int indexOfOrderline = Integer.parseInt(ctx.formParam("delete-btn"));

        Cart cart = ctx.sessionAttribute("cart");
        List<Orderline> orderlineListToDeleteFrom = cart.getOrderlineList();

        orderlineListToDeleteFrom.remove(indexOfOrderline);

        ctx.sessionAttribute("cart", cart);
        ctx.render("/cupcakeSelection.html");
    }
}
