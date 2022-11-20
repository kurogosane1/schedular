/**
 * This is the modify Customers Controller section for editing Existin Customers
 * Form input validation can be necessary for any SQL injections and inconsistant values
 * NoSQL database would be better than relationship databases
 */
package schedular.Controllers;
/**
 * @author Syed Khurshid
 */
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import schedular.DOA.CustomerDOA;
import schedular.DOA.DivisionDOA;
import schedular.Model.Customer;
import schedular.Model.FirstLevelDivision;
/**
 * This is to modify the Customers information form
 */
public class ModifyCustomerController implements Initializable {
    /**
     * This is the Customers Object to store Customer information
     */
    public Customer customer;
    /**
     * THis is the Cancel button
     */
    @FXML
    private Button canceblButton;
    /**
     * This is the Customer Address Text Field
     */
    @FXML
    private TextField cusAddrTF;
    /**
     * This is the Customer Address Text Field
     */
    @FXML
    private Label cusAddressLabel;
    /**
     * This is the Customer ID label
     */
    @FXML
    private Label cusIDLabel;
    /**
     * THis is the Customer ID Text Field
     */
    @FXML
    private TextField cusIDTextField;
    /**
     * This is the Customer Name Text Field
     */
    @FXML
    private Label cusNameLabel;
    /**
     * This is the Customer Name Text Field
     */
    @FXML
    private TextField cusNameTF;
    /**
     * This is the Customer Phone Label
     */
    @FXML
    private Label cusPhoneLabel;
    /**
     * This is the Customer Phone Text Field
     */
    @FXML
    private TextField cusPhoneTF;
    /**
     * This is the Customer Postal Zip Code Label
     */
    @FXML
    private Label cusPostalLabel;
    /**
     * This is the Customer Postal Code Zip Field
     */
    @FXML
    private TextField cusPostalTF;
    /**
     * This is the Division Name Text Field
     */
    @FXML
    private TextField divNameTF;
    /**
     * This is the Division Choice box field
     */
    @FXML
    private ChoiceBox<Integer> divisionChoice;
    /**
     * This is the Save button
     */
    @FXML
    private Button saveCusButton;
    /**
     * This is the First level Division Object
     */
    private FirstLevelDivision fDivision;
    /**
     * This is the Division DOA
     */
    private DivisionDOA divDOA = new DivisionDOA();
    /**
     * This is the Observable List Array for division
     */
    private ObservableList<FirstLevelDivision> div = FXCollections.observableArrayList();
    /**
     * This is to cancel and redirect user to the Customer page if no change or modifications are being carried out
     * @param event which is a button press
     * @throws IOException when a screen change error occurs
     */
    @FXML
    void cancelAddCusAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/CustomerPage.fxml"));
        Stage stage = (Stage) canceblButton.getScene().getWindow();
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * This is the Customer Address Function button
     * @param event This is coming from the button press
     */
    @FXML
    void cusAddressAction(ActionEvent event) {}
    /**
     * Name customer name change action function
     * @param event This is coming from the action change
     */
    @FXML
    void cusNameAction(ActionEvent event) {}
    /**
     * Name change action function which is not being utilized
     * @param event from button press action
     */
    @FXML
    void cusPostalAction(ActionEvent event) {}
    /**
     * Name change action function. not being utilized
     * @param event from a button press action
     */
    @FXML
    void divNameAction(ActionEvent event) {}
      /**
     * This is to add the Customer when the form is filled
     * @param event which is from the Save Button Press
     * @throws SQLException when a error in SQL database is encountered
     */
    @FXML
    public void saveCusAction(ActionEvent event) throws SQLException {
        CustomerDOA customerDOA = new CustomerDOA();
        int id = Integer.parseInt(cusIDTextField.getText());
        try {// This incase the Name is empty ONLY!
            String customerName = cusNameTF.getText();
            try {// This is for the Customer Address being emptied
                String customerAddress = cusAddrTF.getText();
                try {
                    int post = Integer.parseInt(cusPostalTF.getText());
                    try {
                        int phone = Integer.parseInt(cusPhoneTF.getText());
                        try {
                            int selection = divisionChoice.getValue();
                            Customer customer = new Customer(id, customerName, customerAddress, String.valueOf(post),
                                    String.valueOf(phone), selection);
                            customerDOA.update(customer);
                            try {
                                goBackAfterSave();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (NullPointerException e) {
                            displayError(5);
                            return;
                        }
                    } catch (NullPointerException e) {
                        displayError(4);
                        return;
                    } catch (NumberFormatException e) {
                        displayError(7);
                        return;
                    }
                } catch (NullPointerException e) {
                    displayError(3);
                    return;
                } catch (NumberFormatException e) {
                    displayError(6);
                    return;
                }
            } catch (NullPointerException e) {
                displayError(2);
                return;
            }
        } catch (NullPointerException e) {
            displayError(1);
            return; 
        }
    }
     /**
     * This is to return to the customer page if successfully processed
     * @throws IOException on screen change occurs
     */
     public void goBackAfterSave() throws IOException {
         System.out.println("Added customer");
         Parent root = FXMLLoader.load(getClass().getResource("/schedular/CustomerPage.fxml"));
         Stage stage = (Stage) saveCusButton.getScene().getWindow();
         stage.setTitle("Customers");
         stage.setScene(new Scene(root));
         stage.show();
         
     }
     /**
      * This is when the user makes a selection of the division automatically the division name is seen
      * @param event is a selection from the ChoiceBox
      */
     public void changeDivision(ActionEvent event) {
         int choice = divisionChoice.getValue();
         try {
             fDivision = divDOA.get(choice);
            divNameTF.setText(fDivision.getDivision());
        } catch (SQLException e) {
            e.printStackTrace();
        }
     }
     /**
      * This is to fill in the choice related fields with the selections made
      */
     public void choiceboxFill() {
         ArrayList<Integer> choices = new ArrayList<Integer>();
         int selected = divisionChoice.getValue();
         try {
             div = divDOA.getAll();
             for (FirstLevelDivision first : div) {
                 choices.add(first.getCountryID());
                 if (first.getCountryID() == selected) {
                     divNameTF.setText(first.getDivision());
                 }
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
         divisionChoice.getItems().addAll(choices);
     }
    /**
     * This is where the Customer information is carried here to fill the text fields
     * @param customer Object is brought in from the Customers Page
     */
    public void ModCustomer(Customer customer) {
        this.customer = customer;
        cusIDTextField.setText(String.valueOf(customer.getCustomerId()));
        cusNameTF.setText(customer.getCustomerName());
        cusAddrTF.setText(customer.getCustomerAddress());
        cusPostalTF.setText(customer.getPostalCode());
        cusPhoneTF.setText(customer.getPhoneNumber());
        divisionChoice.setValue(customer.getDivision_id());
        FirstLevelDivision d;
        choiceboxFill();
        try {
            d = divDOA.get(divisionChoice.getValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
      * A simple error producing function
      * @param alertNumber which is the integer that is passed
      */
     public void displayError(int alertNumber) {
         Alert alert = new Alert(Alert.AlertType.WARNING);
         switch (alertNumber) {
             case 1: // This is if Customer Name is empty ONLY!
                 alert.setTitle("Empty Customer Name");
                 alert.setContentText("You have not entered a customer name");
                 alert.showAndWait();
                 break;
             case 2: // This is in case the Customer Address is empty ONLY!
                 alert.setTitle("Empty Address");
                 alert.setContentText("You have not entered the customer address");
                 alert.showAndWait();
                 break;
             case 3: // This is in case the Postal Code is empty ONLY!
                 alert.setTitle("Empty Postal Code");
                 alert.setContentText("You have not entered the Postal Code");
                 alert.showAndWait();
                 break;
             case 4: // This is when the Phone Number is not Entered
                 alert.setTitle("Empty Phone Number");
                 alert.setContentText("You have not entered the Phone Number");
                 alert.showAndWait();
                 break;
             case 5: // This is when the Division choicebox is not selected
                 alert.setTitle("Division has not been selected");
                 alert.setContentText("You have not selected a division");
                 alert.showAndWait();
                 break;
             case 6: // This is incase the Postal Code is entered not in Number Format
                 alert.setTitle("Postal Code incorrect Format");
                 alert.setContentText("Postal Code needs to be in Number format. Example is 77777");
                 alert.showAndWait();
                 break;
             case 7: // This is for incorrect format is entered for Phone Number
                 alert.setTitle("Incorrect Phone Number format");
                 alert.setContentText("Only numbers can be entered for Phone Number");
                 alert.showAndWait();
             default: // This is when none of the fields are entered
                 alert.setTitle("All Fields are empty");
                 alert.setContentText("Cannot save customer when all the fields are empty");
                 alert.showAndWait();
                 break;
         }
     }
    /**
     * Main initializing page function
     * @param arg0 is the URL
     * @param arg1 is the resourceBundle
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        divisionChoice.setOnAction(this::changeDivision);
    } 
}
