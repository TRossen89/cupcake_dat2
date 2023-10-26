package app.controllers;

import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.http.Context;

import java.sql.SQLException;

public class UserController {
    public static void login(Context ctx, ConnectionPool connectionPool)
    {
        String name = ctx.formParam("username");
        String password = ctx.formParam("password");
        try
        {
            User user = UserMapper.login(name, password, connectionPool);
            ctx.sessionAttribute("currentUser", user);
            ctx.render("/cupcakeSelection.html");
        }
        catch (SQLException e)
        {
            ctx.attribute("message", e.getMessage());
            ctx.render("login.html");
        }
    }
}