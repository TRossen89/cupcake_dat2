package app.controllers;
import java.sql.SQLException;

import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.http.Context;

public class UserControler {
    
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
}
