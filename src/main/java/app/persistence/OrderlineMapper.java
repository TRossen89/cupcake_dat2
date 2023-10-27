package app.persistence;

import app.entities.Bottom;
import app.entities.Orderline;
import app.entities.Topping;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderlineMapper {


    public static Orderline getOrderline(int bottomId, int toppingId, int quantity, ConnectionPool connectionPool) throws DatabaseException {

        double bottomPrice = 0;
        double toppingPrice = 0;
        double totalPrice;

        String bottomName = "";
        String toppingName = "";

        Bottom newBottom = null;
        Topping newTopping = null;

        String sql = "SELECT name, price FROM bottom WHERE id=?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, bottomId);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    bottomName = rs.getString("name");
                    bottomPrice = rs.getDouble("price");

                    newBottom = new Bottom(bottomId, bottomName, bottomPrice);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Something went wrong with the connection to the database");
        }


        String sql2 = "SELECT name, price FROM topping WHERE id=?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql2)) {

                ps.setInt(1, toppingId);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    toppingName = rs.getString("name");
                    toppingPrice = rs.getDouble("price");

                    newTopping = new Topping(toppingId, toppingName, toppingPrice);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Something went wrong with the connection to the database");
        }


        totalPrice = quantity * (bottomPrice + toppingPrice);

        Orderline newOrderline = new Orderline(newBottom, newTopping, quantity, totalPrice);

        return newOrderline;
    }
}
