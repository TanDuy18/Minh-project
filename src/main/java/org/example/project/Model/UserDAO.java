package org.example.project.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public List<User> getAllUser() {
        List<User> users = new ArrayList<User>();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM USER") ;

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("username"));
                user.setXeThue(rs.getString("xeThue"));
                user.setNgayTra(rs.getString("ngayTra"));
                user.setNgayThue(rs.getString("ngayThue"));
                user.setValid(rs.getString("valid"));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
    public boolean insertUser(User user) {
        try{
            PreparedStatement ps = conn.prepareStatement("INSERT INTO user(username , xeThue , ngayThue,ngayTra) VALUES (?,?,?,?)") ;
            ps.setString(1, user.getName());
            ps.setString(2, user.getXeThue());
            ps.setString(3, user.getNgayThue());
            ps.setString(4, user.getNgayTra());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true ;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false ;
    }

    public boolean updateUser(User user , int id) {
        try{
            PreparedStatement ps = conn.prepareStatement("UPDATE user SET username = ?,xeThue = ? ,ngayThue = ? , ngayTra = ? WHERE id = ?") ;
            ps.setString(1, user.getName());
            ps.setString(2, user.getXeThue());
            ps.setString(3, user.getNgayThue());
            ps.setString(4, user.getNgayTra());
            ps.setInt(5, id);

            int rowsUpdated = ps.executeUpdate();

            // Kiểm tra xem có hàng nào được cập nhật không
            return rowsUpdated > 0;
        }catch (Exception e) {{
        e.printStackTrace();
        }
        }
        return false ;
    }

    public void deleteUser(User user) {
        try{
            PreparedStatement ps = conn.prepareStatement("DELETE FROM user WHERE id = ?") ;
            ps.setInt(1, user.getId());

            int rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0) {
                System.out.println("Delete User Success");
            } else {
                System.out.println("Delete User Failed");
            }
        }catch (Exception e) {

        }
    }

    public List<User> searchUsers(String searchText) {
        List<User> result = new ArrayList<>();
        String query = "SELECT * FROM user WHERE xeThue LIKE ?";


        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, "%" + searchText + "%"); // Thêm wildcard cho LIKE

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("xeThue"),
                        rs.getString("ngayThue"),
                        rs.getString("ngayTra"),
                        rs.getString("valid")
                );
                result.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}
