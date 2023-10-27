package app.persistence;

import app.entities.Bottom;
import app.entities.Topping;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OptionsMapper {

    public static List<Topping> getAllToppings(ConnectionPool connectionPool) throws DatabaseException {

        List<Topping> allToppings = new ArrayList<>();

        String sql = "SELECT * FROM topping";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String toppingName = rs.getString("name");
                    double price = rs.getDouble("price");
                    Topping newTopping = new Topping(id, toppingName, price);
                    allToppings.add(newTopping);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Something wwent wrong with the connection to the database");
        }

        return allToppings;

    }

    public static List<Bottom> getAllBottoms(ConnectionPool connectionPool) throws DatabaseException {

        List<Bottom> allBottoms = new ArrayList<>();

        String sql = "SELECT * FROM bottom";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String toppingName = rs.getString("name");
                    double price = rs.getDouble("price");

                    Bottom newBottom = new Bottom(id, toppingName, price);
                    allBottoms.add(newBottom);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Something went wrong with the connection to the database");
        }

        return allBottoms;

    }


}
