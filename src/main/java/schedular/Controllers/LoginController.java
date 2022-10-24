package schedular.Controllers;

import java.io.IOException;

/**
 * @author Syed Khurshid
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController implements Initializable {
    /**
     * This is the Login Button for Logging in user. Also includes the reset button to clear the textfields
     */
    @FXML
    private Button LoginButton, resetButton;
    /**
     * Choice box for the language preference
     */
    @FXML
    private ChoiceBox<String> languageChoice;
    /**
     * This is the text that shows the user the location
     */
    @FXML
    private Text locationText;
    /**
     * These are the labels of the password and User ID Labels
     */
    @FXML
    private Label passwordLabel, userIdLabel;
    /**
     * This is the password field
     */
    @FXML
    private PasswordField passwordText;
    /**
     * This is the User ID Text Field
     */
    @FXML
    private TextField userIDText;
    
    /**
     * This is an action Button press 
     * @param event Button press action that would check User ID and Password
     * @throws text errors which is user has not entered password correctly
     * @throws IOException with error due to location not found
     * Future preference is to make sure that the checks happens while the user is entering the data
     */
    @FXML
    void LogPress(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/MainPage.fxml"));
        Stage stage = (Stage) LoginButton.getScene().getWindow();
        stage.setTitle("Add Product");
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    /**
     * This is a password Check. It will verify the users password
     * @param event which is a input entry check
     * Future preference is to check the regEx characters and no SQL injections
     */
    @FXML
    void passwordCheck(ActionEvent event) {

    }
    
    /**
     * This is a action event to clear the User ID and Password fields
     * @param event from button press to clear the fields
     * Future preference is that this might not be necessary later to reset
     */
    @FXML
    void resetPress(ActionEvent event) {
        userIDText.clear();
        passwordText.clear();
    }
    
    /**
     * User ID check to see if it exists
     * @param event from key input to check if the user ID exists
     * Future preference is to add RegEx ccheck to see if their is no SQL injections
     */
    @FXML
    void userIDCheck(ActionEvent event) {

    }
    /**
     * An array of languageChoices for users to seelect
     */
    private String[] langauge = { "English US", "French" };
    /**
     * Selecting the choices of language
     * @param event which is a selection from a choice drop down
     */
     public void getLanguage(ActionEvent event) {
          
          String myChoice = languageChoice.getValue();
          locationText.setText(myChoice);
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
        locationText.setText(languageChoice.getValue());
        languageChoice.setOnAction(this::getLanguage);

    }
    
     

}
