package app.controllers;

import app.entities.Orderline;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.http.Context;

import java.sql.*;
import java.util.List;

public class OrderController {
    public static void placeOrder(Context ctx, ConnectionPool connectionPool) {

        List<Orderline> orderlineList = ctx.sessionAttribute("orderlineList");
        double totalPriceOfOrder = ctx.sessionAttribute("totalPriceOfCart");
        User currentUser = ctx.sessionAttribute("currentUser");


        try{
            OrderMapper.placeOrderInDB(orderlineList, totalPriceOfOrder, currentUser, connectionPool);

            orderlineList.clear();
            ctx.sessionAttribute("orderlineList", orderlineList);
            ctx.sessionAttribute("totalPriceOfCart", 0);

            //TODO: It should render html page with receipt
            ctx.render("/cupcakeSelection.html");


        }catch (DatabaseException e){

            ctx.attribute("dbConnectionError", e);
            ctx.render("/cupcakeSelection.html");
        }





    }


}

