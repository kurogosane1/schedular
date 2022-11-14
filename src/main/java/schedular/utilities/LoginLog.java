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
 * File in which to store the login logs
 */
public class LoginLog {
    /**
     * This is the file name
     */
    public final static String LOGIN_LOG = "login_activity.txt";
    /**
     * This is the Local Date Formatter
     */
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    /**
     * Date formatting pattern
     */
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Appends a record for a login to be recorded to a log file
     * @param username Username used in the attempted Login
     * @param success Whether the login succeeded
     * @throws IOException on failure to append to log file
     */
    public static void append(String username, boolean success) throws IOException {
        var now = LocalDateTime.now();
        var logLine = "Login on " + now.format(DATE_FORMAT) + " at " + now.format(TIME_FORMAT)
                + " by user with username " + username + " - " + (success ? "succeeded" : "failed");
        var bufferWritter = Files.newBufferedWriter(Paths.get(LOGIN_LOG), StandardCharsets.UTF_8,
                StandardOpenOption.WRITE, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        var printer = new PrintWriter(bufferWritter, true);
        printer.println(logLine);
    }
}
