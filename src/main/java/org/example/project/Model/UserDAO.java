package org.example.project.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insertUser(User user) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into user(username, password) values(?,?)") ;

        ps.setString(1, user.getName());
        ps.setString(2, user.getPassword());

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            return true ;
        }
        return false ;
    }
}
