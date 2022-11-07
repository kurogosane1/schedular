package schedular.Controllers;
/**
 * @author Syed Khurshid
 */
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import schedular.DOA.AppointmentDOA;
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
    private Spinner<String> endHourSpinner;
    /**
     * This is the minutes to get the end of time of appointment in Minutes
     */
    @FXML
    private Spinner<String> endMinSpinner;
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
    private Spinner<String> startHourSpinner;
    /**
     * This is the Start Minute Spinner
     */
    @FXML
    private Spinner<String> startMinSpinner;
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
    AppointmentDOA appointmentDOA = new AppointmentDOA();
    /**
     * This is the Appointment ID input check
     * @param event on Action Button Press Event
     */
    @FXML
    void apptIDCheck(ActionEvent event) {

    }
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

    @FXML
    void endDateCheck(ActionEvent event) {

    }

    @FXML
    void locationTFCheck(ActionEvent event) {

    }

    @FXML
    void saveButtonPress(ActionEvent event) throws SQLException {
        // THis is automatically added later
        int id = appointmentDOA.getAll().size();
        String title = titleTF.getText();
        String desc = descTF.getText();
        String location = locationTF.getText();
        String type = typeTF.getText();
        String startDate = StartDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String startTime = startHourSpinner.getValue() + ":" + startMinSpinner.getValue();
        String endDate = endDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endTime = endHourSpinner.getValue() + ":" + endMinSpinner.getValue();
        // This is then convert Time
        Date startUTC = Date.valueOf(convertTimeDateUTC(startDate+" "+startTime+":00"));
        Date endUTC = Date.valueOf(convertTimeDateUTC(endDate+" "+endTime+":00"));
        int customer_id = contactIDChoice.getValue();
        int user_id = userIDChoice.getValue();
        int contact_id = contactIDChoice.getValue();
    }

    @FXML
    void startDateCheck(ActionEvent event) {

    }

    @FXML
    void titleTFCheck(ActionEvent event) {

    }
    /**
     * This is the Type Text Field Check. This is not being used
     * @param event
     */
    @FXML
    void typeTFCheck(ActionEvent event) {}
    /**
     * This is
     * @param alertNumber
     */
    public void errorTextAction(int alertNumber) {}
    /**
     * This is to initialize the page
     * @param arg0 which is from the URL
     * @param arg1 Which is the resourceBundle
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("This is the Add Appointments Controllers Page");
        descET.setText("");
    }
}
