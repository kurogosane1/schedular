/**
 * Refactoring code and moving to a noSQL database
 * Class Object for time zone conversion is necessary because its being used a lot
 */
package schedular.Controllers;
/**
 * @author Syed Khurshid
 */
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
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
import schedular.DOA.ContactsDOA;
import schedular.Model.Appointments;
import schedular.Model.Contacts;
import schedular.Model.Customer;
import schedular.Model.User;
import schedular.utilities.AddObject;
import schedular.utilities.GoBack;
import schedular.utilities.TimeConversion;
/**
 * This is the Appointments Adding Controllers for the purpose of adding appointments as well as validating the times being input
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
    @FXML private Label endTimeLabel;
    /**
     * This is the location error Text
     */
    @FXML private Text locET;
    /**
     * This is the Location Label
     */
    @FXML private Label locationLabel;
    /**
     * This is the Location Text Field
     */
    @FXML private TextField locationTF;
    /**
     * This is the save button 
     */
    @FXML private Button saveButton;  
    /**
     * This is the Start Date Label
     */
    @FXML private Label startDLabel;
    /**
     * This is the Start Hour Spinner to get the Starting Meeting Spinner
     */
    @FXML private Spinner<Integer> startHourSpinner;
    /**
     * This is the Start Minute Spinner
     */
    @FXML private Spinner<Integer> startMinSpinner;
    /**
     * This is the Start Time Label
     */
    @FXML private Label startTimeLabel;
    /**
     * This is the Time Error Text
     */
    @FXML private Text timeET;
    /**
     * This is the Appointment Title Error Text
     */
    @FXML private Text titleET;
    /**
     * This is the Title Label Field
     */
    @FXML private Label titleLabel;
    /**
     * This is the Title Text Field
     */
    @FXML private TextField titleTF;
    /**
     * This is the Choicebox for User ID, contact ID and Customer ID
     */
     @FXML ChoiceBox<Integer> contactIDChoice, custIDChoice,userIDChoice;
    /**
     * This is the Type error Text 
     */
    @FXML private Text typeET;
    /**
     * This is the Type Label 
     */
    @FXML private Label typeLabel;
    /**
     * This is the Type Text Field
     */
    @FXML  private TextField typeTF;
    /**
     * This is the User ID Error Text
     */
    @FXML private Text userIDET;
    /**
     * This is the User ID Label
     */
    @FXML private Label userIDLabel;
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
     * This is a tool to convert time but moved to another class since its being reused over and over again
     */
    private TimeConversion timeTools = new TimeConversion();
    /**
     * Time format layout
     */
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * This is to cancel Button Function to return user
     * @throws IOException if an error occurs 
     */
    @FXML
    void cancelPressAct(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/MainPage.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * This is the Description TF Check. Not Being used
     * @param event This is the Description Check 
     */
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
     * @throws SQLException in case of a SQL error
     * @throws IOException in case of a screen change error
     */
    @FXML
    void saveButtonPress(ActionEvent event) throws SQLException, IOException {
        // Getting the Appointment ID which will be automatically added
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
                            // Getting the Date and Time in string format
                            String startTime = TimeConversion.timeStringConversion(startHourSpinner.getValue(), startMinSpinner.getValue());
                            String endTime = TimeConversion.timeStringConversion(endHourSpinner.getValue(),
                                    endMinSpinner.getValue());
                            String startDateTime = TimeConversion.convertDateTime(StartDatePicker.getValue(),
                                    startTime);
                            String endDateTime = TimeConversion.convertDateTime(endDatePicker.getValue(), endTime);        
                            // Converting to ZonedDateTime from the date and time picked
                            ZonedDateTime zStart = TimeConversion.convertToLocalTimeZone(startDateTime);
                            ZonedDateTime zEnd = TimeConversion.convertToLocalTimeZone(endDateTime);

                            // Checking if the Dates are correct
                            Integer errorCheck = TimeConversion.compareDates(zStart.toLocalDateTime().format(formatter).toString(),
                                    zEnd.toLocalDateTime().format(formatter).toString());
                            if (errorCheck == 1 && errorCheck != 0) {
                                displayError(6);
                                return;
                            }
                            if (errorCheck == 2 && errorCheck != 0) {
                                displayError(9);
                                return;
                            }
                            if (errorCheck == 3 && errorCheck != 0) {
                                displayError(10);
                                return;
                            }
                            // Performing Time Checks 
                            Boolean timeCheck = TimeConversion.compareTimes(startDateTime, endDateTime);
                               if (!timeCheck) {
                                displayError(11);
                                return;
                            }
                            // Checking if the time selected falls under EST business hours
                            Boolean validBusinessHour = timeTools.compareTimeZones(zStart, zEnd, zStart.toLocalDate(),
                                    zEnd.toLocalDate());
                            if (!validBusinessHour) {
                                displayError(12);
                                return;
                            }
                            // Check if the business hours are valid
                            if (!validBusinessHour) {
                                displayError(12);
                                return;
                            }
                            // Checking for Overlaps
                            Boolean noOverlaps = TimeConversion.appointmentOverlapCheck(zStart.toLocalDateTime().format(formatter).toString(),
                                    zEnd.toLocalDateTime().format(formatter).toString());
                            if (!noOverlaps) {
                                displayError(13);
                                return;
                            }
                            try{
                                // Getting Customer ID, User ID and Contact ID
                            int customerID = custIDChoice.getValue();
                            int userID = userIDChoice.getValue();
                            int contactID = contactIDChoice.getValue();
                            // Convert to UTC format
                            String startUTC = TimeConversion.convertTimeToUTC(zStart.toLocalDateTime().format(formatter).toString());
                            String endUTC = TimeConversion.convertTimeToUTC(zEnd.toLocalDateTime().format(formatter).toString());
                            // Creating the Appointment Object
                            pushToDatabase.pushToDatabase(appointmentID, title, description, location,
                                    type, startUTC, endUTC, customerID, userID, contactID);
                            
                            // Switching now going back to main screen once done
                            switchScreens.switchScreens("/schedular/MainPage.fxml");
                            }
                            catch (Exception e) {
                                 e.printStackTrace(); // This is for Customer ID, User ID and Contact ID
                                 displayError(8);
                                 return;
                            }
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
     * Lambda expression to go switch screens.
     * Reason for using Lambda is still not quite the same
     * @param s which is the String
     */
    GoBack switchScreens =(s)->{
        Parent root = FXMLLoader.load(getClass().getResource(s));
         Stage stage = (Stage) saveButton.getScene().getWindow();
         stage.setTitle("Main Appointment");
         stage.setScene(new Scene(root));
         stage.show();
    };
    /**
     * Using lambda expression to push to database. With this I can reduce the number of code lines in a function
     * By placing these outside is helping to reduce the line of codes in a function
     * @param id this is the appointment id
     * @param title this is the name of the appointment
     * @param description this is the description of the appointment
     * @param location this is the location of the appointment
     * @param type this is the type of appointment
     * @param start this is the start date of the appointment
     * @param end this is the end date of the appointment
     * @param cusID this is is the customer id of the appointment
     * @param useID this is the User ID of the appointment
     * @param contactID this is the Contact ID of the appointment
     */
    AddObject pushToDatabase = (Integer id, String title, String description, String location, String type,
            String start, String end, Integer cusID, Integer useID, Integer contactID) -> 
    {   
                AppointmentDOA apptDOA = new AppointmentDOA();
                Appointments appointment = new Appointments(id, title, description, location, type, start, end, cusID, useID, contactID);                               
                try {
                int check = apptDOA.insert(appointment);
                if (check != 0) {
                    displayError(14);
                }
                } catch (SQLException e) {
                e.printStackTrace();
            System.out.println("SQL error inserting into database please check message: " + e.getMessage());
            return;
            }
    };
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
        ContactsDOA contactsDOA = new ContactsDOA();
        ArrayList<Integer> contactID = new ArrayList<Integer>();
        try {
            contacts = contactsDOA.getAll();
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
     * This is for the Hours selection
     */
    public void spinnerHourChoice() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        SpinnerValueFactory<Integer> endvalueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        startHourSpinner.setValueFactory(valueFactory);
        endHourSpinner.setValueFactory(endvalueFactory);
    }
    /**
     * This is for the Minute for the spinner choices
     */
    public void spinnerMinuteChoice() {
        SpinnerValueFactory<Integer> startMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59);
        startMinuteFactory.setValue(00);
        SpinnerValueFactory<Integer> endMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
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
                break;
            case 12: // This is when the date or time is not at a valid business hour
                alert.setTitle("Incorrect Business Hour");
                alert.setContentText("Please select a valid business hour from 8am EST to 10pm EST");
                alert.showAndWait();
                break;
            case 13: //This is when the appointments overlap
                alert.setTitle("Overlapping Appointments");
                alert.setContentText(
                        "Apppointment date or time are overlapping existing appointments. \n Please select another date or time");
                alert.showAndWait();
                break;
            case 14: // This is for successfully adding a new appointment
                Alert sAlert = new Alert(Alert.AlertType.INFORMATION);
                sAlert.setTitle("Successfully created");
                sAlert.setContentText("Appointment has been successfully created");
                sAlert.setHeaderText(null);
                Optional<ButtonType> result = sAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    return;
                }
            default:
                alert.setTitle("Invalid information input");
                alert.setContentText("Please check the data input and make sure the fields are not empty");
                alert.showAndWait();
                break;
        }

    }
    
    public void showContactText(ActionEvent event) {
        contIDET.setVisible(true);
        ContactsDOA contactDOA = new ContactsDOA();
        contIDET.setText("");
        System.out.println(contactIDChoice.getValue() + " Value");
        if (contactIDChoice.getValue() !=null) {
            try{
                Contacts contact = contactDOA.get(contactIDChoice.getValue());
            if (contact.getContactName() == null) {
                contIDET.setText("");
            } else {
                
                contIDET.setText(contact.getContactName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            }
        }
    }
    /**
     * This is to initialize the page
     * @param arg0 which is from the URL
     * @param arg1 Which is the resourceBundle
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        descET.setText("");
        customerIDChoiceBox();
        userIDChoiceBox();
        contactIDChoiceBox();
        spinnerHourChoice();
        spinnerMinuteChoice();
        contactIDChoice.setOnAction(this::showContactText);
    }
}
