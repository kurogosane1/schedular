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
 * This is to add Custoemrs to the Table
 */
public class AddCustomerController implements Initializable {
     @FXML
    private Button canceblButton;

    @FXML
    private TextField cusAddrTF;

    @FXML
    private Label cusAddressLabel;

    @FXML
    private Label cusIDLabel;

    @FXML
    private TextField cusIDTextField;

    @FXML
    private Label cusNameLabel;

    @FXML
    private TextField cusNameTF;

    @FXML
    private Label cusPhoneLabel;

    @FXML
    private Label cusPostalLabel;

    @FXML
    private TextField cusPostalTF;

    @FXML
    private TextField divNameTF;

    @FXML
    private ChoiceBox<Integer> divisionChoice;

    @FXML
    private TextField phoneTF;

    @FXML
    private Button saveCusButton;

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
    //TODO Add checks to see if all the fileds have been filled or not
    @FXML
    public void saveCusAction(ActionEvent event) throws SQLException {
        CustomerDOA customerDOA = new CustomerDOA();
        ObservableList<Customer> customers = customerDOA.getAll();
        String customer_name = cusNameTF.getText();
        String customer_Address = cusAddrTF.getText();
        String postalCode = cusPostalTF.getText();
        String phone = phoneTF.getText();
        int divisionID = divisionChoice.getValue();
        Customer customer = new Customer(customers.size(), customer_name, customer_Address, postalCode, phone,
                divisionID);
        customerDOA.insert(customer);
        try {
            goBackAfterSave();
        } catch (IOException e) {
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
    }
    /**
     * This is to return to the customer page if successfully processed
     * @throws IOException
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
     * This is to fill the choice box for the user to select the divisions
     */
    public void choiceboxFill(){
        DivisionDOA divisions = new DivisionDOA();
        ArrayList<Integer> choices= new ArrayList<Integer>();
        try {
            ObservableList<FirstLevelDivision> div = divisions.getAll();
            for (FirstLevelDivision first : div) {
                System.out.println(first.getDivision());
                choices.add(first.getCountryID());
            }
            // divisionChoice.getItems().addAll(choices);
            System.out.println("This is after the for loop");
            System.out.println(choices.size());
            divisionChoice.getItems().addAll(choices);
        } catch (SQLException e) {
          
            e.printStackTrace();
        }
    }
}
   // @FXML
    // private Label cusIDLabel;
    // @FXML
    // private TextField cusIDTextField;
    // @FXML
    // private Button canceblButton;
    // @FXML
    // private TextField cusAddrTF;
    // @FXML
    // private Label cusAddressLabel;
    // @FXML
    // private Label cusNameLabel;
    // @FXML
    // private TextField cusNameTF;
    // @FXML
    // private Label cusPhoneLabel;
    // @FXML
    // private Label cusPostalLabel;
    // @FXML
    // private TextField cusPostalTF;
    // @FXML
    // private TextField divNameTF;
    // @FXML
    // private ChoiceBox<?> divisionChoice;
    // @FXML
    // private Button saveCusButton;

    // @FXML
    // void cancelAddCusAction(ActionEvent event) throws IOException {
    //     Parent root = FXMLLoader.load(getClass().getResource("/schedular/CustomerPage.fxml"));
    //     Stage stage = (Stage) canceblButton.getScene().getWindow();
    //     stage.setTitle("Main Screen");
    //     stage.setScene(new Scene(root));
    //     stage.show();
    // }

    // @FXML
    // void cusAddressAction(ActionEvent event) {

    // }

    // @FXML
    // void cusNameAction(ActionEvent event) {

    // }

    // @FXML
    // void cusPostalAction(ActionEvent event) {

    // }

    // @FXML
    // void divNameAction(ActionEvent event) {

    // }

    // @FXML
    // void saveCusAction(ActionEvent event) throws SQLException {
    //     CustomerDOA customerDOA = new CustomerDOA();
    //     ObservableList<Customer> customers = customerDOA.getAll();
    //     String customer_name = cusNameTF.getText();
    //     String customer_Address = cusAddrTF.getText();
    //     String postalCode = cusPostalTF.getText();
        
    // }