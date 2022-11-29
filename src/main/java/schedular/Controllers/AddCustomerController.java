/**
 * A a further detail customer form can be required
 * Form and state changes depending on the country that is selected
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
import schedular.DOA.CountriesDOA;
import schedular.DOA.CustomerDOA;
import schedular.DOA.DivisionDOA;
import schedular.Model.Countries;
import schedular.Model.Customer;
import schedular.Model.FirstLevelDivision;
import javafx.scene.text.Text;
/**
 * This is to add Customers to the Table
 */
public class AddCustomerController implements Initializable {
    /**
     * This is the add error Text 
     */
    @FXML private Text addErrorT;
    /**
     * This is the Cancel button
     */
    @FXML private Button canceblButton;
    /**
     * This is the Country Choicebox
     */
    @FXML private ChoiceBox<String> countryChoice;
    /**
     * This is the Country Label
     */
    @FXML private Label countryLabel;
    /**
     * This is the Customer Address Text Field
     */
    @FXML private TextField cusAddrTF;
    /**
     * This is the Customer Address Label
     */
    @FXML private Label cusAddressLabel;
    /**
     * This is the Customer ID Label
     */
    @FXML private Label cusIDLabel;
    /**
     * This is the Customer ID TextField
     */
    @FXML private TextField cusIDTextField;
    /**
     * This is the Customer Name Label
     */
    @FXML private Label cusNameLabel;
    /**
     * This is the Customer Name TF
     */
    @FXML private TextField cusNameTF;
    /**
     * This is the Customer Phone Number label
     */
    @FXML private Label cusPhoneLabel;
    /**
     * This is the Customer Postal Label
     */
    @FXML private Label cusPostalLabel;
    /**
     * This is the Customer Postal Code Text Field
     */
    @FXML private TextField cusPostalTF;
    /**
     * This is the Division ID error Text Field
     */
    @FXML private Text divisionIDError;
    /**
     * This is the name error text field
     */
    @FXML private Text nameErrorT;
    /**
     * This is the Phone Error Text Field
     */
    @FXML private Text phoneErrorT;
    /**
     * This is the Phone Text Field that the user will fill
     */
    @FXML private TextField phoneTF;
    /**
     * This is the postal error code text field
     */
    @FXML private Text postalErrorT;
    /**
     * This is the save button 
     */
    @FXML private Button saveCusButton;
    /**
     * This is the state choicebox that the user will select
     */
    @FXML private ChoiceBox<String> stateChoice;
    /**
     * This is the State or Province reference label
     */
    @FXML private Label stateLabel;
    /** 
     * This is the First Level Division Object
     */ 
    private FirstLevelDivision fDivision;
    /**
     * This is the Division DOA
     */
    private DivisionDOA divDOA = new DivisionDOA();
    /**
     * This is the Countries DOA to get country information
     */
    private CountriesDOA countriesDOA = new CountriesDOA();
    /**
     * This is all the countries observableArrayList
     */
    private ObservableList<Countries> countries = FXCollections.observableArrayList();
    /**
     * This is to cancel current Action and redirect user to the Customer Page
     * @param event is the Button press
     * @throws IOException when an error on screen change is encountered
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
     * @throws SQLException when error with the SQL database occurs
     */
    @FXML
    public void saveCusAction(ActionEvent event) throws SQLException {
        CustomerDOA customerDOA = new CustomerDOA();
        Integer customer_id = 0;
        // ObservableList<Customer> customers = customerDOA.getAll();
            try {// This is for empty Customer Name
            String customerName = cusNameTF.getText();
            try {// This is for empty Customer Address
                String address = cusAddrTF.getText();
                try {// This is for Empty Postal Code
                    String postalCode = cusPostalTF.getText();
                    try {// This is for Phone Number
                        String phone = phoneTF.getText();
                        try {
                            // This is the Country selected
                            String sSelection = stateChoice.getValue(); 
                            int divID = divDOA.getDivisionID(sSelection);
                            int country_id = divDOA.getCountryID(sSelection);
                            Customer customer = new Customer(customer_id,customerName,address,postalCode,phone,divID, country_id);
                            customerDOA.insert(customer);
                            try {
                                // This is to go back to the main page
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
        }catch(

    NullPointerException e)
    {
            displayError(1);
            return;
        }  
    }
    /**
     * This is to fill the choice box for the user to select the divisions
     * It also carries out an SQL exception that the function handles within itself than throwing it to the main
     * function
     */
    public void choiceboxFill() {
        ArrayList<String> choices = new ArrayList<String>();
           try{
               countries = countriesDOA.getAll();
               for (Countries country : countries) {
                   choices.add(country.getCountry());
               }
               countryChoice.getItems().addAll(choices);
           }
           catch (SQLException e) {
            e.printStackTrace();
           }
    }
     /**
     * This is to return to the customer page if successfully processed
     * @throws IOException for errors when the stage changes
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
      @param event which is a choicebox options
      */
     public void changeDivision(ActionEvent event) {
        ArrayList<String> stateChoices = new ArrayList<String>();
        ObservableList<FirstLevelDivision> div = FXCollections.observableArrayList();
        Countries country;
      try {
          country = countriesDOA.getIdByCountryName(countryChoice.getValue());
        // Getting the Country ID value from the name of the Ccountry
        Integer countryID = country.getCountryID();
         // Clearing out in case the User decides to choose something else
        stateChoice.getItems().clear();
        DivisionDOA divisions = new DivisionDOA();
        if (countryID != 0) {
            try {
                div = divisions.getAllByID(countryID);
                for (FirstLevelDivision first : div) {
                    stateChoices.add(first.getDivision());
                }
                stateChoice.getItems().addAll(stateChoices);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            // It will fill with empty state
            stateChoice.getItems().addAll(stateChoices);
        }
    } catch (SQLException e1) {
        e1.printStackTrace();
    } 
    }
    /**
     * Main initializing page function
     * @param arg0 is the URL
     * @param arg1 is the resourceBundle
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        choiceboxFill();
        countryChoice.setOnAction(this::changeDivision);
    }
}
   