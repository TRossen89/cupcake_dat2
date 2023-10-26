package app.controllers;

import app.entities.Bottom;
import app.entities.Orderline;
import app.entities.Topping;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OptionsMapper;
import app.persistence.OrderMapper;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class CartController {

    //TODO: Delete this method or finish it and transfer it to UserController
    public static void login(Context ctx, ConnectionPool connectionPool){

        List<Orderline> orderlineList = new ArrayList<>();
        ctx.sessionAttribute("orderlineList", orderlineList);

        List<Topping> allToppings;
        List<Bottom> allBottoms;

        //User currentUser = new User(1, "tobias", "1234", "admin", 200.0);

        try{
            allBottoms = OptionsMapper.getAllBottoms(connectionPool);
            allToppings = OptionsMapper.getAllToppings(connectionPool);
            ctx.attribute("allBottoms", allBottoms);
            ctx.attribute("allToppings", allToppings);
            //ctx.sessionAttribute("currentUser", currentUser);
            ctx.render("/cupcakeSelection.html");

        }catch (DatabaseException e){

            ctx.attribute("dbErroMsg", e);
            ctx.render("/cupcakeSelection.html");
        }




    }


    public static void addToCart(Context ctx, ConnectionPool connectionPool){

        int bottomId = Integer.parseInt(ctx.formParam("bottom"));
        int toppingId = Integer.parseInt(ctx.formParam("topping"));
        int quantity = Integer.parseInt(ctx.formParam("quantity"));

        List<Topping> allToppings;
        List<Bottom> allBottoms;

        List<Orderline> orderlineList = ctx.sessionAttribute("orderlineList");

        try{

            Orderline newOrderline = OrderMapper.getTotalPriceOfOrderline(bottomId, toppingId, quantity, connectionPool);

            orderlineList.add(newOrderline);

            allBottoms = OptionsMapper.getAllBottoms(connectionPool);
            allToppings = OptionsMapper.getAllToppings(connectionPool);

            ctx.attribute("allBottoms", allBottoms);
            ctx.attribute("allToppings", allToppings);

            double totalPriceOfCart = totalPriceOfCart(orderlineList);

            ctx.sessionAttribute("totalPriceOfCart", totalPriceOfCart);
            ctx.sessionAttribute("orderlineList", orderlineList);
            ctx.render("/cupcakeSelection.html");

        }catch (DatabaseException e){

            ctx.attribute("dbErrorMsg");
            ctx.render("/cupcakeSelection.html");
        }


    }

    public static double totalPriceOfCart(List<Orderline> orderlines){

        double totalPriceOfCart = 0;
        for(Orderline o: orderlines){

            totalPriceOfCart += o.getTotalPrice();
        }
        return totalPriceOfCart;

    }

}
