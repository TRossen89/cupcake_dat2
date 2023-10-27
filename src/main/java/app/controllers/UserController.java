package app.controllers;


import app.entities.Cart;
import app.entities.Orderline;
import app.entities.Bottom;
import app.entities.Topping;
import app.entities.User;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OptionsMapper;
import app.persistence.UserMapper;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    public static void login(Context ctx, ConnectionPool connectionPool)
    {
        List<Orderline> orderlineList = new ArrayList<>();
        Cart cart = new Cart(orderlineList);
        ctx.sessionAttribute("cart", cart);
        
        String name = ctx.formParam("username");
        String password = ctx.formParam("password");

        try
        {
            User user = UserMapper.login(name, password, connectionPool);
            ctx.sessionAttribute("currentUser", user);

            List<Topping> allToppings = OptionsMapper.getAllToppings(connectionPool);
            List<Bottom> allBottoms = OptionsMapper.getAllBottoms(connectionPool);
            ctx.sessionAttribute("allBottoms", allBottoms);
            ctx.sessionAttribute("allToppings", allToppings);

            ctx.redirect("/userpage");
            
        }
        catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage());
            ctx.render("login.html");
        }
        catch (SQLException e)
        {
            ctx.attribute("message", e.getMessage());
            ctx.render("login.html");
        }
    }

    
    public static void createUser(Context ctx, ConnectionPool connectionPool){
        String name = ctx.formParam("username");
        String password = ctx.formParam("password");
        String role = ctx.formParam("isadmin");
        if(role == null){
            role = "user";
        } else {
            role = "admin";
        }
        try{
            UserMapper.createUser(name, password, role, connectionPool);
            //TODO: maybe add some message that tells the user of a sucsecss full creation
            ctx.render("login.html");
        } catch (SQLException e){
            //TODO: add some error handeling
            System.out.println(e.getMessage());
            ctx.render("createUser.html");
        }
    }

    public static void logout(Context ctx)
    {
        // Invalidate session
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }
}

