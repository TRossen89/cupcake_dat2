package app.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.entities.Order;
import app.entities.Orderline;

public class AdminMapper {

    public static List<Order> getAllOrders(ConnectionPool connectionPool) throws SQLException{
        String sql = "SELECT O.id, O.date, O.status, U.username " +
                     "FROM orders O JOIN public.user U on O.user_id = U.id";
        List<Order> orders = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    Date date = resultSet.getDate("date");
                    String status = resultSet.getString("status");
                    String userName = resultSet.getString("username");
                    Order order = new Order(id, date, status, userName);
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    public static List<Orderline> getOrderLines(ConnectionPool connectionPool, int oderId) throws SQLException {
        String sql = "SELECT OL.id as id, OL.quantity, T.name as topping, B.name as bottom, OL.total_price " +
                     "FROM orderlines OL " + 
                         "JOIN topping T ON OL.topping_id = T.id " + 
                         "JOIN bottom B ON OL.bottom_id = B.id " +
                     "WHERE OL.order_id = ?";
        List<Orderline> orderlines = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, oderId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String bottom = resultSet.getString("bottom");
                    String topping = resultSet.getString("topping");
                    int quantity = resultSet.getInt("quantity");
                    double totalPrice = resultSet.getDouble("total_price");
                    Orderline orderline = new Orderline(id,bottom,topping,quantity,totalPrice);
                    orderlines.add(orderline);
                }
            }
        }
        return orderlines;
    }
}
