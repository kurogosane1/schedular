package schedular.Controllers;
/**
 * @author Syed Khurshid
 */
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import schedular.DOA.CustomerDOA;
import schedular.DOA.DivisionDOA;
import schedular.Model.Customer;
import schedular.Model.FirstLevelDivision;
import javafx.scene.text.Text;
/**
 * This is to add Customers to the Table
 */
public class AddCustomerController implements Initializable {
    /**
     * These are the errors texts for Customer Name, Address, Phone, Postal Code
     */
    @FXML
    private Text addErrorT,nameErrorT, divisionIDError, phoneErrorT, postalErrorT;
    /**
     * This is the Cancel button
     */
    @FXML
    private Button canceblButton;
    /**
     * This is the Customer Address Text Field
     */
    @FXML
    private TextField cusAddrTF;
    /**
     * This is the Customer Address Label
     */
    @FXML
    private Label cusAddressLabel;
    /**
     * This is the Customer ID Label
     */
    @FXML
    private Label cusIDLabel;
    /**
     * This is the Customer ID Text Field
     */
    @FXML
    private TextField cusIDTextField;
    /**
     * This is the Customer Name Label
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
     * This is the customer Postal Label
     */
    @FXML
    private Label cusPostalLabel;
    /**
     * This is the Customer Postal TextField
     */
    @FXML
    private TextField cusPostalTF;
    /**
     * This is the Division name Text Field
     */
    @FXML
    private TextField divNameTF;
    /**
     * This is the Division Choice box
     */
    @FXML
    private ChoiceBox<Integer> divisionChoice;
    /**
     * This is the Phone Text Field
     */
    @FXML
    private TextField phoneTF;
    /**
     * This is to Save Customer Button
     */
    @FXML
    private Button saveCusButton;
    /** 
     * This is the First Level Division Object
     */ 
    private FirstLevelDivision fDivision;
    /**
     * This is the Division DOA
     */
    private DivisionDOA divDOA = new DivisionDOA();
    /**
     * This is to cancel current Action and redirect user to the Customer Page
     * @param event is the Button press
     * @throws IOException 
     */
    @FXML
    void cancelAddCusAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/CustomerPage.fxml"));
        Stage stage = (Stage) canceblButton.getScene().getWindow();
        stage.setTitle("Customers");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * This is to add the Customer when the form is filled
     * @param event which is from the Save Button Press
     * @throws SQLException
     */
    @FXML
    public void saveCusAction(ActionEvent event) throws SQLException {
        CustomerDOA customerDOA = new CustomerDOA();
        ObservableList<Customer> customers = customerDOA.getAll();
            try {// This is for empty Customer Name
            String customerName = cusNameTF.getText();
            try {// This is for empty Customer Address
                String address = cusAddrTF.getText();
                try {// This is for Empty Postal Code
                    String postalCode = cusPostalTF.getText();
                    try {// This is for Phone Number
                        int phone = Integer.parseInt(phoneTF.getText());
                        try {
                            int selection = divisionChoice.getValue();
                            Customer customer = new Customer(customers.size(), customerName, address, postalCode,
                                    String.valueOf(phone), selection);
                            customerDOA.insert(customer);
                            try {
                                goBackAfterSave();
                            } catch (IOException e) {
                                e.printStackTrace();
                                return;
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
     * This is to fill the choice box for the user to select the divisions
     */
    public void choiceboxFill() {
        DivisionDOA divisions = new DivisionDOA();
        ArrayList<Integer> choices = new ArrayList<Integer>();
        try {
            ObservableList<FirstLevelDivision> div = divisions.getAll();
            for (FirstLevelDivision first : div) {
                System.out.println(first.getDivision());
                choices.add(first.getCountryID());
            }
            divisionChoice.getItems().addAll(choices);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     /**
     * This is to return to the customer page if successfully processed
     * @throws IOException
     */
     public void goBackAfterSave() throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/schedular/CustomerPage.fxml"));
         Stage stage = (Stage) saveCusButton.getScene().getWindow();
         stage.setTitle("Customers");
         stage.setScene(new Scene(root));
         stage.show();
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
      * This is for when the user decides on the Division and then selections lead to a drop down list to be visible
      */
     public void changeDivision(ActionEvent event) {
         int choice = divisionChoice.getValue();
        try {
            fDivision = divDOA.get(choice);
            divNameTF.setText(fDivision.getDivision());
        } catch (Exception e) {
          e.printStackTrace();
        }
    }
    /**
     * Main initializing page function
     * @param arg0 is the URL
     * @param arg1 is the resourceBundle
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Add Customers Page has been initialized");
        choiceboxFill();
        divisionChoice.setOnAction(this::changeDivision);
    }
}
   