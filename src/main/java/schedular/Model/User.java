/**
 * @author Syed Khurshid
 * This is the User class for creating the User Object
 */
package schedular.Model;

/**
 * This is the User Object for Creating User Objects
 */
public class User {
    private int userId;
    private String userName;
    private String Password;
    
    /**
     * Default Constructor
     */
    public User() {
        userId = 0;
        userName = null;
        Password = null;
    }
    /**
     * Constructor with userId and userName and password
     * @param userId for the User ID
     * @param userName for the userName
     * @param Password for the password
     */
    public User(int userId, String userName, String Password) {
        this.userId = userId;
        this.userName = userName;
        this.Password = Password;
    }
    /**
     * This is to get the User ID
     * @return userID is returned
     */
    public int getUserId() {
        return userId;
    }
    /**
     * This is set the user id
     * @param userId is then added to the database
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /**
     * This is simply to return the username
     * @return username name of the User
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This is to set the Username
     * @param userName which is the user name in a string format
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * This is return the password
     * @return password returned from the database
     */
    public String getPassword() {
        return Password;
    }
    /**
     * This is to set the password
     * @param password is used to input the password in the database
     */
    public void setPassword(String password) {
        Password = password;
    }


    
    
}
