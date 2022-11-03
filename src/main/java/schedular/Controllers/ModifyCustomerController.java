package schedular.Controllers;
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
import schedular.DOA.DivisionDOA;
import schedular.Model.Customer;
import schedular.Model.FirstLevelDivision;

public class ModifyCustomerController implements Initializable {
    public Customer customer;
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
    private TextField cusPhoneTF;

    @FXML
    private Label cusPostalLabel;

    @FXML
    private TextField cusPostalTF;

    @FXML
    private TextField divNameTF;

    @FXML
    private ChoiceBox<Integer> divisionChoice;

    @FXML
    private Button saveCusButton;

    @FXML
    void cancelAddCusAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/CustomerPage.fxml"));
        Stage stage = (Stage) canceblButton.getScene().getWindow();
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void cusAddressAction(ActionEvent event) {

    }

    @FXML
    void cusNameAction(ActionEvent event) {

    }

    @FXML
    void cusPostalAction(ActionEvent event) {

    }

    @FXML
    void divNameAction(ActionEvent event) {

    }

    @FXML
    void saveCusAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public void ModCustomer(Customer customer) {
        this.customer = customer;
        System.out.println(customer.getCustomerName());
        cusIDTextField.setText(String.valueOf(customer.getCustomerId()));
        cusNameTF.setText(customer.getCustomerName());
        cusAddrTF.setText(customer.getCustomerAddress());
        cusPostalTF.setText(customer.getPostalCode());
        cusPhoneTF.setText(customer.getPhoneNumber());
        choiceboxFill();
        divisionChoice.setValue(customer.getDivision_id());
        // if (divisionChoice.getValue() == customer.getDivision_id()) {
        //     divisionChoice.setValue(customer.getDivision_id());
        // }
        // System.out.println(divisionChoice.getSelectionModel().selectedIndexProperty());
        System.out.println(divisionChoice.getItems().size());
        System.out.println(customer.getDivision_id());
        System.out.println(divisionChoice.equals(customer.getDivision_id()));
        // System.out.println( divisionChoice.showingProperty());
        // for (int i = 0; i < divisionChoice.getItems().size(); i++) {
        //     if (divisionChoice.getItems().equals(customer.getDivision_id())) {
        //         System.out.println("This is the divisions section");
        //         System.out.println(divisionChoice.getItems());

        //     }
        // }
        
    }
    
     public void choiceboxFill(){
        DivisionDOA divisions = new DivisionDOA();
        ArrayList<Integer> choices= new ArrayList<Integer>();
        try {
            ObservableList<FirstLevelDivision> div = divisions.getAll();
            for (FirstLevelDivision first : div) {
                choices.add(first.getCountryID());
            }
            // divisionChoice.getItems().addAll(choices);
            divisionChoice.getItems().addAll(choices);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
