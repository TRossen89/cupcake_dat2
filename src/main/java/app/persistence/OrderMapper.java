package app.persistence;

import app.entities.Bottom;
import app.entities.Orderline;
import app.entities.Topping;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class OrderMapper {

    public static Orderline getOrderline(int bottomId, int toppingId, int quantity, ConnectionPool connectionPool) throws DatabaseException {

        double bottomPrice = 0;
        double toppingPrice = 0;
        double totalPrice;


        String bottomName = "";
        String toppingName = "";

        Bottom newBottom = null;
        Topping newTopping = null;

        String sql = "SELECT * FROM \"bottom\" WHERE id=?";

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


        String sql2 = "SELECT * FROM \"topping\" WHERE id=?";

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


    public static boolean checkAndPlaceOrderInDB(List<Orderline> orderlineList, double totalPriceOfOrder, User currentUser, ConnectionPool connectionPool) throws DatabaseException {

        // Calculating new balance
        double newBalance = calculateNewBalance(currentUser, totalPriceOfOrder, connectionPool);

        // Checking if user has enough money
        if (newBalance >= 0) {

            // Setting new balance
            settingBalance(newBalance, currentUser, connectionPool);

            //Inserting order in DB if user has enough money
            insertingOrderInDB(currentUser, totalPriceOfOrder, connectionPool, orderlineList);
        } else {
            // Returning false if user doesn't have enough money
            return false;
        }

        // Returning true if user has enough money
        return true;
    }


    private static void insertingOrderInDB(User currentUser, double totalPriceOfOrder, ConnectionPool connectionPool, List<Orderline> orderlineList) throws DatabaseException {

        int generatedOrderId = 0;

        long millis = System.currentTimeMillis();
        java.sql.Date dateOfOrder = new java.sql.Date(millis);

        String sql1 = "INSERT INTO \"orders\" (date, status, user_id, total_price) VALUES (?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)) {

                ps.setDate(1, dateOfOrder);
                ps.setString(2, "pending pickup");
                ps.setInt(3, currentUser.getId());
                ps.setDouble(4, totalPriceOfOrder);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    ResultSet rs = ps.getGeneratedKeys();
                    rs.next();
                    generatedOrderId = rs.getInt(1);

                } else {
                    throw new DatabaseException("Fejl");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error when inserting new order in db!");
        }


        for (Orderline orderline : orderlineList) {

            String sql2 = "INSERT INTO \"orderlines\" (order_id, quantity, topping_id, bottom_id, total_price) VALUES (?, ?, ?, ?, ?)";

            try (Connection connection = connectionPool.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement(sql2)) {

                    ps.setInt(1, generatedOrderId);
                    ps.setInt(2, orderline.getQuantity());
                    ps.setInt(3, orderline.getTopping().getId());
                    ps.setInt(4, orderline.getBottom().getId());
                    ps.setDouble(5, orderline.getTotalPrice());

                    ps.executeUpdate();
                }

            } catch (SQLException e) {
                throw new DatabaseException("Error when inserting orderline in DB!");
            }
        }
    }


    private static void settingBalance(double newBalance, User currentUser, ConnectionPool connectionPool) throws DatabaseException {

        String sql4 = "UPDATE public.user SET balance = ? WHERE id = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql4, Statement.RETURN_GENERATED_KEYS)) {

                ps.setDouble(1, newBalance);
                ps.setInt(2, currentUser.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error when updating balance");
        }
    }


    private static double calculateNewBalance(User currentUser, double totalPriceOfOrder, ConnectionPool connectionPool) throws DatabaseException {

        double oldBalance = 0;

        String sql3 = "SELECT balance FROM public.user WHERE id = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, currentUser.getId());
                ps.executeQuery();

                ResultSet rs = ps.getResultSet();
                if (rs.next()) {
                    oldBalance = rs.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error when calculating new balance");
        }

        double newBalance = oldBalance - totalPriceOfOrder;

        return newBalance;
    }
}