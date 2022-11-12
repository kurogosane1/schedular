package schedular.Controllers;
import java.io.IOException;
import java.net.URL;
/**
 * @author Syed Khurshid
 */
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import schedular.DOA.AppointmentDOA;
import schedular.DOA.CustomerDOA;
import schedular.Model.Appointments;
import schedular.Model.Customer;

/**
 * This is the Reports page for the Appointments
 */
public class ReportsPageController implements Initializable {
    /**
     * This is the label for searching by Customer name
     */
    @FXML
    private Label customerLabel;
    /**
     * THis is the Appointment ID table Column
     */
    @FXML
    private TableColumn<Appointments, Integer> apptIDColumn;
    /**
     * This is the appointments Table
     */
    @FXML
    private TableView<Appointments> apptTable;
    /**
     * This is the Contacts Column for the Appointments table
     */
    @FXML
    private TableColumn<Appointments, String> contactCol;
    /**
     * This is the Choicebox that will show customers
     */
    @FXML
    private ChoiceBox<String> customerChoice;
    /**
     * This is the Customers ID column for the Appointments table
     */
    @FXML
    private TableColumn<Appointments, Integer> customerIDCol;
    /**
     * This is the Description column for the Appointments table
     */
    @FXML
    private TableColumn<Appointments, String> descriptionCol;
    /**
     * This is the End of the time column for the Appointments table
     */
    @FXML
    private TableColumn<Appointments, Date> endTimeCol;
    /**
     * This is the location column for the Appointments table
     */
    @FXML
    private TableColumn<Appointments, String> locationCol;
    /**
     * This is the radio button for selection of Monthly Appointments
     */
    @FXML
    private RadioButton monthlyRadio;
    /**
     * This is the radio button toggle functions
     */
    @FXML
    private ToggleGroup radioSelection;
    /**
     * This is the Starting Time of the Appointments
     */
    @FXML
    private TableColumn<Appointments, Timestamp> startTimeCol;
    /**
     * If this is the title of the Appointments Table
     */
    @FXML
    private TableColumn<Appointments, String> titleCol;
    /**
     * Thisis the type of the meeting for the Appointments Table
     */
    @FXML
    private TableColumn<Appointments, String> typeCol;
    /**
     * This is the User ID Code for the Appointments Table
     */
    @FXML
    private TableColumn<Appointments, Integer> userIDCol;
    /**
     * This is the Button to go back to the main page
     */
    @FXML
    private Button goBackButton;
    /**
     * This is the view All Radio button to show all the Appointments 
     */
    @FXML
    private RadioButton viewAllRadio;
    /**
     * This is the radio button that will show the appointments weekly
     */
    @FXML
    private RadioButton weeklyRadio;
    /**
     * This is the Appointments DOA
     */
    private AppointmentDOA appointments = new AppointmentDOA();
    /**
     * This is the appointment Schedule Observable List
     */
    private ObservableList<Appointments> aptSchedule = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    /**
     * This is the Monthly Radio Selection function to select the monthly appointments
     * @param event requires a radio button press
     * @throws SQLException
     */
    @FXML
    void monthlyRadioSelection(ActionEvent event) throws SQLException {
        apptTable.setItems(appointments.getApptsMonthly());
    }
    /**
     * This is the view all the appointments
     * @param event requires a radio button press
     * @throws SQLException
     */
    @FXML
    void viewAllSelection(ActionEvent event) throws SQLException {
        apptTable.setItems(appointments.getAll());
    }
    /**
     * This is to view the Appointments weekly
     * @param event requires a radio button press
     * @throws SQLException
     */
    @FXML
    void weeklyRadioSelection(ActionEvent event) throws SQLException {
        apptTable.setItems(appointments.getApptsWeekly());
    }
    /**
     * This is to get the Appointments by Customer Name
     * @param event is a selection of customer from the Choicebox
     */
    public void getAppointmentsByCustomer(ActionEvent event) {
        if (customerChoice.getValue() != null) {
            if (customerChoice.getValue().equals("All")) {
                try {
                    aptSchedule = appointments.getAll();
                    apptTable.setItems(aptSchedule);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
               
            } else {
             String str = customerChoice.getValue();
             int id = Integer.parseInt(str.substring(0, str.indexOf(".")));
             try {
                apptTable.setItems(appointments.getByCustomerID(id));
             } catch (SQLException e) {
                e.printStackTrace();
            }
           }
        }
    }
    /**
     * This is to get the Customer Names of the database
     */
    public void choiceBoxSelection() {
        CustomerDOA customerDOA = new CustomerDOA();
        ObservableList<String> cus = FXCollections.observableArrayList();
        try {
            customers = customerDOA.getAll();
            for (Customer custom : customers) {
                cus.add(String.valueOf(custom.getCustomerId()) + "." + " " + custom.getCustomerName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cus.add("All");
        customerChoice.getItems().addAll(cus);
    }
    @FXML
    void gobacktoMainPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/MainPage.fxml"));
         Stage stage = (Stage) goBackButton.getScene().getWindow();
         stage.setTitle("Main Appointment");
         stage.setScene(new Scene(root));
         stage.show();
    }
     /**
     * This is to help initialize the Table View
     */
    public void initializingTable() {
            try {
                aptSchedule = appointments.getAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                apptTable.setItems(aptSchedule);
                apptIDColumn.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
                titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
                descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
                locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
                typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
                startTimeCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
                endTimeCol.setCellValueFactory(new PropertyValueFactory<>("End"));
                customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
                userIDCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
                contactCol.setCellValueFactory(new PropertyValueFactory<>("contact_id"));  
    }
    /**
     * Main initializing page function
     * @param arg0 is the URL
     * @param arg1 is the resourceBundle
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initializingTable();
        choiceBoxSelection();
        customerChoice.setOnAction(this::getAppointmentsByCustomer);
        
    }
}