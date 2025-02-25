package com.university.dao;

import com.university.dao.DatabaseConnector;
import com.university.models.User;
import com.university.auth.BCryptUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Authenticate User by Email
    public User authenticateByEmail(String email, String password) {
        String sql = "SELECT u.user_id, u.email, u.username, u.password_hash, r.role_name, u.failed_attempts, u.locked_until " +
                     "FROM users u JOIN roles r ON u.role_id = r.role_id WHERE u.email = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");

                if (BCryptUtil.checkPassword(password, storedHash)) {
                    resetFailedAttempts(email);
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("role_name"),
                        rs.getInt("failed_attempts"),
                        rs.getTimestamp("locked_until")
                    );
                } else {
                    incrementFailedAttempts(email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Add User
    public boolean addUser(String email, String username, String role, String password) {
        int roleId = getRoleId(role);
        if (roleId == -1) {
            System.out.println("Invalid role: " + role);
            return false;
        }

        String hashedPassword = BCryptUtil.hashPassword(password);
        String sql = "INSERT INTO users (email, username, role_id, password_hash) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, username);
            stmt.setInt(3, roleId);
            stmt.setString(4, hashedPassword);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update User
    public boolean updateUser(int userId, String email, String username, String role) {
        int roleId = getRoleId(role);
        if (roleId == -1) {
            System.out.println("Invalid role: " + role);
            return false;
        }

        String sql = "UPDATE users SET email = ?, username = ?, role_id = ? WHERE user_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, username);
            stmt.setInt(3, roleId);
            stmt.setInt(4, userId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete User by ID
    public boolean deleteUserById(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get Role ID by Role Name
    private int getRoleId(String roleName) {
        String sql = "SELECT role_id FROM roles WHERE role_name = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, roleName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("role_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Get All Users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.user_id, u.email, u.username, r.role_name, u.failed_attempts, u.locked_until " +
                     "FROM users u JOIN roles r ON u.role_id = r.role_id";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                    rs.getInt("user_id"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("role_name"),
                    rs.getInt("failed_attempts"),
                    rs.getTimestamp("locked_until")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Update Password
    public boolean updatePassword(String email, String newPassword) {
        String hashedPassword = BCryptUtil.hashPassword(newPassword);
        String sql = "UPDATE users SET password_hash = ? WHERE email = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hashedPassword);
            stmt.setString(2, email);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete User by Email
    public boolean deleteUser(String email) {
        String sql = "DELETE FROM users WHERE email = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Increment Failed Login Attempts
    private void incrementFailedAttempts(String email) {
        String sql = "UPDATE users SET failed_attempts = failed_attempts + 1 WHERE email = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Reset Failed Login Attempts
    private void resetFailedAttempts(String email) {
        String sql = "UPDATE users SET failed_attempts = 0, last_login = NOW() WHERE email = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getInstructorId(String email) {
	    int instructorId = -1;
	    String query = "SELECT instructor_id FROM instructors WHERE user_id = (SELECT user_id FROM users WHERE email = ?)";

	    try (Connection conn = DatabaseConnector.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setString(1, email);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            instructorId = rs.getInt("instructor_id");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return instructorId;
	}

}
