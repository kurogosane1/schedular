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
 * This is to edit the Contacts Page
 */
public class EditContactController implements Initializable{
    /**
     * This is the Cancel button 
     */
    @FXML private Button cancelButton;
    /**
     * This is the Email Text Field
     */
    @FXML private TextField emailTF;
    /**
     * This is the name Text Field
     */
    @FXML private TextField nameTF;
    /**
     * This is the save button Text Field
     */
    @FXML private Button saveButton;
    /**
     * This is the Contacts Class Object
     */
    private Contacts contact;
    /** 
     * This is the function to handle the Cancel button Action
     * @param event This is the cancel Button Press Action
     * @throws IOException if an error occurs
     */
    @FXML
    void handleCancelAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/Contact.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.setTitle("Contacts");
        stage.setScene(new Scene(root));
        stage.show();
    }
 /**
   * This is to dave the data
   * @param event This is when the Save button is clicked
  */
    @FXML
    void handleSaveAction(ActionEvent event) {
        ContactsDOA contactDB = new ContactsDOA();
        try{
            String name = nameTF.getText();
            try {
                String email = emailTF.getText();
                int id = contact.getContact_ID();
                contact.setContactName(name);
                contact.setEmailAddress(email);
                contactDB.update(contact);
                goBack();
            } catch (Exception e) {
                e.printStackTrace();
                displayError(2);
                return;
            }
        }   
        catch (Exception e) {
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
     * This is to Display Errors 
     * @param alertNumber an integer that triggers the alert
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
     * This is to get the Contact Object from the Contacts Page
     * @param t this is the Contact Object
     */
    public void modContact(Contacts t) {
        nameTF.setText(t.getContactName());
        emailTF.setText(t.getEmailAddress());
        contact = new Contacts(t.getContact_ID(), t.getContactName(), t.getEmailAddress());
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }
    
}
