package org.example.project.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    private Connection conn;

    public AdminDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insertUser(Admin user) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into admin(username, password) values(?,?)") ;

        ps.setString(1, user.getName());
        ps.setString(2, user.getPassword());

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            return true ;
        }
        return false ;
    }

    public boolean selectUser(Admin user) throws SQLException {
        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
