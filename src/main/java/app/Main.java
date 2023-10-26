package app;

import java.sql.SQLException;

import app.config.ThymeleafConfig;
import app.controllers.AdminControler;
import app.controllers.UserControler;
import app.persistence.AdminMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
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

        app.get("/", ctx ->  ctx.render("index.html"));
      
        app.get("/createUser", ctx -> ctx.render("createUser.html"));
        app.post("/createUser", ctx -> UserControler.createUser(ctx, connectionPool));
      
        app.get("/adminpage", ctx -> AdminControler.renderAdminPage(ctx, connectionPool));
        app.post("/adminOrderLine", ctx -> AdminControler.getOrderLine(ctx, connectionPool));
    }
}