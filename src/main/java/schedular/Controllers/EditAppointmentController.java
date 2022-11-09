package schedular.Controllers;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import schedular.DOA.CustomerDOA;
import schedular.DOA.UsersDOA;
import schedular.DOA.contactsDOA;
import schedular.Model.Appointments;
import schedular.Model.Contacts;
import schedular.Model.Customer;
import schedular.Model.User;


public class EditAppointmentController implements Initializable {
    /**
     * Date picker for the Start of the Appointment and end of the Appointment
     */
    @FXML
    private DatePicker StartDatePicker, endDatePicker;
    /**
     * Buttons for saving the Appointments and Cancelling of Appointments
     */
    @FXML
    private Button cancelButton, saveButton;
    /**
     * Error Texts for different text fields
     */
    @FXML
    private Text apptIDET, contIDET, cusIDET, dateET, descET, locET, timeET, titleET, typeET, userIDET;
    /**
     * Labels for the fields of information for the Appointments
     */
    @FXML
    private Label apptIDLabel, customerIDLabel, descLabel, endDateLabel, endTimeLabel, locationLabel, startDLabel,startTimeLabel, titleLabel, typeLabel, userIDLabel;
    /**
     * Text fields for the Appointments information 
     */
    @FXML
    private TextField apptIDTF, descTF, locationTF, typeTF, titleTF;
    /**
     * This is the Spinners appointments for the information
     */
    @FXML
    private Spinner<Integer> endHourSpinner, endMinSpinner, startHourSpinner, startMinSpinner;
    /**
     * This is the Choicebox for User ID, contact ID and Customer ID
     */
     @FXML ChoiceBox<Integer> contactIDChoice, custIDChoice,userIDChoice;
     /**
     * This is the Customer DOA
     */
    private CustomerDOA customerDOA = new CustomerDOA();
    /**
     * This is the User DOA
     */
    private UsersDOA userDOA = new UsersDOA();
    /**
     * This is the Cancel Button for exiting from correcting an appointments
     * @param event which is the Button press action
     * @throws IOException
     */
    @FXML
    void cancellPressAct(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/MainPage.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    @FXML
    void saveButtonPress(ActionEvent event) {
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
    /**
     * This is to add the choices in a spinner
     */
    public void spinnerHourChoice(int t,int g) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        valueFactory.setValue(t);
        SpinnerValueFactory<Integer> endvalueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        endvalueFactory.setValue(g);
        startHourSpinner.setValueFactory(valueFactory);
        endHourSpinner.setValueFactory(endvalueFactory);
    }
    
    /**
     * This is to add the choices in a spinner
     */
    public void spinnerMinuteChoice(int t, int g) {
        SpinnerValueFactory<Integer> startMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59);
        startMinuteFactory.setValue(t);
        SpinnerValueFactory<Integer> endMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        endMinuteFactory.setValue(g);
        startMinSpinner.setValueFactory(startMinuteFactory);
        endMinSpinner.setValueFactory(endMinuteFactory);
    }

    public void modifyAppointment(Appointments t){
        apptIDTF.setText(String.valueOf(t.getAppointmentID()));
        titleTF.setText(t.getTitle());
        descTF.setText(t.getDescription());
        locationTF.setText(t.getLocation());
        typeTF.setText(t.getType());
        // Get the date format corrected
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime localstart = LocalDateTime.parse(t.getStart(),formatter);
        LocalDateTime ldtstart = LocalDateTime.parse(t.getStart());
        LocalDateTime ldtend = LocalDateTime.parse(t.getEnd());
        spinnerMinuteChoice(ldtstart.getMinute(), ldtend.getMinute());
        spinnerHourChoice(ldtstart.getHour(), ldtend.getHour());
        StartDatePicker.setValue(localstart);
        LocalDate localend = LocalDate.parse(t.getEnd());
        endDatePicker.setValue(localend);
        custIDChoice.setValue(t.getCustomer_id());
        userIDChoice.setValue(t.getUser_id());
        contactIDChoice.setValue(t.getContact_id());

    }   
    /**
     * This is to initialize the page
     * @param arg0 which is from the URL
     * @param arg1 Which is the resourceBundle
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        customerIDChoiceBox();
        userIDChoiceBox();
        contactIDChoiceBox();
        // spinnerHourChoice();
        // spinnerMinuteChoice();
    }
}
