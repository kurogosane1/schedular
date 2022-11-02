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
import schedular.Model.Customer;


public class MainPageController implements Initializable {
    /**
     * Add Appointment Button
     */
    @FXML
    private Button addApptButton;
    /**
     * Appointment ID Table Column
     */
    @FXML
    private TableColumn<?, ?> apptIDColumn;
    /**
     * Appointment Table View
     */
    @FXML
    private TableView<Customer> apptTable;
    /**
     * Contact Column of the Table
     */
    @FXML
    private TableColumn<?, ?> contactCol;
    /**
     * Customer Button to view Customers
     */
    @FXML
    private Button customerButton;
    /**
     * Customer ID Table Column
     */
    @FXML
    private TableColumn<?, ?> customerIDCol;
    /**
     * Delete Appointment Button
     */
    @FXML
    private Button deleteAppButton;
    /**
     * Appointment Description Table Column
     */
    @FXML
    private TableColumn<?, ?> descriptionCol;
    /**
     * Appointment End Date Table Column
     */
    @FXML
    private TableColumn<?, ?> endDateCol;
    /**
     * Appointment End Time Table Column
     */
    @FXML
    private TableColumn<?, ?> endTimeCol;
    /**
     * Location Table Column
     */
    @FXML
    private TableColumn<?, ?> locationCol;
    /**
     * Log Out Button Column
     */
    @FXML
    private Button logoutButton;
    /**
     * Modify Appointment Button for the table
     */
    @FXML
    private Button modApptButton;
    /**
     * Report Button for the Apppointment
     */
    @FXML
    private Button reportButton;
    /**
     * Start Time table Column
     */
    @FXML
    private TableColumn<?, ?> startTimeCol;
    /**
     * Appointment Title Column
     */
    @FXML
    private TableColumn<?, ?> titleCol;
    /**
     * Appointment Type TableColumn
     */
    @FXML
    private TableColumn<?, ?> typeCol;
    /**
     * User ID Table Column
     */
    @FXML
    private TableColumn<?, ?> userIDCol;
    /**
     * View All Appointments radio button for Table Column
     */
    @FXML
    private RadioButton viewAllRadio;
    /**
     * View Customer RadioButton
     */
    @FXML
    private RadioButton viewCustomerRadio;
    /**
     * View Monthly Radio Button
     */
    @FXML
    private RadioButton viewMonthRadio;
    /**
     * View Weekly Radio Button
     */
    @FXML
    private RadioButton viewWeeklyRadio;
    /**
     * Toggle Group for Radio Buttons
     */
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
// updatingCountryTest();
        // System.out.println("This ran");
        // gettingCountriesTEst();


  // public static void getCountry() throws SQLException {
    //     CountriesDOA countDOA = new CountriesDOA();
    //     ObservableList<Countries> count1 = countDOA.getAll();
    //     for (Countries count : count1) {
    //         System.out.println(count.getCountry());
    //     }
    // }
    // public static void updateCountry() throws SQLException {
    //     CountriesDOA countDOA = new CountriesDOA();
    //     Countries country = new Countries(2, "United Kingdom");
    //     countDOA.update(country);
    // }
        
    // public static void gettingCountriesTEst() {
    //     try {
    //         getCountry();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }

    // public static void updatingCountryTest() {
    //     try {
    //         updateCountry();          
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }