/**
 * User NoSQL database instead of trying to perform relations connections 
 */
package schedular;
/**
 * @author Syed Khurshid
 * Appointment management app that was built on JavaFX
 * Connecting to Database
 * Future would make data go beyond just schedule appointments and more also like chat and calls
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import schedular.connect.Database;
import java.io.IOException;
/**
 * JavaFX App
 */
public class App extends Application {
    /**
     * This is the Scene class for the software to be developed
     */
    private static Scene scene;
    
    /**
     * The main page scene
     */
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("Login.fxml"));
        scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
    /**
     * This is the main starting apps
     * @param args Strings args 
     * @throws ClassNotFoundException any error of a class not found
     */
    public static void main(String[] args) throws ClassNotFoundException {
        Database.openConnection(); // Opening the connection
        launch(); // Launching the app
        Database.closeConnection(); // Closing the connection
        
    }

}