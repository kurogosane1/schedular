package schedular.DOA;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import schedular.Model.User;
import schedular.connect.Database;

public class UsersDOA {
    private static final String GET_USERS = "SELECT * FROM Users";
    private static final String CREATE_USER = "INSERT INTO Users (User_Name, Password) VALUES (?,?)"; 
    /**
     * This is to Create Users
     * @param user value is then placed into the database
     * @return null
     * @throws SQLException any error from the connection
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
     * This is to get all Users
     * @return users which is an ObservableList of User
     * @throws SQLException of not being able to process
     */
    public ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList();
        // PreparedStatement ps = Database.getConnection().prepareStatement(GET_USERS);
        try (PreparedStatement ps = Database.getConnection().prepareStatement(GET_USERS)) {
            ResultSet rs = ps.executeQuery();
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            User user = new User(userID, userName, password);
            users.add(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return users;
    }
}
