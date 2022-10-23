module schedular {
    requires javafx.controls;
    requires javafx.fxml;

    opens schedular to javafx.fxml;
    exports schedular;
}
