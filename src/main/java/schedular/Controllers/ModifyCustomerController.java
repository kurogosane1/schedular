package schedular.Controllers;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyCustomerController {
    
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
    private ChoiceBox<?> divisionChoice;

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

}
