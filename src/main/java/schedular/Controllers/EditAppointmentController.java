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
    /**
     * This is to save appointment information after they fill information
     * @param event which is the Button press action
     * @throws SQLException 
     */
    @FXML
    void saveButtonPress(ActionEvent event) throws SQLException {
        AppointmentDOA appointmentDOA = new AppointmentDOA();
        // Validation of the data first
        int appointmentID = Integer.parseInt(apptIDTF.getText()); // This is default and will not change
        try {
            String title = titleTF.getText();
          try {
              String description = descTF.getText();
              try {
                  String location = locationTF.getText();
                    try {
                        String type = typeTF.getText();
                        try {
                            String startDate = StartDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            String startTime = String.valueOf(startHourSpinner.getValue() + ":" + startMinSpinner.getValue()+":00");
                            String endDate = String.valueOf(endDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            String endTime = String.valueOf(endHourSpinner.getValue()) + ":" + String.valueOf(endMinSpinner.getValue()+":00");
                            // This is then convert Time
                            String startUTC = convertTimeDateUTC(startDate + " " + startTime);
                            String endUTC = convertTimeDateUTC(endDate + " " + endTime);
                            // Now comparison of start and end date comparison
                            LocalDate startDateComparison = LocalDate.parse(startDate); // This is temporary Comparison
                            LocalDate endDateComparison = LocalDate.parse(endDate);// This is temporary Comparison
                            if (startDateComparison.isAfter(endDateComparison)) {
                                displayError(5);
                                return;
                            }
                            // TODO Get the Date time parse error resolved. 
                            LocalDateTime startTimeComparison = LocalDateTime.parse(startUTC); // This
                            LocalDateTime endTimeComparison = LocalDateTime.parse(endUTC); // This
                            if (startTimeComparison.isAfter(endTimeComparison)) {
                                displayError(6);
                                return;
                            }
                            try {
                                int customerID = custIDChoice.getValue();
                                int userID = userIDChoice.getValue();
                                int contactID = contactIDChoice.getValue();

                                Appointments appointments = new Appointments(appointmentID, title, description,
                                        location, type, startUTC, endUTC, customerID, userID, contactID);
                                
                                appointmentDOA.update(appointments);

                            } catch (NullPointerException e) {
                                e.printStackTrace(); // This is for Customer ID, User ID and Contact ID
                                displayError(8);
                                return;
                            } 
                        } catch (NullPointerException e) {
                            e.printStackTrace(); // This is for the Start Date and Timestamp
                            displayError(7);
                            return;
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace(); // This is for type
                        displayError(4);
                        return;
                    }
              } catch (NullPointerException e) {
                  e.printStackTrace();// This is for Location
                  displayError(3);
                  return;
              }
          } catch (NullPointerException e) {
              e.printStackTrace(); // This is for Description
              displayError(2);
              return;
          }  
        } catch (NullPointerException e) {
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
     * This is to get Customer User ID choicebox filled at start
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
     * This is to get the User ID choice box to be filled at start
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
            default:
                alert.setTitle("Invalid information input");
                alert.setContentText("Please check the data input and make sure the fields are not empty");
                alert.showAndWait();
                break;
        }
        
    }
    /**
     * This function is used to carry the Appointment object to the modify page for editing
     * @param t Appointment object
     */
    public void modifyAppointment(Appointments t){
        apptIDTF.setText(String.valueOf(t.getAppointmentID()));
        titleTF.setText(t.getTitle());
        descTF.setText(t.getDescription());
        locationTF.setText(t.getLocation());
        typeTF.setText(t.getType());
        // Getting the Local Date and Time
        String str = t.getStart(); // Start Date and Time
        String str2 = t.getEnd(); // End Date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(str, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(str2, formatter);
        spinnerHourChoice(startDateTime.getHour(), endDateTime.getHour());
        spinnerMinuteChoice(startDateTime.getMinute(), endDateTime.getMinute());
        // Setting the Date
        StartDatePicker.setValue(startDateTime.toLocalDate());
        endDatePicker.setValue(endDateTime.toLocalDate());
        // Customer ID, User ID and Contact ID Choice
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
    }
}
