package schedular.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class MainPageController implements Initializable{
      @FXML
    private Button addApptButton;

    @FXML
    private TableColumn<?, ?> apptIDColumn;

    @FXML
    private TableView<?> apptTable;

    @FXML
    private TableColumn<?, ?> contactCol;

    @FXML
    private Button customerButton;

    @FXML
    private TableColumn<?, ?> customerIDCol;

    @FXML
    private Button deleteAppButton;

    @FXML
    private TableColumn<?, ?> descriptionCol;

    @FXML
    private TableColumn<?, ?> endDateCol;

    @FXML
    private TableColumn<?, ?> endTimeCol;

    @FXML
    private TableColumn<?, ?> locationCol;

    @FXML
    private Button logoutButton;

    @FXML
    private Button modApptButton;

    @FXML
    private Button reportButton;

    @FXML
    private TableColumn<?, ?> startTimeCol;

    @FXML
    private TableColumn<?, ?> titleCol;

    @FXML
    private TableColumn<?, ?> typeCol;

    @FXML
    private TableColumn<?, ?> userIDCol;

    @FXML
    private RadioButton viewAllRadio;

    @FXML
    private RadioButton viewCustomerRadio;

    @FXML
    private RadioButton viewMonthRadio;

    @FXML
    private RadioButton viewWeeklyRadio;

    @FXML
    private ToggleGroup viewsToggle;

    @FXML
    void addAppoint(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/AddAppointment.fxml"));
        Stage stage = (Stage) addApptButton.getScene().getWindow();
        stage.setTitle("Add Appointment");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void deleteApptAction(ActionEvent event) {

    }
    /**
     * 
     * @param event
     * @throws IOException
     */
    @FXML
    void logOut(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/Login.fxml"));
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * 
     * @param event
     */
    @FXML
    void modifyAppt(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/EditAppointment.fxml"));
        Stage stage = (Stage) modApptButton.getScene().getWindow();
        stage.setTitle("Modify Appointment");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * 
     * @param event
     */
    @FXML
    void radioAll(ActionEvent event) {

    }
    /**
     * 
     * @param event
     */
    @FXML
    void reportGen(ActionEvent event) {

    }
    /**
     * 
     * @param event
     * @throws IOException
     */
    @FXML
    void toCustomerPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/CustomerPage.fxml"));
        Stage stage = (Stage) customerButton.getScene().getWindow();
        stage.setTitle("Customer Info");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * 
     * @param event
     */
    @FXML
    void viewCustomer(ActionEvent event) {

    }
    /**
     * 
     * @param event
     */
    @FXML
    void viewMonthly(ActionEvent event) {

    }
    /**
     * 
     * @param event
     */
    @FXML
    void viewWeekly(ActionEvent event) {

    } 
    /**
     * 
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       System.out.println("Main Page has been initialized");
        
    }

}

    // @FXML
    // private Button addApptButton, deleteAppButton, logoutButton, modApptButton, reportButton;
    // @FXML
    // private RadioButton viewAllRadio, viewCustomerRadio, viewMonthRadio, viewWeeklyRadio;
    // @FXML
    // private ToggleGroup viewsToggle;

    // @FXML
    // private TableView<?> apptTable;

    // @FXML
    // private TableColumn<?, ?> contactCol;

    // @FXML
    // private TableColumn<?, ?> customerIDCol;

    // @FXML
    // private TableColumn<?, ?> descriptionCol;

    // @FXML
    // private TableColumn<?, ?> endDateCol;

    // @FXML
    // private TableColumn<?, ?> endTimeCol;

    // @FXML
    // private TableColumn<?, ?> locationCol;

    // @FXML
    // private TableColumn<?, ?> startTimeCol;

    // @FXML
    // private TableColumn<?, ?> titleCol;

    // @FXML
    // private TableColumn<?, ?> typeCol;

    // @FXML
    // private TableColumn<?, ?> userIDCol;

  

    // @FXML
    // void addAppoint(ActionEvent event) {

    // }

    // @FXML
    // void deleteApptAction(ActionEvent event) {

    // }

    // @FXML
    // void logOut(ActionEvent event) throws IOException {
    //     Parent root = FXMLLoader.load(getClass().getResource("/schedular/Login.fxml"));
    //     Stage stage = (Stage) logoutButton.getScene().getWindow();
    //     stage.setTitle("Main Screen");
    //     stage.setScene(new Scene(root));
    //     stage.show();
    // }

    // @FXML
    // void modifyAppt(ActionEvent event) {

    // }

    // @FXML
    // void radioAll(ActionEvent event) {

    // }

    // @FXML
    // void reportGen(ActionEvent event) {

    // }

    // @FXML
    // void viewCustomer(ActionEvent event) {

    // }

    // @FXML
    // void viewMonthly(ActionEvent event) {

    // }

    // @FXML
    // void viewWeekly(ActionEvent event) {

    // }

    // @Override
    // public void initialize(URL arg0, ResourceBundle arg1) {
    //    System.out.println("Main Page has been initialized");
        
    // }