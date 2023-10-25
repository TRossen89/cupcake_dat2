package app.persistence;

import app.entities.Bottom;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class OrderMapper {

    public static double getTotalPriceOfOrderline(int quantity, String bottomName, String toppingName, ConnectionPool connectionPool) throws DatabaseException {

        double bottomPrice = 0;
        double toppingPrice = 0;
        double totalPrice = 0;


        String sql = "SELECT price FROM \"bottom\" WHERE name=?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setString(1, bottomName);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    bottomPrice = rs.getDouble("price");

                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Something went wrong with the connection to the database");
        }

        totalPrice = quantity * (bottomPrice + toppingPrice);


        return totalPrice;
    }


}
