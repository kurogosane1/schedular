package schedular.Controllers;
/**
 * @author Syed Khurshid
 */
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import schedular.DOA.AppointmentDOA;
import schedular.DOA.CustomerDOA;
import schedular.DOA.UsersDOA;
import schedular.DOA.contactsDOA;
import schedular.Model.Appointments;
import schedular.Model.Contacts;
import schedular.Model.Customer;
import schedular.Model.User;

import static schedular.timeUtil.convertTimeDateUTC;

/**
 * This is the Appointments Adding Controllers
 */
public class AddAppointmentController implements Initializable{
    /**
     * Date Start Date DatePicker
     */
    @FXML
    private DatePicker StartDatePicker;
    /**
     * Appointment ID Error Message Text
     */
    @FXML
    private Text apptIDET;
    /**
     * appointment ID Label
     */
    @FXML
    private Label apptIDLabel;
    /**
     * Appointment ID Text Field
     */
    @FXML
    private TextField apptIDTF;
    /**
     * Cancel Button for cancelling for adding any appointment
     */
    @FXML
    private Button cancelButton;
    /**
     * Contact ID Error Text Field
     */
    @FXML
    private Text contIDET;
    /**
     * Customer ID Error Text
     */
    @FXML
    private Text cusIDET;
    /**
     * Customer ID Label
     */
    @FXML
    private Label customerIDLabel;
    /**
     * Date Error Text
     */
    @FXML
    private Text dateET;
    /**
     * Text Error for Description 
     */
    @FXML
    private Text descET;
    /**
     * Description label field
     */
    @FXML
    private Label descLabel;
    /**
     * This is the Description Text Field
     */
    @FXML
    private TextField descTF;
    /**
     * This is the end Date Label
     */
    @FXML
    private Label endDateLabel;
    /**
     * This is the end Date Picker
     */
    @FXML
    private DatePicker endDatePicker;
    /**
     * This is the end Hour Spinner
     */
    @FXML
    private Spinner<Integer> endHourSpinner;
    /**
     * This is the minutes to get the end of time of appointment in Minutes
     */
    @FXML
    private Spinner<Integer> endMinSpinner;
    /**
     * This is the end of Time Label
     */
    @FXML
    private Label endTimeLabel;
    /**
     * This is the location error Text
     */
    @FXML
    private Text locET;
    /**
     * This is the Location Label
     */
    @FXML
    private Label locationLabel;
    /**
     * This is the Location Text Field
     */
    @FXML
    private TextField locationTF;
    /**
     * This is the save button 
     */
    @FXML
    private Button saveButton;  
    /**
     * This is the Start Date Label
     */
    @FXML
    private Label startDLabel;
    /**
     * This is the Start Hour Spinner to get the Starting Meeting Spinner
     */
    @FXML
    private Spinner<Integer> startHourSpinner;
    /**
     * This is the Start Minute Spinner
     */
    @FXML
    private Spinner<Integer> startMinSpinner;
    /**
     * This is the Start Time Label
     */
    @FXML
    private Label startTimeLabel;
    /**
     * This is the Time Error Text
     */
    @FXML
    private Text timeET;
    /**
     * This is the Appointment Title Error Text
     */
    @FXML
    private Text titleET;
    /**
     * This is the Title Label Field
     */
    @FXML
    private Label titleLabel;
    /**
     * This is the Title Text Field
     */
    @FXML
    private TextField titleTF;
    /**
     * This is the Choicebox for User ID, contact ID and Customer ID
     */
     @FXML ChoiceBox<Integer> contactIDChoice, custIDChoice,userIDChoice;
    /**
     * This is the Type error Text 
     */
    @FXML
    private Text typeET;
    /**
     * This is the Type Label 
     */
    @FXML
    private Label typeLabel;
    /**
     * This is the Type Text Field
     */
    @FXML
    private TextField typeTF;
    /**
     * This is the User ID Error Text
     */
    @FXML
    private Text userIDET;
    /**
     * This is the User ID Label
     */
    @FXML
    private Label userIDLabel;
    /**
     * This is the Appointments DOA
     */
    private AppointmentDOA appointmentDOA = new AppointmentDOA();
    /**
     * This is the Customer DOA
     */
    private CustomerDOA customerDOA = new CustomerDOA();
    /**
     * This is the User DOA
     */
    private UsersDOA userDOA = new UsersDOA();
    /**
     * This is to cancel Button Function to return user
     */
    @FXML
    void cancelPressAct(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/MainPage.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void descTFCheck(ActionEvent event) {
        String desc = descTF.getText();
        if (desc == null) {
             descET.setText("Please input a description of the Appointments");
        } else {
            return;
        }
    }
    /**
     * This is to save the Appointment information to the database
     * @param event button when the save button is clicked
     * @throws SQLException 
     * @throws IOException
     */
    @FXML
    void saveButtonPress(ActionEvent event) throws SQLException, IOException {
        AppointmentDOA appointmentDOA = new AppointmentDOA();
        // THis is automatically added later
        int id = appointmentDOA.getAll().size();
        String title = titleTF.getText();
        String desc = descTF.getText();
        String location = locationTF.getText();
        String type = typeTF.getText();
        String startDate = StartDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String startTime = String.valueOf(startHourSpinner.getValue() + ":" + startMinSpinner.getValue());
        String endDate = String.valueOf(endDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        String endTime = String.valueOf(endHourSpinner.getValue()) + ":" + String.valueOf(endMinSpinner.getValue());
        // This is then convert Time
        String startUTC = convertTimeDateUTC(startDate + " " + startTime + ":00");
        System.out.println(startUTC);
        String endUTC = convertTimeDateUTC(endDate+" "+endTime+":00");
        int customer_id = custIDChoice.getValue();
        int user_id = userIDChoice.getValue();
        int contact_id = contactIDChoice.getValue();
        Appointments appointment = new Appointments(id, title, desc, location, type, startUTC, endUTC, customer_id,
                user_id, contact_id);
        appointmentDOA.insert(appointment);
        goBackAfterSave();
    }
    /**
     * This is to redirect user to the main page if the save goes through
     * @throws IOException
     */
    public void goBackAfterSave() throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/schedular/MainPage.fxml"));
         Stage stage = (Stage) saveButton.getScene().getWindow();
         stage.setTitle("Main Appointment");
         stage.setScene(new Scene(root));
         stage.show();
    }
    /**
     * This is to get Customer User ID choicebox filled
     */
    public void customerIDChoiceBox() {
        ObservableList<Customer> customers;
        ArrayList<Integer> customerID = new ArrayList<Integer>();
        try {
            customers = customerDOA.getAll();
            for (Customer customer : customers) {
                customerID.add(customer.getCustomerId());
            }
            custIDChoice.getItems().addAll(customerID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * This is to get the User ID choice box to be filled
     */
    public void userIDChoiceBox() {
        ObservableList<User> users;
        ArrayList<Integer> userIds = new ArrayList<Integer>();
        try {
            users = userDOA.getAllUsers();
            for (User user : users) {
                userIds.add(user.getUserId());
            }
            userIDChoice.getItems().addAll(userIds);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This is to get the Contact ID choice box to be filled
     */
    public void contactIDChoiceBox() {
        ObservableList<Contacts> contacts;
        ArrayList<Integer> contactID = new ArrayList<Integer>();
        try {
            contacts = contactsDOA.getAllContacts();
            if (contacts != null) {
                for (Contacts contact : contacts) {
                    contactID.add(contact.getContact_ID());
                }
                contactIDChoice.getItems().addAll(contactID);
            } else {
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void spinnerHourChoice() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        SpinnerValueFactory<Integer> endvalueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        startHourSpinner.setValueFactory(valueFactory);
        endHourSpinner.setValueFactory(endvalueFactory);
    }

    public void spinnerMinuteChoice() {
        SpinnerValueFactory<Integer> startMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59);
        SpinnerValueFactory<Integer> endMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        startMinSpinner.setValueFactory(startMinuteFactory);
        endMinSpinner.setValueFactory(endMinuteFactory);
    }
    /**
     * This is to initialize the page
     * @param arg0 which is from the URL
     * @param arg1 Which is the resourceBundle
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("This is the Add Appointments Controllers Page");
        descET.setText("");
        customerIDChoiceBox();
        userIDChoiceBox();
        contactIDChoiceBox();
        spinnerHourChoice();
        spinnerMinuteChoice();
    }
}
