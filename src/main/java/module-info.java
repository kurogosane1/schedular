module schedular {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens schedular.Controllers to javafx.fxml;
    opens schedular to javafx.fxml;
    opens schedular.Model to javafx.fxml;

    exports schedular;
    exports schedular.Controllers;
    exports schedular.Model;
}
