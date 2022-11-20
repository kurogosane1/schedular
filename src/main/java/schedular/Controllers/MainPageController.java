/**
 * Future use of NoSQL database connection instead of MysQl Connection
 * Better layout of tables 
 * 
 */
package schedular.Controllers;
/**
 * @author Syed Khurshid
 */
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
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
import schedular.utilities.LoginLog;

/**
 * This is the main page point of entry that displays all the appointments as well viewing the  appointments by week, month and all.
 */
public class MainPageController implements Initializable {
    /**
     * Add Appointment Button
     */
    @FXML private Button addApptButton; 
    /**
     * Appointment ID Table Column
     */
    @FXML private TableColumn<Appointments, Integer> apptIDColumn;
    /**
     * Appointment Table View
     */
    @FXML private TableView<Appointments> apptTable;
    /**
     * Contact Column of the Table
     */
    @FXML private TableColumn<Appointments, String> contactCol;
    /**
     * Customer Button to view Customers
     */
    @FXML private Button customerButton;  
    /**
     * Customer ID Table Column
     */
    @FXML private TableColumn<Appointments, Integer> customerIDCol;
    /**
     * Delete Appointment Button
     */
    @FXML private Button deleteAppButton;
    /**
     * Appointment Description Table Column
     */
    @FXML private TableColumn<Appointments, String> descriptionCol;
    /**
     * Appointment End Time Table Column
     */
    @FXML private TableColumn<Appointments, Date> endTimeCol;
    /**
     * Location Table Column
     */
    @FXML private TableColumn<Appointments, String> locationCol;
    /**
     * Log Out Button Column
     */
    @FXML private Button logoutButton;
    /**
     * Modify Appointment Button for the table
     */
    @FXML private Button modApptButton;
    /**
     * Report Button to get the Appointment Report
     */
    @FXML private Button reportButton; 
    /**
     * Start Time table Column
     */
    @FXML private TableColumn<Appointments, Timestamp> startTimeCol;
    /**
     * Appointment Title Column
     */
    @FXML private TableColumn<Appointments, String> titleCol;
    /**
     * Appointment Type TableColumn
     */
    @FXML private TableColumn<Appointments, String> typeCol;  
    /**
     * User ID Table Column
     */
    @FXML private TableColumn<Appointments, Integer> userIDCol;
    /**
     * View All Appointments radio button for Table Column
     */
    @FXML private RadioButton viewAllRadio;
    /**
     * View Monthly Radio Button
     */
    @FXML private RadioButton viewMonthRadio;
    /**
     * View Weekly Radio Button
     */
    @FXML private RadioButton viewWeeklyRadio;
    /**
     * Toggle Group for Radio Buttons
     */
    @FXML private ToggleGroup viewsToggle; 
    /**
     * This is the button for going to Contacts page
     */
    @FXML private Button contactsButton; 
    /**
     * This is the Appointments DOA
     */
    private AppointmentDOA appointments = new AppointmentDOA();
    /**
     * This is the appointment Schedule Observable List
     */
    private ObservableList<Appointments> aptSchedule = FXCollections.observableArrayList();
    /**
     * This is the button to add a new appointment
     * @param event which is the add appointment button
     * @throws IOException when Stage is switched
     */
    @FXML
    public void addAppoint(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/AddAppointment.fxml"));
        Stage stage = (Stage) addApptButton.getScene().getWindow();
        stage.setTitle("Add Appointment");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * This is to delete an appointment
     * @param event which is after the delete appointment button has been pressed
     * 
     */
    @FXML
    public void deleteApptAction(ActionEvent event) {
        if (apptTable.getSelectionModel().getSelectedItems() == null || apptTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select an appointment to delete");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Please check");
            alert.setContentText("This will permanently delete an appointment, are you sure you want to delete this appointment");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Appointments appointment = apptTable.getSelectionModel().getSelectedItem();
                try {
                    Boolean success = aptSchedule.remove(appointment);
                    if (!success) {
                        alert.setTitle("Failure to delete appointment");
                        alert.setContentText("SQL issues in deleting appointment. Please check system for issues");
                        alert.showAndWait();
                    } else {
                     appointments.delete(appointment);
                   }  
                } catch (SQLException e) {
                  e.printStackTrace();
                }
            }
        }
    }
    /**
     * This is to log out the user
     * @param event which is a button press
     * @throws IOException from when the stage is switched and an error occurs
     */
    @FXML
    public void logOut(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/Login.fxml"));
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * This is to Modify Appointments 
     * @param event with a button press
     * @throws IOException from a screen transfer
     */
    @FXML
    public void modifyAppt(ActionEvent event) throws IOException {
        if (apptTable.getSelectionModel().getSelectedItem() != null || !apptTable.getSelectionModel().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/schedular/EditAppointment.fxml"));
            Parent root = loader.load();
            EditAppointmentController mod = loader.getController();
            Appointments appointment = apptTable.getSelectionModel().getSelectedItem();
            mod.modifyAppointment(appointment);
            Stage stage = (Stage) modApptButton.getScene().getWindow();
            stage.setTitle("Modify Appointment");
            stage.setScene(new Scene(root));
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Appointment is Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an Appointment from the List to modify");
            alert.showAndWait();   
        }
    }
    /**
     * This is to get all the Appointments 
     * @param event is a Radio press event
     * @throws SQLException if an error occurs
     */
    @FXML
    public void radioAll(ActionEvent event) throws SQLException {
            aptSchedule = appointments.getAll();
            apptTable.setItems(aptSchedule);
    }
    /**
     * This is to redirect User to Report Page
     * @param event reporting button press
     * @throws IOException on screen transfer
     */
    @FXML
    public void reportGen(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/schedular/ReportsPage.fxml"));
         Stage stage = (Stage) reportButton.getScene().getWindow();
         stage.setTitle("Reports Page");
         stage.setScene(new Scene(root));
         stage.show();
    }
    /**
     * This is to redirect User to Customer Page
     * @param event Button press action
     * @throws IOException if an error occurs with screen change
     */
    @FXML
    public void toCustomerPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/CustomerPage.fxml"));
        Stage stage = (Stage) customerButton.getScene().getWindow();
        stage.setTitle("Customer Info");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * This is view the Schedule Monthly from the current Date
     * @param event is a radio button press
     * @throws SQLException if an error occurs
     */
    @FXML
    public void viewMonthly(ActionEvent event) throws SQLException {
        apptTable.setItems(appointments.getApptsMonthly());
    }
    /**
     * This is to view the Schedule Weekly from the current Date
     * @param event is a radio button press
     * @throws SQLException if an error occurs
     */
    @FXML
    public void viewWeekly(ActionEvent event) throws SQLException {
        aptSchedule = appointments.getApptsWeekly();
        apptTable.setItems(aptSchedule);
    }
    /**
     * This is to go to the Contacts Page
     * @param event This is the action from the Contacts Button press
     * @throws IOException if an error occurs with Screen transfer
     */
    @FXML
    public void toContactsPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/Contact.fxml"));
        Stage stage = (Stage) contactsButton.getScene().getWindow();
        stage.setTitle("Contacts");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * This is to validate if the Appointments are falling within 15minutes of the current time
     */
    public void AppointmentCheck() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String apptDescription="";
        String customerName="";
        String appointmentTime="";
        String appointmentDate="";
        long apptTimeDifference = -1;
        for (Appointments appointment : aptSchedule) {
            LocalDateTime ldt = LocalDateTime.parse(appointment.getStart(), formatter);
            LocalDateTime currentTime = LocalDateTime.now();
            long timedifference = ChronoUnit.MINUTES.between(ldt, currentTime) * -1;
            CustomerDOA customerDOA = new CustomerDOA();
            Customer customer;
            try {
                customer = customerDOA.get(appointment.getCustomer_id());
                if (timedifference <= 15 || timedifference == 0) {
                    apptTimeDifference = timedifference;
                    apptDescription = appointment.getDescription();
                    customerName = customer.getCustomerName();
                    appointmentDate = ldt.toLocalDate().toString();
                    appointmentTime = ldt.toLocalTime().toString();
                } else {
                    apptTimeDifference = -1;
                }
            } catch (SQLException e) {
                System.out.println("SQL error");
                e.printStackTrace();
            }
        }
        if (apptTimeDifference != -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText(
                    "Your Appointment is in 15 minutes for " + apptDescription + " with " + customerName + "On "
                            + appointmentDate + " at " + appointmentTime);
            alert.showAndWait();
        }
        else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("No Appointment within coming soon");
                alert.showAndWait();
        }
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
     * user information that has logged in. TO be used for other purposes of getting information for other 
     * @param user information 
     */
    public void userHasLoggedIn(String user) {
        if (user != null) {
            //Setting the name of the user logged in
            LoginLog.setUserLoggedIn(user);
            // Running the appointment check
            AppointmentCheck();
        } else {
            return;
        }
    }
    /**
     * Main initializing page function
     * @param arg0 is the URL
     * @param arg1 is the resourceBundle
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initializingTable();
    }
  
}
