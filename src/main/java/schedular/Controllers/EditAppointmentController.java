package schedular.Controllers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class EditAppointmentController implements Initializable {
    
    @FXML
    private DatePicker StartDatePicker;

    @FXML
    private Text apptIDET;

    @FXML
    private Label apptIDLabel;

    @FXML
    private TextField apptIDTF;

    @FXML
    private Button cancelButton;

    @FXML
    private Text contIDET;

    @FXML
    private Text cusIDET;

    @FXML
    private Label customerIDLabel;

    @FXML
    private Text dateET;

    @FXML
    private Text descET;

    @FXML
    private Label descLabel;

    @FXML
    private TextField descTF;

    @FXML
    private Label endDateLabel;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Spinner<?> endHourSpinner;

    @FXML
    private Spinner<?> endMinSpinner;

    @FXML
    private Label endTimeLabel;

    @FXML
    private Text locET;

    @FXML
    private Label locationLabel;

    @FXML
    private TextField locationTF;

    @FXML
    private Button saveButton;

    @FXML
    private Label startDLabel;

    @FXML
    private Spinner<?> startHourSpinner;

    @FXML
    private Spinner<?> startMinSpinner;

    @FXML
    private Label startTimeLabel;

    @FXML
    private Text timeET;

    @FXML
    private Text titleET;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField titleTF;

    @FXML
    private Text typeET;

    @FXML
    private Label typeLabel;

    @FXML
    private TextField typeTF;

    @FXML
    private Text userIDET;

    @FXML
    private Label userIDLabel;

    @FXML
    void apptIDCheck(ActionEvent event) {

    }

    @FXML
    void cancellPressAct(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/schedular/MainPage.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void descTFCheck(ActionEvent event) {

    }

    @FXML
    void endDateCheck(ActionEvent event) {

    }

    @FXML
    void locationTFCheck(ActionEvent event) {

    }

    @FXML
    void saveButtonPress(ActionEvent event) {

    }

    @FXML
    void startDateCheck(ActionEvent event) {

    }

    @FXML
    void titleTFCheck(ActionEvent event) {

    }

    @FXML
    void typeTFCheck(ActionEvent event) {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }
}
