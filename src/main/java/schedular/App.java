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

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("Login.fxml"));
        scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}