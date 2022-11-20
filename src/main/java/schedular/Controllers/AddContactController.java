/**
 * Using a no SQL database would be a better outcome
 * Better relationshops with forms
 * Less Code with More centralized Class methods
 */
package schedular.Controllers;
/**
 * @author Syed Khurshid
 */
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import schedular.DOA.ContactsDOA;
import schedular.Model.Contacts;

/**
 * This is to add Contacts to the database
 */
public class AddContactController implements Initializable {
    /**
     * This is the Cancel Button
     */
    @FXML private Button cancelButton;
    /**
     * This is the Email Text Field
     */
    @FXML private TextField emailTF;
    /**
     * This is the name of the Text Field
     */
    @FXML private TextField nameTF;
    /**
     * This is the Save Button
     */
    @FXML private Button saveButton;
    /**
     * This is to go back to the contacts page but cancelling any action
     * @param event this is after the Button to cancel is pressed
     * @throws IOException
     */
    @FXML void handleCancelAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/Contact.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.setTitle("Contacts");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * This is to save the data to the database
     * @param event save button pressed
     */
    @FXML void handleSaveAction(ActionEvent event){
        ContactsDOA contactDB = new ContactsDOA();
        try {
            String name = nameTF.getText();
            try {
                String email = emailTF.getText();
                Contacts contact = new Contacts(0, name, email);
                contactDB.insert(contact);
                goBack();
            } catch (Exception e) {
                e.printStackTrace();
                displayError(2);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            displayError(1);
            return;
        }
    }
    /**
     * This is to go back to the Contacts Page after successfully Saving 
     * @throws IOException if an error occurs
     */
    public void goBack() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/Contact.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.setTitle("Contacts");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * This is to show the error messages
     * @param alertNumber which is an integer that is passed based on the alert decision
     */
    public void displayError(int alertNumber) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        switch (alertNumber) {
            case 1: //This is if the Contact Name is empty
                alert.setTitle("Contact Name is empty");
                alert.setContentText("Please be sure to enter Contact Name");
                alert.showAndWait();
                break;
            case 2: //This is if the Email Address is empty
                alert.setTitle("Email Address is empty");
                alert.setContentText("Please enter Email Address");
                alert.showAndWait();
                break;
            default: //This is if the
                alert.setTitle("Cannot be empty");
                alert.setContentText("Please be sure to enter all the required fields");
                alert.showAndWait();
                break;
        }
    }
    /**
     * This is to initalize the Add the Contacts Page
     * @param arg0 which is the URL or address
     * @param arg1 which is the screen resourceBundle 
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }
    
}
