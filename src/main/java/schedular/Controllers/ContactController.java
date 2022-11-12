package schedular.Controllers;
/**
 * @author Syed Khurshid
 */
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import schedular.DOA.ContactsDOA;
import schedular.Model.Contacts;

/**
 * This is the contacts page
 */
public class ContactController implements Initializable{
   /**
   * This is the button to add Contacts.
   */
    @FXML private Button addContButton;
    /**
     * This is the button to Go Back to the Main page
     */
    @FXML private Button goBackButton;
    /**
     * This is to modify Contact Button
     */
    @FXML private Button modConButton;
    /**
     * This is to delete Contact Button
     */
    @FXML private Button delContButton;
    /**
     * This is the Contact ID Column of the Contact Table
     */
    @FXML private TableColumn<Contacts, Integer> contactIDColumn;
    /**
     * This is the Contact Name Column of the Contact Table
     */
    @FXML private TableColumn<Contacts, String> contactNameColumn;
    /**
     * This is the Contact Table Column
     */
    @FXML private TableView<Contacts> contactTable;
    /**
     * This is the email column of the Contact Table
     */
    @FXML private TableColumn<Contacts, String> emailColumn;
    /**
     * This is the contactsDOA from the Contact Table
     */
    private ContactsDOA contactDOA = new ContactsDOA();
    /**
     * This is the Contacts Observable List for the table
     */
    private ObservableList<Contacts> contacts = FXCollections.observableArrayList();
    /**
     * This is the action to go Contacts Form 
     * @param event Button press action
     * @throws IOException
     */
    @FXML void addContactAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/AddContact.fxml"));
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.setTitle("Add Contact");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * This is to delete the Contact from the database and list
     * @param event is when the button is pressed to delete the contact
     */
    @FXML void deleteContact(ActionEvent event) {
        if (contactTable.getSelectionModel().getSelectedItems() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a Contact to delete");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    " This will permanently delete the contact selected \n Are you sure you want to delete this contact?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Contacts contact = contactTable.getSelectionModel().getSelectedItem();
                contacts.remove(contact);
                try {
                    contactDOA.delete(contact); 
                } catch (SQLException e) {
                   e.printStackTrace();
                }
            }
        }
    }
    /**
     * This is to Go Back to the Main page
     * @param event action from the Go Back Button press
     * @throws IOException if an error occurs
     */
    @FXML void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/MainPage.fxml"));
         Stage stage = (Stage) goBackButton.getScene().getWindow();
         stage.setTitle("Main Appointment");
         stage.setScene(new Scene(root));
         stage.show();
    }
    /**
     * This is to modify the Contacts in the database
     * @param event this is when the button is pressed
     * @throws IOException if an error occurs
     */
    @FXML void modContact(ActionEvent event) throws IOException {
        if (contactTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/schedular/EditContacts.fxml"));
            Parent root = loader.load();
            EditContactController mod = loader.getController();
            Contacts contact = contactTable.getSelectionModel().getSelectedItem();
            mod.modContact(contact);
            Stage stage = (Stage) modConButton.getScene().getWindow();
            stage.setTitle("Edit Contact");
            stage.setScene(new Scene(root));
            stage.show();
        } else {
           Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a Contact to Modify from the list");
            alert.showAndWait();   
        }
    }
    /**
     * This is to initialize the Contact Table
     */
    public void initializeTable() {
        try {
            contacts = contactDOA.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        contactTable.setItems(contacts);
        contactIDColumn.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
    }
    /**
     * This is the initializing function
     * @param arg0 is the URL
     * @param arg1 is the resourceBundle
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       initializeTable();
    } 
}
