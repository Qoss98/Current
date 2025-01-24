module com.eva.current {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.eva.current to javafx.fxml;
    exports com.eva.current;
}