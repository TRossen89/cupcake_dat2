package app.controllers;

import app.entities.Orderline;
import io.javalin.http.Context;

import java.util.ArrayList;

public class CartController {

    private static ArrayList<Orderline> orderlinesList;

    public CartController() {
        this.orderlinesList = new ArrayList<>();
    }

    public static void addToCart (Context ctx){



        ctx.sessionAttribute("currentCart", orderlinesList);
        ctx.render("/cupcakeSelection");






    }


}
