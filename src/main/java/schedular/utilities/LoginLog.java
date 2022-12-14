/**
 * This is The Login Log for 
 * A seperate user logged in database would be better to have with reddis implementation
 */
package schedular.utilities;

/**
 * @author Syed Khurshid
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * File in which to store the login logs and user that has logged in
 */
public class LoginLog {
    /**
     * This is to set the User logged in so that when information is requested, can be recieved
     */
    private static String userLoggedIn;
    /**
     * This is to get the User that has logged in so that when information is requested, can be recieved
     * @return userLoggedIn which is the name of the user
     */
    public static String getUserLoggedIn() {
        return userLoggedIn;
    }
    /**
     * This is to get the 
     * @param user in which is the user logged in name
     */
    public static void setUserLoggedIn(String user) {
        userLoggedIn = user;
    }
    /**
     * This is the file name
     */
    public final static String LoginFile = "login_activity.txt";
    /**
     * This is the Local Date Formatter
     */
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_LOCAL_DATE;
    /**
     * Date formatting pattern
     */
    public static final DateTimeFormatter timeFormats = DateTimeFormatter.ofPattern("HH:mm:ss a");

    /**
     * Appends a record for a login to be recorded to a log file
     * @param username Username used in the attempted Login
     * @param success Whether the login succeeded
     * @throws IOException on failure to append to log file
     */
    public static void append(String username, boolean success) throws IOException {
        var currentDate = LocalDateTime.now();
        var logginActivity = "Login on " + currentDate.format(dateFormat) + " at " + currentDate.format(timeFormats)
                + " by user with username " + username + " - " + (success ? "succeeded" : "failed");
        var writing = Files.newBufferedWriter(Paths.get(LoginFile), StandardCharsets.UTF_8,
                StandardOpenOption.WRITE, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        var printer = new PrintWriter(writing, true);
        printer.println(logginActivity);
    }
}
