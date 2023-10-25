package app.controllers;

import app.entities.Bottom;
import app.entities.Orderline;
import app.entities.Topping;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OptionsMapper;
import app.persistence.OrderMapper;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class CartController {

    public static void login(Context ctx, ConnectionPool connectionPool){

        List<Orderline> orderlineList = new ArrayList<>();
        ctx.sessionAttribute("orderlineList", orderlineList);

        List<Topping> allToppings;
        List<Bottom> allBottoms;

        try{
            allBottoms = OptionsMapper.getAllBottoms(connectionPool);
            allToppings = OptionsMapper.getAllToppings(connectionPool);
            ctx.attribute("allBottoms", allBottoms);
            ctx.attribute("allToppings", allToppings);
            ctx.render("/cupcakeSelection.html");

        }catch (DatabaseException e){

            ctx.attribute("dbErroMsg", e);
            ctx.render("/cupcakeSelection.html");
        }




    }


    public static void addToCart(Context ctx, ConnectionPool connectionPool){

        String bottomName = ctx.formParam("bottom");
        String toppingName = ctx.formParam("topping");
        int quantity = Integer.parseInt(ctx.formParam("quantity"));

        double totalPrice = 0;

        List<Orderline> orderlineList = ctx.sessionAttribute("orderlineList");

        try{
            totalPrice = OrderMapper.getTotalPriceOfOrderline(quantity, bottomName, toppingName, connectionPool);

            Orderline newOrderline = new Orderline(bottomName, toppingName, quantity, totalPrice);
            orderlineList.add(newOrderline);

            ctx.sessionAttribute("orderlineList", orderlineList);
            ctx.render("/cupcakeSelection.html");

        }catch (DatabaseException e){

            ctx.attribute("dbErrorMsg");
            ctx.render("/cupcakeSelection.html");
        }


    }

}
