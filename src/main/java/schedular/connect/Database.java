/**
 * This is to connect to SQL Database
 * @author Syed Khurshid
 */
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
    // The information below is what needs to be changed inor
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost:3306/";
    private static final String databaseName = "Javas";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?serverTimeZone = UTC"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver Reference
    private static final String userName = "root"; // Later use sqlUser
    private static String password = "password";
    public static Connection connection;
    
    /**
     * Default constructor
     */
    public Database() {
    }

    /**
     * Tgus us ti Establish a connection to the database
     */
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
    
    /**
     * This is to Close the connections to the database when the application is closed
     */
        public static void closeConnection() {
            try {
                connection.close();
                System.out.println("Connection Closed!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        /**
         * This is to get the current state of the database
         * @return connections settings and layout
         */
        public static Connection getConnection() {
            return connection;
        }
        /**
         * This is the closing statement to close the connection
         * @param statement This is to get the current state of the connection
         * @throws SQLException in case of an error
         */
        public static void closeStatement(Statement statement) throws SQLException {
            statement.close();
        }
        /**
         * This is to close the current Statement which is the SQL statement
         * @param preparedStatement SQL statement object
         * @throws SQLException if an error occurs
         */
        public static void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
            preparedStatement.close();
        }
        /**
         * This is to close the ResultSet
         * @param resultSet is brought in to be closed
         * @throws SQLException if an error occurs
         */
	    public static void closeResultSet(ResultSet resultSet) throws SQLException {
		resultSet.close();
	    }
   
}