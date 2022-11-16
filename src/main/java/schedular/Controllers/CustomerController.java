/**
 * Possibly better customization of code can be done. Placing more object orientated format of the code where we can reutilize the code
 * @author Syed Khurshid
 */
package schedular.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
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
import schedular.DOA.AppointmentDOA;
import schedular.DOA.CustomerDOA;
import schedular.Model.Customer;

/**
 * This is the Customer Controller for the Customer controller page to be developed
 */
public class CustomerController implements Initializable {
    CustomerDOA cus = new CustomerDOA();
    /**
     * This is the add Customer Button
     */
    @FXML
    private Button addCustButton;
    /**
     * Address Column of the Customer
     */
    @FXML
    private TableColumn<Customer, String> addressColumn;
    /**
     * Customer ID column of the Customer
     */
    @FXML
    private TableColumn<Customer, Integer> customerIDColumn;
    /**
     * Customer Table View on the page
     */
    @FXML
    private TableView<Customer> customerTableView;
    /**
     * Button to delete Customer
     */
    @FXML
    private Button delCusColumn;
    /**
     * Division that was selected to be added
     */
    @FXML
    private TableColumn<Customer, Integer> divisionIDColumn;
    /**
     * This is to go back button 
     */
    @FXML
    private Button goBackButton;
    /**
     * Modify Customer Button
     */
    @FXML
    private Button modCustButton;
    /**
     * Name of the Customer Column
     */
    @FXML
    private TableColumn<Customer, String> nameColumn;
    /**
     * The Phone Column of the Customer
     */
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    /**
     * The Postal Code Column of the Customer
     */
    @FXML
    private TableColumn<Customer, String> postalColumn;
    /**
     * This is to add new Customers and direct user to the Customer Forms
     * @param event Button action button press
     * @throws IOException
     */
    @FXML
    void addCustomerAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/AddCustomer.fxml"));
        Stage stage = (Stage) addCustButton.getScene().getWindow();
        stage.setTitle("Add Customer");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * This is a button to delete the Customer but after its checked if the user has an appointment and if they do then the appointments are removed first
     * @param event Button press action
     */
    @FXML
    public void delCustomerAction(ActionEvent event) {
        AppointmentDOA apptDOA = new AppointmentDOA();
        if (customerTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select Customer to remove from List");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "This will permanently remove the customer selected \n This will also result in any other appointments related to be deleted as well? \n Are you sure you want to Continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Customer c = customerTableView.getSelectionModel().getSelectedItem();
                try {
                    apptDOA.deleteByCustomerID(c.getCustomerId());
                    cus.delete(c);
                    ObservableList<Customer> customers = cus.getAll();
                    customerTableView.setItems(customers);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
      }
    }
    /**
     * This is to take user out of the Customers page
     * @param event Button press action
     * @throws IOException
     */
    @FXML
    public void goBackAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/MainPage.fxml"));
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * This is the modify Button Press to take the User to the Form to modify input
     * @param event Button press action
     * @throws IOException
     */
    @FXML
    public void modCustomerAction(ActionEvent event) throws IOException {
        if(customerTableView.getSelectionModel().getSelectedItem()!=null)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/schedular/ModifyCustomer.fxml"));
            Parent root = loader.load();
            ModifyCustomerController mod = loader.getController();
            Customer customer = customerTableView.getSelectionModel().getSelectedItem();
            mod.ModCustomer(customer);
            Stage stage = (Stage) addCustButton.getScene().getWindow();
            stage.setTitle("Modify Customer Customer");
            stage.setScene(new Scene(root));
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a Customer to Update from the list");
            alert.showAndWait(); 
        }
    }
    
    /**
     * This is to initialize the page
     * @param arg0 which is from the URL
     * @param arg1 Which is the resourceBundle
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Customer Page was initialized");
        CustomerDOA customer = new CustomerDOA();
        
        try {
            ObservableList<Customer> customers = customer.getAll();
            customerTableView.setItems(customers);
            customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            postalColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            divisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("Division_id"));
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
