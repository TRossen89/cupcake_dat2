package app;

import java.sql.SQLException;

import app.config.ThymeleafConfig;

import app.controllers.AdminControler;
import app.controllers.UserControler;
import app.persistence.AdminMapper;
import app.entities.User;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "cupcake";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
    public static void main(String[] args)
    {

        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            JavalinThymeleaf.init(ThymeleafConfig.templateEngine());
        }).start(7070);

        // Routing

        app.get("/", ctx ->  renderFrontPage(ctx));
      
        app.get("/createUser", ctx -> ctx.render("createUser.html"));
        app.post("/createUser", ctx -> UserController.createUser(ctx, connectionPool));
      
        app.get("/adminpage", ctx -> AdminControler.renderAdminPage(ctx, connectionPool));
        app.post("/adminOrderLine", ctx -> AdminControler.getOrderLine(ctx, connectionPool));
    }


    public static void renderFrontPage(Context ctx) {

        User currentUser = new User(1, "guest", "1234sdf2338jdsvw34599458490sks", "customer", 200.0);
        ctx.sessionAttribute("currentUser", currentUser);
        ctx.render("/frontpage.html");
    }

}

