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
import schedular.DOA.CountriesDOA;
import schedular.DOA.CustomerDOA;
import schedular.DOA.DivisionDOA;
import schedular.Model.Countries;
import schedular.Model.Customer;
import schedular.Model.FirstLevelDivision;
/**
 * This is to modify the Customers information form
 */
public class ModifyCustomerController implements Initializable {
    /**
     * This is the Cancel Button
     */
    @FXML
    private Button canceblButton;
    /**
     * This is the Country Choicebox
     */
    @FXML
    private ChoiceBox<String> countryChoice;
    /**
     * Country Label
     */
    @FXML
    private Label countryLabel;
    /**
     * This is the Customer Address Text Field
     */
    @FXML
    private TextField cusAddrTF;
    /**
     * This is the Customer Address Label
     */
    @FXML
    private Label cusAddressLabel1;
    /**
     * This is the Customer ID Label
     */
    @FXML
    private Label cusIDLabel1;
    /**
     * This is the Customer ID Text Field
     */
    @FXML
    private TextField cusIDTextField;
    /**
     * This is the Customer Name Label
     */
    @FXML
    private Label cusNameLabel1;
    /**
     * This is the Customer Name Text Field
     */
    @FXML
    private TextField cusNameTF;
    /**
     * This is the Customer Phone Label
     */
    @FXML
    private Label cusPhoneLabel1;
    /**
     * This is the Customer Postal label
     */
    @FXML
    private Label cusPostalLabel1;
    /**
     * This is the Customer Postal Text Field
     */
    @FXML
    private TextField cusPostalTF;
    /**
     * This is the Phone Text Field
     */
    @FXML
    private TextField phoneTF;
    /**
     * This is the Customer Save Button
     */
    @FXML
    private Button saveCusButton;
    /**
     * This is the state Choice Button
     */
    @FXML
    private ChoiceBox<String> stateChoice;
    /**
     * This is the State Label
     */
    @FXML
    private Label stateLabel;
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
     * This is the Countries DOA to get country information
     */
     private CountriesDOA countriesDOA = new CountriesDOA();
     /**
     * This is all the countries observableArrayList
     */
    private ObservableList<Countries> countries = FXCollections.observableArrayList();
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
                        String phone = phoneTF.getText();
                        try {
                            // This is the Country selected
                            String sSelection = stateChoice.getValue();
                            div = divDOA.getAll();
                            int divID = divDOA.getDivisionID(sSelection);
                            int country_id = divDOA.getCountryID(sSelection);
                            Customer customer = new Customer(id, customerName, customerAddress, String.valueOf(post),
                                    phone, divID, country_id);
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
                displayError(2); // This is for Customer Address Being empty
                return;
            }
        } catch (NullPointerException e) {
            displayError(1); // This is for Customer Name being empty
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
  * This is where the Customer information is received from the Customer Controller page to be loaded in advance when the page initializes
  * @param customer object passed from the Customers page
  */
    public void ModCustomer(Customer customer) {
        ArrayList<String> countrys = new ArrayList<String>();
        // this.customer = customer;
        cusIDTextField.setText(String.valueOf(customer.getCustomerId()));
        cusNameTF.setText(customer.getCustomerName());
        cusAddrTF.setText(customer.getCustomerAddress());
        cusPostalTF.setText(customer.getPostalCode());
        phoneTF.setText(customer.getPhoneNumber());
        try {
            // Getting all the values for Countries first
            countries = countriesDOA.getAll();
            for (Countries c : countries) {
                countrys.add(c.getCountry());
            }
            // Getting Division for the country ID
            FirstLevelDivision division = divDOA.get(customer.getDivision_id());
            // Setting value first to be visible
            stateChoice.setValue(division.getDivision());
            Countries country = countriesDOA.get(division.getCountryID());
            // setting country value first to be visible when page loads
            countryChoice.setValue(country.getCountry());
            // Adds remaining list of choices to users
            countryChoice.getItems().addAll(countrys);

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
        countryChoice.setOnAction(this::changeDivision);
    } 
}
