package com.foxminded.university.dao;

import java.sql.*;
import com.foxminded.university.domain.Lector;

public class LectorDao {
    
    private static final String CREATE_QUERY = "INSERT INTO lectors (first_name, last_name) "
            + "VALUES (?, ?);";
    
    public Lector create(Lector lector) {

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, lector.getFirstName());
            statement.setString(2, lector.getLastName());

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                lector.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lector; 
    }
}
