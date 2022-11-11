package schedular.Controllers;
/**
 * @author Syed Khurshid
 */
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
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
import javafx.scene.control.Alert;
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
         // Getting the Appointment ID which will be automatically added
        String minuteSelectionStart;
        String minuteSelectionEnd;
        String hourSelectionStart;
        String hourSelectionEnd;
        String startResultHour;
        String endResultHour;
        String startResultMinute;
        String endResultMinute;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");    

        int appointmentID = appointmentDOA.getAll().size();
        try {
            String title = titleTF.getText();
             try {
                 String description = descTF.getText();
                try {
                    String location = locationTF.getText();
                    try {
                        String type = typeTF.getText();
                        try {
                            minuteSelectionStart = String.valueOf(startMinSpinner.getValue());
                            minuteSelectionEnd = String.valueOf(endMinSpinner.getValue());
                            hourSelectionStart = String.valueOf(startHourSpinner.getValue());
                            hourSelectionEnd = String.valueOf(endHourSpinner.getValue());
                            // Getting the minutes corrected
                            if (minuteSelectionStart.length() == 1) {
                                startResultMinute = "0" + minuteSelectionStart;
                            }
                            else {
                                startResultMinute = minuteSelectionStart;
                            }
                            if (minuteSelectionEnd.length() == 1) {
                                endResultMinute = "0" + minuteSelectionEnd;
                            }
                            else {
                                endResultMinute = minuteSelectionEnd;
                            }
                            if (hourSelectionStart.length() == 1) {
                                startResultHour = "0" + hourSelectionStart;
                            }
                            else {
                                startResultHour = hourSelectionStart;
                            }
                            if (hourSelectionEnd.length() == 1) {
                                endResultHour = "0" + hourSelectionEnd;
                            }
                            else {
                                endResultHour = hourSelectionEnd;
                            }

                            String strTime = startResultHour + ":" + startResultMinute + ":00";
                            String strTimeend = endResultHour + ":" + endResultMinute + ":00";
                            String strDate = String.valueOf(StartDatePicker.getValue());
                            String strDateEnd = String.valueOf(endDatePicker.getValue());
                            String startUTC = convertTimeDateUTC(strDate + " " + strTime);
                            String endUTC = convertTimeDateUTC(strDateEnd + " " + strTimeend);
                            LocalDate startDateComparison = LocalDate.parse(startUTC, formatter);
                            LocalDate endDateComparison = LocalDate.parse(endUTC, formatter);
                            
                            if (startDateComparison.isAfter(endDateComparison)) {
                                displayError(5);
                                return;
                            }
                            if (startDateComparison.isBefore(LocalDate.now())) {
                                displayError(9);
                                return;
                            }
                            if (endDateComparison.isBefore(LocalDate.now())
                                    || endDateComparison.isBefore(startDateComparison)) {
                                displayError(10);
                                return;
                            }
                            
                            LocalTime startTimeComparison = LocalTime.parse(strTime, timeFormatter);
                            LocalTime endTimeComparison = LocalTime.parse(strTimeend, timeFormatter);
                            if (startTimeComparison.isAfter(endTimeComparison)
                                    || endTimeComparison.isBefore(startTimeComparison)) {
                                displayError(11);
                                return;
                            }
                            

                            try {
                                 int customerID = custIDChoice.getValue();
                                int userID = userIDChoice.getValue();
                                int contactID = contactIDChoice.getValue();

                                Appointments appointment = new Appointments(appointmentID, title, description, location,
                                        type, startUTC, endUTC, customerID, userID, contactID);
                                appointmentDOA.insert(appointment);
                                goBackAfterSave();
                            } catch (Exception e) {
                                e.printStackTrace(); // This is for Customer ID, User ID and Contact ID
                                displayError(8);
                                return;
                            }
                            // String startTime = convertTimeDateUTC()
                        } catch (Exception e) {
                            e.printStackTrace(); // This is for Date
                            displayError(7);
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace(); // This is for type
                        displayError(4);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // This is for Location
                    displayError(3);
                    return;
                }
             } catch (Exception e) {
                 e.printStackTrace();// This is for Description
                 displayError(2);
                 return;
             }
        } catch (Exception e) {
            e.printStackTrace(); // This is for Title
            displayError(1);
            return;
        }    
        
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
        startMinuteFactory.setValue(00);
        SpinnerValueFactory<Integer> endMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59);
        endMinuteFactory.setValue(00);
        startMinSpinner.setValueFactory(startMinuteFactory);
        endMinSpinner.setValueFactory(endMinuteFactory);
    }
    /**
     * This is the Display Alert to show when a mistake is made
     * @param alertNumber is the integer that is passed to run the alert
     */ 
    public void displayError(int alertNumber) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        switch (alertNumber) {
            case 1: // This is when the 
                 alert.setTitle("Empty Title");
                 alert.setContentText("You have not entered a Title for The Appointment");
                 alert.showAndWait();
                 break;
             case 2: // This is when the Description is empty
                 alert.setTitle("Empty Description");
                 alert.setContentText("You have not entered a Description");
                 alert.showAndWait();
                 break;
             case 3: // This is when the the Location is empty
                 alert.setTitle("Empty Location");
                 alert.setContentText("You have not entered a Location");
                 alert.showAndWait();
                 break;
             case 4: // This is when the the Type is empty
                 alert.setTitle("Empty Type");
                 alert.setContentText("You have not entered a Type");
                 alert.showAndWait();
                 break;
             case 5: //This is when the Start Date is after the End Date
                 alert.setTitle("Start Date invalid");
                 alert.setContentText("Start Date should not be after the end date");
                 alert.showAndWait();
                 break;
            case 6: // This is when the Start Time is after the End Time
                alert.setTitle("Start time should not be after the end date");
                alert.setContentText("Start time should not be after the end date");
                alert.showAndWait();
                break;
            case 7: // This is when the date or time are empty
                alert.setTitle("Date or time should not be empty");
                alert.setContentText("Date or time should not be empty, please be sure to select the date and time");
                alert.showAndWait();
                break;
            case 8: // This is if Customer ID, Contact ID or User ID is empty
                alert.setTitle("Empty User ID, Contact ID, Customer ID");
                alert.setContentText(
                        "Please do not leave the Contact ID, or User ID or Customer ID or all of them empty");
                alert.showAndWait();
                break;
            case 9: // This is if the start date is before the current date
                alert.setTitle("Start Date cannot be before the current date");
                alert.setContentText(
                       "Start Date Cannot be before the current date and should be in the current date or after current Date");
                alert.showAndWait();
            case 10: // This is if the end date is before the current date or start date
                alert.setTitle("Incorrect End Date");
                alert.setContentText(
                       "End Date cannot be before start Date or the current date. \n Please enter the end date either the same as the start date or after start date.");
                alert.showAndWait();
            case 11: // This is is the start time is after end time or end time is before start time
                alert.setTitle("Incorrect Time");
                alert.setContentText(
                        "Please correctly select the time. Start time cannot be equal, or after end time or endtime cannot be before start time");
                alert.showAndWait();
            default:
                alert.setTitle("Invalid information input");
                alert.setContentText("Please check the data input and make sure the fields are not empty");
                alert.showAndWait();
                break;
        }
        
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
