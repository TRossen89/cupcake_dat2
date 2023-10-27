package app.controllers;

import java.sql.SQLException;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import app.entities.Order;
import app.entities.Orderline;
import app.persistence.AdminMapper;
import app.persistence.ConnectionPool;
import io.javalin.http.Context;

public class AdminControler {

    public static void getOrderLine(Context ctx, ConnectionPool connectionPool) {
        List<Orderline> orderlines = null;
        int orderId = Integer.parseInt(ctx.formParam("orderid"));
        try {
            orderlines = AdminMapper.getOrderLines(connectionPool, orderId);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(orderlines == null){
            //TODO: handle if order are null
        }
        System.out.println(orderlines);
        ctx.sessionAttribute("orderlines", orderlines);
        ctx.render("adminOrderlinePage.html");
    }

    public static void renderAdminPage(Context ctx, ConnectionPool connectionPool) {
        List<Order> orders = null;
        try {
            orders = AdminMapper.getAllOrders(connectionPool);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(orders == null){
            //TODO: handle if order are null
        }
        ctx.sessionAttribute("orders", orders);
        ctx.render("adminpage.html");
    }
    
}
