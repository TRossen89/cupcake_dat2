package app.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.entities.Order;
import app.entities.User;

public class UserMapper {
    
    public static User login(String name, String password, ConnectionPool connectionPool) throws SQLException 
    {
        String sql = "SELECT * FROM public.user WHERE username=? AND password=?";

        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){

                    int id = resultSet.getInt("id");
                    String role = resultSet.getString("role");
                    double balance = resultSet.getDouble("balance");
                    
                    return new User(id, name, password, role, balance);


                } else {
                    throw new SQLException("Fejl i login. Prøv igen.");
                }
            }
        }
    }

    public static void createUser(String name, String password, String role, ConnectionPool connectionPool) throws SQLException
    {
        String sql = "INSERT INTO \"user\" (username, password, role, balance) VALUES (?, ?, ?, 200)";
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, role);
                int rowsAffected = preparedStatement.executeUpdate();
                if(rowsAffected != 1){
                    throw new SQLException("Fejl ved oprettelse af ny bruger");
                }
            }
        }
        catch (SQLException e)
        {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value"))
            {
                msg = "Brugernavnet findes allerede. Vælg et andet";
            }

            throw new SQLException(msg);
        }
    }

    public static List<Order> getUserOrders(int userId, ConnectionPool connectionPool) throws SQLException {
        String sql = "select * from orders where user_id = ?";
        List<Order> orders = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, userId);
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
        } catch (SQLException e) {
            throw new SQLException("Der er sket en fejl, kunne ikke finde odre");
        }
        return orders;
    }
}
