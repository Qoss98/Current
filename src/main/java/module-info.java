module com.eva.current {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.eva.current to javafx.fxml;
    exports com.eva.current;
}