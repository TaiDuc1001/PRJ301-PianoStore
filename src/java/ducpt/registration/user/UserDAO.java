package ducpt.registration.user;

import ducpt.database.DBUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class UserDAO {
    private List<UserDTO> users;
    
    public List<UserDTO> getUsers() {
        return users;
    }

    public boolean addUser(String username, String password, String firstname, String lastname, boolean role) throws SQLException {
        try (Connection con = DBUtils.makeConnection();
             PreparedStatement stm = con.prepareStatement(
                "INSERT INTO Users (username, password, first_name, last_name, isAdmin) VALUES (?, ?, ?, ?, ?)")) {
            
            stm.setString(1, username);
            stm.setString(2, password);
            stm.setString(3, firstname);
            stm.setString(4, lastname);
            stm.setBoolean(5, role);
            return stm.executeUpdate() > 0;
        }
    }

    public boolean updateUser(String username, String password, String firstname, String lastname, boolean role) throws SQLException {
        try (Connection con = DBUtils.makeConnection();
             PreparedStatement stm = con.prepareStatement(
                "UPDATE Users SET password=?, first_name=?, last_name=?, isAdmin=? WHERE username=?")) {
            
            stm.setString(1, password);
            stm.setString(2, firstname);
            stm.setString(3, lastname);
            stm.setBoolean(4, role);
            stm.setString(5, username);
            return stm.executeUpdate() > 0;
        }
    }

    public void searchByLastname(String searchValue) throws SQLException {
        try (Connection con = DBUtils.makeConnection();
             PreparedStatement stm = con.prepareStatement(
                "SELECT username, password, first_name, last_name, isAdmin FROM Users WHERE last_name LIKE ?")) {
            
            stm.setString(1, "%" + searchValue + "%");
            try (ResultSet rs = stm.executeQuery()) {
                users = new ArrayList<>();
                while (rs.next()) {
                    users.add(new UserDTO(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getBoolean("isAdmin")
                    ));
                }
            }
        }
    }

    public boolean checkLogin(String username, String password) {
        try (Connection con = DBUtils.makeConnection();
             PreparedStatement stm = con.prepareStatement(
                "SELECT username FROM Users WHERE username = ? AND password = ?")) {
            
            stm.setString(1, username);
            stm.setString(2, password);
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean isAdmin(String username) throws SQLException {
        try (Connection con = DBUtils.makeConnection();
             PreparedStatement stm = con.prepareStatement(
                "SELECT isAdmin FROM Users WHERE username = ?")) {
            
            stm.setString(1, username);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("isAdmin");
                }
            }
        }
        return false;
    }
} 