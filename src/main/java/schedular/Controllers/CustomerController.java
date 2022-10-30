package schedular.Controllers;
import java.io.IOException;
/**
 * @author Syed Khurshid
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class CustomerController implements Initializable {
    /**
     * 
     */
    @FXML
    private Button addCustButton;
    /**
     * 
     */
    @FXML
    private TableColumn<?, ?> addressColumn;

    @FXML
    private TableColumn<?, ?> customerIDColumn;

    @FXML
    private TableView<?> customerTableView;

    @FXML
    private Button delCusColumn;

    @FXML
    private TableColumn<?, ?> divisionIDColumn;

    @FXML
    private Button goBackButton;

    @FXML
    private Button modCustButton;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> phoneColumn;

    @FXML
    private TableColumn<?, ?> postalColumn;

    @FXML
    void addCustomerAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/AddCustomer.fxml"));
        Stage stage = (Stage) addCustButton.getScene().getWindow();
        stage.setTitle("Add Customer");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void delCustomerAction(ActionEvent event) {

    }

    @FXML
    void goBackAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/MainPage.fxml"));
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void modCustomerAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/ModifyCustomer.fxml"));
        Stage stage = (Stage) addCustButton.getScene().getWindow();
        stage.setTitle("Modify Customer Customer");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Customer Page was initialized");  
    }

}
