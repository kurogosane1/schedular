module schedular {
    requires javafx.controls;
    requires javafx.fxml;

    opens schedular.Controllers to javafx.fxml;
    opens schedular to javafx.fxml;

    exports schedular;
    exports schedular.Controllers;
}
