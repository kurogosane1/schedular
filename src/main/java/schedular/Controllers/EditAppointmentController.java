/**
 * Code refactoring to refactor code that is being re-used as much as possible
 * Using a no sql database would work better
 */
package schedular.Controllers;
/**
 * @author Syed Khurshid
 */
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import schedular.utilities.TimeConversion;
/**
 * This is to Edit the Appointment Page
 */
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
     * This is the Time Conversion object that is used to convert times and others which process is being reused
     */
    private TimeConversion timeTools = new TimeConversion();
    /**
     * Time format layout
     */
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * This is the Cancel Button for exiting from correcting an appointments
     * @param event which is the Button press action
     * @throws IOException from screen transfer error
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
     * @throws SQLException in case of SQL error
     * @throws IOException in screen transfer
     */
    @FXML
    void saveButtonPress(ActionEvent event) throws SQLException, IOException {
        AppointmentDOA appointmentDOA = new AppointmentDOA();
        int appointmentID = Integer.parseInt(apptIDTF.getText()); // This is default and will not change 
        // Checking if these fields are empty
        if (titleTF.getText().isEmpty() && descTF.getText().isEmpty() && locationTF.getText().isEmpty()
                && typeTF.getText().isEmpty()
                || titleTF.getText().isEmpty() && descTF.getText().isEmpty()
                || descTF.getText().isEmpty() && locationTF.getText().isEmpty()
                || titleTF.getText().isEmpty() && locationTF.getText().isEmpty()
                || typeTF.getText().isEmpty() && descTF.getText().isEmpty()
                || typeTF.getText().isEmpty() && locationTF.getText().isEmpty()
                || typeTF.getText().isEmpty() && titleTF.getText().isEmpty()
                ) {
            displayError(20);
            return;
        } else {
            if (titleTF.getText().isEmpty()) {
                displayError(1);
                return;
            }
            if (descTF.getText().isEmpty()) {
                displayError(2);
                return;
            }
            if (locationTF.getText().isEmpty()) {
                displayError(3);
                return;
            }
            if (typeTF.getText().isEmpty()) {
                displayError(4);
                return;
            }
            String title = titleTF.getText();
            String description = descTF.getText();
            String location = locationTF.getText();
            String type = typeTF.getText();
            // Value check to see if the User Choicebox is selected for Customer ID, User ID and Contact ID
            if (custIDChoice.getValue() == null || userIDChoice.getValue() == null || contactIDChoice.getValue() == null
                    || custIDChoice.getValue() == null && userIDChoice.getValue() == null
                    || custIDChoice.getValue() == null && contactIDChoice.getValue() == null
                    || custIDChoice.getValue() == null && contactIDChoice.getValue() == null
                            && userIDChoice.getValue() == null) {
                displayError(15);
                return;
            }
            // Value check to see if the datepicker is selected and not left blank
            if (StartDatePicker.getValue() ==null && endDatePicker.getValue()==null
                    || StartDatePicker.getValue()==null
                    || endDatePicker.getValue() == null) {
                displayError(7);
                return;
            }
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
                            // Testing new overlap functions
                            Boolean overlapping=TimeConversion.checkOverlap(zStart.toLocalDateTime().format(formatter).toString(),
                                    zEnd.toLocalDateTime().format(formatter).toString(), appointmentID);
                            // Checking for Overlaps
                            // Boolean noOverlaps = TimeConversion.appointmentOverlapCheck(zStart.toLocalDateTime().format(formatter).toString(),
                            //         zEnd.toLocalDateTime().format(formatter).toString());
                            if (overlapping) {
                                displayError(13);
                                return;
                            }
                            try {
                            // Getting Customer ID, User ID and Contact ID
                            int customerID = custIDChoice.getValue();
                            int userID = userIDChoice.getValue();
                            int contactID = contactIDChoice.getValue();
                            // Convert to UTC format
                            String startUTC = TimeConversion.convertTimeToUTC(zStart.toLocalDateTime().format(formatter).toString());
                            String endUTC = TimeConversion.convertTimeToUTC(zEnd.toLocalDateTime().format(formatter).toString());
                            // Creating the Appointment Object
                            Appointments appointment = new Appointments(appointmentID, title, description, location,
                                    type, startUTC, endUTC, customerID, userID, contactID);
                            int result = appointmentDOA.update(appointment);
                            if (result == 0) {
                                System.out.println("Appointments SQL error issue");
                            }
                            goBackAfterSave();
                            } catch (NullPointerException e) {
                                e.printStackTrace(); // This is for Customer ID, User ID and Contact ID
                                displayError(8);
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace(); // This is for the Start Date and Timestamp
                            displayError(7);
                            return;
                        }  
        }
    }
     /**
     * This is to redirect user to the main page if the save goes through
     * @throws IOException if an error occurs
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
        ContactsDOA contactsDOA = new ContactsDOA();
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
     * This is to add the choices in a spinner
     * @param t which is the starting hour selected form the spinner
     * @param g which is the ending hour selected from the spinner
     */
    public void spinnerHourChoice(int t,int g) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 22);
        valueFactory.setValue(t);
        SpinnerValueFactory<Integer> endvalueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 22);
        endvalueFactory.setValue(g);
        startHourSpinner.setValueFactory(valueFactory);
        endHourSpinner.setValueFactory(endvalueFactory);
    }   
    /**
     * This is to add the choices in a spinner
     * @param t integer for minutes
     * @param g integer for end minutes selected
     */
    public void spinnerMinuteChoice(int t, int g) {
        SpinnerValueFactory<Integer> startMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59);
        startMinuteFactory.setValue(t);
        SpinnerValueFactory<Integer> endMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59);
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
            case 15: // This is incase the Contact ID or Customer ID or User ID or all are empty
                alert.setTitle("Missing selection of ID's");
                alert.setContentText(
                        "Please be sure to select Contact ID, Customer ID and User ID. \n Please no leave any of them unselected");
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
    public void modifyAppointment(Appointments t) {
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
     * This is to show the Contact Name from the choicebox
     * @param event this is from the Contact Choice Box
     */
    public void showContactText(ActionEvent event) {
        contIDET.setVisible(true);
        ContactsDOA contactDOA = new ContactsDOA();
        if (contactIDChoice.getValue() != null) {
            try {
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
     * This is to get the Customer Name when the Customer ID choice is selected
     * @param event is from the ChoiceBox option
     */
     public void showCustomerChoice(ActionEvent event) {
         cusIDET.setVisible(true);
         if (custIDChoice.getValue() != null) {
             try {
                 Customer customer = customerDOA.get(custIDChoice.getValue());
                 if (customer.getCustomerName() == null) {
                     cusIDET.setText("");
                 } else {
                     cusIDET.setText(customer.getCustomerName());
                 }
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
     }
     /**
      * This is to get the User name from the choices selected from the User ID choicebox
      * @param event is from the choicebox selection
      */
     public void showUserChoice(ActionEvent event) {
         userIDET.setVisible(true);
         if (userIDChoice.getValue() != null) {
            try {
                User user = userDOA.getID(userIDChoice.getValue());
                if (user.getUserName() != null) {
                    userIDET.setText(user.getUserName());
                }
                else {
                    userIDET.setText("");
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
        customerIDChoiceBox();
        userIDChoiceBox();
        contactIDChoiceBox();
        contactIDChoice.setOnAction(this::showContactText);
        custIDChoice.setOnAction(this::showCustomerChoice);
        userIDChoice.setOnAction(this::showUserChoice);
    }
}


// if (titleTF.getText().isEmpty() && descTF.getText().isEmpty() && locationTF.getText().isEmpty() && typeTF.getText().isEmpty()) {
//             displayError(20);
//             return;
//         }
//         else {
//             // Validation of the data first
//         int appointmentID = Integer.parseInt(apptIDTF.getText()); // This is default and will not change 
//         try {
//             String title = titleTF.getText();
//             System.out.println("this is line 130: "+title);
//              try {
//                  String description = descTF.getText();
//                  System.out.println("This is line 133: " + description);
//                 try {
//                     String location = locationTF.getText();
//                     System.out.println("this is line 136: "+location);
//                     try {
//                         String type = typeTF.getText();
//                         System.out.println("this is line 139");
//                         System.out.println(type.isEmpty());
//                         try {
//                              // Getting the Date and Time in string format
//                             String startTime = TimeConversion.timeStringConversion(startHourSpinner.getValue(), startMinSpinner.getValue());
//                             String endTime = TimeConversion.timeStringConversion(endHourSpinner.getValue(),
//                                     endMinSpinner.getValue());
//                             String startDateTime = TimeConversion.convertDateTime(StartDatePicker.getValue(),
//                                     startTime);
//                             String endDateTime = TimeConversion.convertDateTime(endDatePicker.getValue(), endTime);        
//                             // Converting to ZonedDateTime from the date and time picked
//                             ZonedDateTime zStart = TimeConversion.convertToLocalTimeZone(startDateTime);
//                             ZonedDateTime zEnd = TimeConversion.convertToLocalTimeZone(endDateTime);

//                             // Checking if the Dates are correct
//                             Integer errorCheck = TimeConversion.compareDates(zStart.toLocalDateTime().format(formatter).toString(),
//                                     zEnd.toLocalDateTime().format(formatter).toString());
//                             if (errorCheck == 1 && errorCheck != 0) {
//                                 displayError(6);
//                                 return;
//                             }
//                             if (errorCheck == 2 && errorCheck != 0) {
//                                 displayError(9);
//                                 return;
//                             }
//                             if (errorCheck == 3 && errorCheck != 0) {
//                                 displayError(10);
//                                 return;
//                             }
//                             // Performing Time Checks 
//                             Boolean timeCheck = TimeConversion.compareTimes(startDateTime, endDateTime);
//                                if (!timeCheck) {
//                                 displayError(11);
//                                 return;
//                             }
//                             // Checking if the time selected falls under EST business hours
//                             Boolean validBusinessHour = timeTools.compareTimeZones(zStart, zEnd, zStart.toLocalDate(),
//                                     zEnd.toLocalDate());
//                             if (!validBusinessHour) {
//                                 displayError(12);
//                                 return;
//                             }
//                             // Check if the business hours are valid
//                             if (!validBusinessHour) {
//                                 displayError(12);
//                                 return;
//                             }
//                             // Testing new overlap functions
//                             Boolean overlapping=TimeConversion.checkOverlap(zStart.toLocalDateTime().format(formatter).toString(),
//                                     zEnd.toLocalDateTime().format(formatter).toString(), appointmentID);
//                             // Checking for Overlaps
//                             // Boolean noOverlaps = TimeConversion.appointmentOverlapCheck(zStart.toLocalDateTime().format(formatter).toString(),
//                             //         zEnd.toLocalDateTime().format(formatter).toString());
//                             if (overlapping) {
//                                 displayError(13);
//                                 return;
//                             }
//                             try {
//                             // Getting Customer ID, User ID and Contact ID
//                             int customerID = custIDChoice.getValue();
//                             int userID = userIDChoice.getValue();
//                             int contactID = contactIDChoice.getValue();
//                             // Convert to UTC format
//                             String startUTC = TimeConversion.convertTimeToUTC(zStart.toLocalDateTime().format(formatter).toString());
//                             String endUTC = TimeConversion.convertTimeToUTC(zEnd.toLocalDateTime().format(formatter).toString());
//                             // Creating the Appointment Object
//                             Appointments appointment = new Appointments(appointmentID, title, description, location,
//                                     type, startUTC, endUTC, customerID, userID, contactID);
//                             int result = appointmentDOA.update(appointment);
//                             if (result == 0) {
//                                 System.out.println("Appointments SQL error issue");
//                             }
//                             goBackAfterSave();
//                             } catch (NullPointerException e) {
//                                 e.printStackTrace(); // This is for Customer ID, User ID and Contact ID
//                                 displayError(8);
//                                 return;
//                             }
//                         } catch (Exception e) {
//                             e.printStackTrace(); // This is for the Start Date and Timestamp
//                             displayError(7);
//                             return;
//                         }  
//                     } catch (Exception e) {
//                         e.printStackTrace(); // This is for type
//                         displayError(4);
//                         return;
//                     }
//                 } catch (Exception e) {
//                     System.out.println("This ran here");
//                    e.printStackTrace();// This is for Location
//                    displayError(3);
//                    return;
//                 }
//              } catch (Exception e) {
//                 e.printStackTrace(); // This is for Description
//                 displayError(2);
//                 return;
//              }
//          } catch (Exception e) {
//             System.out.println("This ran for Title");
//             e.printStackTrace(); // This is for Title
//             displayError(1);
//             return;
//         }
//         }