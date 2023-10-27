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



//TODO: Add these ctx.attributes every time cupcakeSelection.html is rendered
    /*

        List<Topping> allToppings;
        List<Bottom> allBottoms;

        try{
            allBottoms = OptionsMapper.getAllBottoms(connectionPool);
            allToppings = OptionsMapper.getAllToppings(connectionPool);
            ctx.attribute("allBottoms", allBottoms);
            ctx.attribute("allToppings", allToppings);
        }
*/

    public static void addToCart(Context ctx, ConnectionPool connectionPool) {

        int bottomId = Integer.parseInt(ctx.formParam("bottom"));
        int toppingId = Integer.parseInt(ctx.formParam("topping"));
        int quantity = Integer.parseInt(ctx.formParam("quantity"));

        List<Topping> allToppings;
        List<Bottom> allBottoms;

        Cart cart = ctx.sessionAttribute("cart");

        try {

            Orderline newOrderline = OrderlineMapper.getOrderline(bottomId, toppingId, quantity, connectionPool);
            cart.addToCart(newOrderline);
            ctx.sessionAttribute("cart", cart);

            allBottoms = OptionsMapper.getAllBottoms(connectionPool);
            allToppings = OptionsMapper.getAllToppings(connectionPool);
            ctx.attribute("allBottoms", allBottoms);
            ctx.attribute("allToppings", allToppings);

            ctx.render("/cupcakeSelection.html");

        } catch (DatabaseException e) {

            ctx.attribute("dbErrorMsg");
            ctx.render("/cupcakeSelection.html");
        }
    }

    public static void deleteOrderline(Context ctx, ConnectionPool connectionPool) {

        int indexOfOrderline = Integer.parseInt(ctx.formParam("delete-btn"));

        List<Topping> allToppings;
        List<Bottom> allBottoms;
        Cart cart = ctx.sessionAttribute("cart");
        List<Orderline> orderlineListToDeleteFrom = cart.getOrderlineList();

        orderlineListToDeleteFrom.remove(indexOfOrderline);

        try {

            allBottoms = OptionsMapper.getAllBottoms(connectionPool);
            allToppings = OptionsMapper.getAllToppings(connectionPool);

            ctx.attribute("allBottoms", allBottoms);
            ctx.attribute("allToppings", allToppings);
            ctx.sessionAttribute("cart", cart);
            ctx.render("/cupcakeSelection.html");

        } catch (DatabaseException e) {
            ctx.attribute("dbErrorMsg");
            ctx.render("/cupcakeSelection.html");
        }
    }
}
