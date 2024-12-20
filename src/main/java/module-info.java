module com.eva.current {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.eva.current to javafx.fxml;
    exports com.eva.current;
}