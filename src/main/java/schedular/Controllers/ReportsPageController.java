package schedular.Controllers;
/**
 * @author Syed Khurshid
 */
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import schedular.DOA.AppointmentDOA;
import schedular.DOA.ContactsDOA;
import schedular.DOA.CustomerDOA;
import schedular.Model.Appointments;
import schedular.Model.Contacts;
import schedular.Model.Customer;
import schedular.Model.DivisionInfoTable;
import schedular.Model.Type;

/**
 * This is the Report's page for the Appointments
 */
public class ReportsPageController implements Initializable {
    // This is the Appointment's month Column
    @FXML
    private TableColumn<Type, String> apptMonthCol;
    /**
     * This is the Appointment Type Column for the Appointments Table
     */
    @FXML
    private TableColumn<Type, String> apptTypeCol;
    /**
     * This is the Appointments Total Column
     */
    @FXML
    private TableColumn<Type, Integer> apptTotalCol;
    /**
     * This is appointments Type Table
     */
    @FXML
    private TableView<Type> apptTypeTable;
    
    /**
     * This is the Divisions Customer Total Count of the Divisions Table
     */
    @FXML
    private TableColumn<DivisionInfoTable, Integer> divCustomerColumn;
    /**
     * This is the Division name Column of the Divisions Table
     */
    @FXML
    private TableColumn<DivisionInfoTable, String> divNameCol;
    /**
     * This is the Divisions Table that shows how Many Customers under a division
     */
    @FXML
    private TableView<DivisionInfoTable> divisionTable;
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
     * This is the type of the meeting for the Appointments Table
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
     * This is the Appointments DOA
     */
    private AppointmentDOA appointments = new AppointmentDOA();
    /**
     * This is the appointment Schedule Observable List
     */
    private ObservableList<Appointments> aptSchedule = FXCollections.observableArrayList();
    /**
     * This is the Customers 
     */
    private ObservableList<Contacts> contacts = FXCollections.observableArrayList();
    /**
     * This is the types observableArrayList that is for the reports table
     */
    private ObservableList<Type> types = FXCollections.observableArrayList();
    /**
     * This is the For the Divisions table information
     */
    private ObservableList<DivisionInfoTable> divVal = FXCollections.observableArrayList();
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
                apptTable.setItems(appointments.getByContactID(id));
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
        ContactsDOA contactDOA = new ContactsDOA();
        ObservableList<String> cus = FXCollections.observableArrayList();
        try {
            contacts = contactDOA.getAll();
            for (Contacts custom : contacts) {
                cus.add(String.valueOf(custom.getContact_ID()) + "." + " " + custom.getContactName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cus.add("All");
        customerChoice.getItems().addAll(cus);
    }
    /**
     * This is to go back to the main page
     * @param event Button click press
     * @throws IOException 
     */
    @FXML
    void gobacktoMainPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/MainPage.fxml"));
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.setTitle("Main Appointment");
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    /**
     * This is to initialize the Division Table
     */
    public void initializeDivTab() {
        CustomerDOA customerDOA = new CustomerDOA();
        try{
            divVal = customerDOA.getDivisions();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        divisionTable.setItems(divVal);
        divNameCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        divCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerTotal"));

    }
    /**
     * This is to initialize all the appointments type in reports table
     */
    public void initializingAptTab() {
        try{
            types = appointments.getAllAppointmentTypes();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        apptTypeTable.setItems(types);
        apptMonthCol.setCellValueFactory(new PropertyValueFactory<>("Months"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("Types"));
        apptTotalCol.setCellValueFactory(new PropertyValueFactory<>("count"));
    }
     /**
     * This is to help initialize the Table View
     */
    public void initializingTable(){
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
        initializingAptTab();
        initializeDivTab();
        
    }
}
