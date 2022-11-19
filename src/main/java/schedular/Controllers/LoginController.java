/**
 * Further username and password enhancement is necessary for making a much more secure system
 * Validating key entry stroke is required
 * @author Syed Khurshid
 */
package schedular.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import schedular.DOA.UsersDOA;
import schedular.utilities.LoginLog;

/**
 * This is the Login Page. Here you will reach the login where you will enter your password
 * as well as your username
 */
public class LoginController implements Initializable {
    /**
     * This is the close button
     */
    @FXML private Button closeButton;
    /**
     * This is the Login Button for Logging in user. Also includes the reset button to clear the textfields
     */
    @FXML private Button LoginButton, resetButton;
    /**
     * Choice box for the language preference
     */
    @FXML private ChoiceBox<String> languageChoice;
    /**
     * This is the text that shows the user the location
     */
    @FXML private Text locationText;
    /**
     * These are the labels of the password and User ID Labels
     */
    @FXML private Label passwordLabel, userIdLabel, Login_Label,languageLabel;
    /**
     * This is the password field
     */
    @FXML private PasswordField passwordText;
    /**
     * This is the User ID Text Field
     */
    @FXML private TextField userIDText;
    /**
     * This is for the French Call to language
     */
    Locale french = new Locale("fr", "FR");
    /**
     * This is the english language
     */
    Locale english = new Locale("en", "EN");
    /**
     * This is the File Path
     */
    String FILE_PATH = "langSelection/loginPage";

    Properties p = new Properties();
    private ResourceBundle rb = ResourceBundle.getBundle(FILE_PATH, Locale.getDefault());
     /**
     * An array of languageChoices for users to seelect
     */
    private String[] langauge = { rb.getString("languageChoice1"), rb.getString("languageChoice2")};
    /**
     * This is an action Button press 
     * @param event Button press action that would check User ID and Password
     * @throws text errors which is user has not entered password correctly
     * @throws IOException with error due to location not found
     * Future preference is to make sure that the checks happens while the user is entering the data
     * @throws SQLException
     */
    @FXML
    void LogPress(ActionEvent event) throws IOException, SQLException {
        if (userIDText.getText() == null || passwordText.getText() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(rb.getString("errorTitle"));
            alert.setContentText(rb.getString("errorMessage"));
            alert.showAndWait();
            LoginLog.append(null, false);
        }
        else {
            UsersDOA userDOA = new UsersDOA();
            Boolean success = userDOA.checkUser(userIDText.getText(), passwordText.getText());
            if (!success) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(rb.getString("errorTitle2"));
                alert.setContentText(rb.getString("errorMessage2"));
                alert.showAndWait();
                LoginLog.append(userIDText.getText(), false);
            }
            else {
                LoginLog.append(userIDText.getText(), true);
                Parent root = FXMLLoader.load(getClass().getResource("/schedular/MainPage.fxml"));
                Stage stage = (Stage) LoginButton.getScene().getWindow();
                // stage.setTitle(rb.getString("title"));
                stage.setTitle("Main Appointments");
                stage.setScene(new Scene(root));
                stage.show();
            }
        }
    }  
    /**
     * Closing the Application itself now completely
     * @param event which is a Button Press
     */
    @FXML
    public void closeApp(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    /**
     * This is a password Check. It will verify the users password
     * @param event which is a input entry check
     * Future preference is to check the regEx characters and no SQL injections
     */
    @FXML
    public void passwordCheck(ActionEvent event) {}
    /**
     * This is a action event to clear the User ID and Password fields
     * @param event from button press to clear the fields
     * Future preference is that this might not be necessary later to reset
     */
    @FXML
    public void resetPress(ActionEvent event) {
        userIDText.clear();
        passwordText.clear();
    }
    /**
     * User ID check to see if it exists
     * @param event from key input to check if the user ID exists
     * Future preference is to add RegEx ccheck to see if their is no SQL injections
     */
    @FXML
    public void userIDCheck(ActionEvent event) {}
    /**
     * Selecting the choices of language
     * @param event which is a selection from a choice drop down
     */
    public void getLanguage(ActionEvent event) {
        String myChoice = languageChoice.getValue();
        System.out.println(myChoice);
        if (myChoice.equals("French")) {
            Locale.setDefault(new Locale("fr", "FR"));
            localizeLabels();
            locationText.setText(myChoice + ": "+ZoneId.systemDefault());
        }
        else {
            Locale.setDefault(english);
            localizeLabels();
            locationText.setText(myChoice + ": "+ZoneId.systemDefault());
        }
    }
    /**
     * This is to set the values based on the properties
     */
    private void localizeLabels() {
        Locale lang = Locale.getDefault();
        rb = ResourceBundle.getBundle(FILE_PATH, lang);
        userIdLabel.setText(rb.getString("useridlabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        LoginButton.setText(rb.getString("LoginButton"));
        resetButton.setText(rb.getString("clearButton"));
        closeButton.setText(rb.getString("exitButton"));
        Login_Label.setText(rb.getString("title"));
        languageLabel.setText(rb.getString("languageLabel"));
    }
    /**
     * This is to initialize and login 
     * @param arg0 which is the URL or address
     * @param arg1 which is the screen resourceBundle 
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("This has been initialized");
        languageChoice.getItems().addAll(langauge);
        languageChoice.getSelectionModel().select(0);
        locationText.setText(languageChoice.getValue()  + " : "+ZoneId.systemDefault());
        languageChoice.setOnAction(this::getLanguage);
        localizeLabels();
    } 
}
