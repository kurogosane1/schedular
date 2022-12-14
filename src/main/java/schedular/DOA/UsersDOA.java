/**
 * Refactoring code to reduce the number of code lines
 * @author Syed Khurshid
 */
package schedular.DOA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schedular.Model.User;
import schedular.connect.Database;

/**
 * This is the User Data Object to interact with the database
 */
public class UsersDOA {
    /**
     * This is the SQL String to create the User
     * Will move to the functions
     */
    private static final String CREATE_USER = "INSERT INTO Users (User_Name, Password) VALUES (?,?)"; 
    /**
     * This is to Create Users
     * @param user value is then placed into the database
     * @return null
     */
    public User create(User user) {
        try (PreparedStatement ps = Database.getConnection().prepareStatement(CREATE_USER)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }
    
    /**
     * This is to get the User based on the ID
     * @param id this is the User ID given
     * @return User object from the database
     * @throws SQLException which in case of SQL error
     */
    public User getID(int id) throws SQLException {
        Connection con = Database.getConnection();
        User user = null;
        String sql = "SELECT User_ID, User_Name, Password FROM USERS WHERE User_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int userid = rs.getInt("User_ID");
            String username = rs.getString("User_Name");
            String Password = rs.getString("Password");
            user = new User(userid, username, Password);
        }
        return user;
    }
    /**
     * This is to get all Users
     * @return users which is an ObservableList of User
     * @throws SQLException if an error occurs on the database side
     */
    public ObservableList<User> getAllUsers() throws SQLException {
        Connection con = Database.getConnection();
        ObservableList<User> users = FXCollections.observableArrayList();
        String sql = "SELECT User_ID, User_Name, Password FROM USERS";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            User user = new User(userId, userName, password);
            users.add(user);
        }
        return users;
    }
    /**
     * This is to check the User if the user exists or not
     * @param username which is received from the form
     * @param password which is received from the form
     * @return boolean which is true if the user exists
     * @throws SQLException if an error occurs on the database side
     */
    public Boolean checkUser(String username, String password) throws SQLException {
        Connection con = Database.getConnection();
        User user = null;
        String sql = "SELECT User_ID,User_Name, Password FROM USERS WHERE USER_NAME=? AND PASSWORD=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String passwords = rs.getString("Password");
            user = new User(userId, userName, passwords);
            return true;
        }
        else {
            return false;
        }
    }
}
