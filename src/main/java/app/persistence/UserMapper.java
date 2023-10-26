package app.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.entities.User;

public class UserMapper {
    
    public static User login(String name, String password, ConnectionPool connectionPool) throws SQLException 
    {
        String sql = "SELECT * FROM public.\"user\" WHERE username=? AND password=?";

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
}
