package schedular.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * This is to establish a connection to a database
 */
public abstract class Database {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost:3306/";
    private static final String databaseName = "Javas";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?serverTimeZone = UTC"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver Reference
    private static final String userName = "root"; // Later use sqlUser
    private static String password = "password";
    public static Connection connection;

    public static void openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection Successfully established");
                
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error: "+e.getMessage());
            }
        }
    
        public static void closeConnection() {
            try {
                connection.close();
                System.out.println("Connection Closed!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
     
        public static Connection getConnection() {
            return connection;
        }

        public static void closeStatement(Statement statement) throws SQLException {
            statement.close();
        }

        public static void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
            preparedStatement.close();
        }
        
	    public static void closeResultSet(ResultSet resultSet) throws SQLException {
		resultSet.close();
	    }
   
}