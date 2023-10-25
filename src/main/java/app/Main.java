package app;

import app.config.ThymeleafConfig;
import app.controllers.CartController;
import app.controllers.OrderController;
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

    public static void main(String[] args) {
        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            JavalinThymeleaf.init(ThymeleafConfig.templateEngine());
        }).start(7070);

        // Routing


        //TODO: Change next line to the original
        app.get("/", ctx -> renderFrontPage(ctx));

        app.post("/addToCart", ctx -> CartController.addToCart(ctx, connectionPool));
        app.post("/login", ctx -> CartController.login(ctx, connectionPool));
        app.post("/buy", ctx -> OrderController.placeOrder(ctx, connectionPool));
    }


    //TODO: Copy delete and insert into another branch
    public static void renderFrontPage(Context ctx) {

        User currentUser = new User(1, "guest", "1234sdf2338jdsvw34599458490sks", "admin", 200.0);
        ctx.sessionAttribute("currentUser", currentUser);
        ctx.render("/template.html");

    }
}