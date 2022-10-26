package schedular.connect;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * This is to establish a connection to a database
 */
public abstract class Database {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost:3306/";
    private static final String databaseName = "Javas";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
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

   
}
// public void Test() throws ClassNotFoundException {
    //     String databaseURL = "jdbc:mysql://localhost:3306/Javas";
    //     String user = "root";
    //     String password = "password";
    //     Connection con = null;
    //     Statement stmt = null;
    //     try {
    //         con = DriverManager.getConnection(databaseURL, user, password);
    //         System.out.println("Connected database successfully");
    //         stmt = con.createStatement();
    //         String sql = "SELECT * USERS";
    //         stmt.executeUpdate(sql);
    //         System.out.println("Successfully pulled USERS");
            
            
    //     } catch (SQLException e) {
    //         System.out.println("A Connection error has occured");
    //         e.printStackTrace();
    //     }

    //     try{
    //         if(stmt!=null){
    //             stmt.close();
    //         }
    //         if(con!=null){
    //             con.close();
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }