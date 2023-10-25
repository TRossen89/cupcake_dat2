package app.controllers;

import app.entities.Bottom;
import app.entities.Orderline;
import app.entities.Topping;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OptionsMapper;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class OptionsController {


    public OptionsController() {
    }

    public static void showOptions(Context ctx, ConnectionPool connectionPool) {

        List<Orderline> orderlineList = new ArrayList<>();

        List<Topping> allToppings;
        List<Bottom> allBottoms;

        try{
            allBottoms = OptionsMapper.getAllBottoms(connectionPool);
            allToppings = OptionsMapper.getAllToppings(connectionPool);

            ctx.attribute("allBottoms", allBottoms);
            ctx.attribute("allToppings", allToppings);
            ctx.sessionAttribute("orderlineList", orderlineList);
            ctx.render("/cupcakeSelection.html");

        }
        catch (DatabaseException e){
            ctx.attribute("connectionErrorMsg", e.getMessage());
            ctx.render("index.html");

        }


    }
}
