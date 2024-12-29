package com.eva.current;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;

import java.io.IOException;

public class Current extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Constraints constraints = new Constraints();

        EnergyFormPane energyFormPane = new EnergyFormPane(constraints);

        Scene scene = new Scene(energyFormPane, 1500, 800);

        stage.setTitle("Current Energy");
        stage.setScene(scene);
        stage.show();
    }
}