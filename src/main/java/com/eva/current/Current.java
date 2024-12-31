package com.eva.current;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
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