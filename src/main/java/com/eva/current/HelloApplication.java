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

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        GridPane root = new GridPane();
        Scene scene = new Scene(root, 1500, 800);


        stage.setTitle("Current Energy");
        stage.setScene(scene);


        Label klantnr = new Label("Klantnummer: ");
        TextField txtKlantnr = new TextField();
        Label vNaam = new Label("Voornaam: ");
        TextField txtVNaam = new TextField();
        Label aNaam = new Label("Achternaam: ");
        TextField txtANaam = new TextField();


        Label jVoorschot = new Label("Jaarlijks Voorschot: ");
        TextField txtVoorschot = new TextField();
        Label hgas = new Label("Huidige Gasprijs: ");
        TextField txtGas = new TextField();
        Label dataVanafGasL = new Label("Datum vanaf: ");
        DatePicker dataVanafGas = new DatePicker();
        Label dataTotGasL = new Label("Datum tot: ");
        DatePicker dataTotGas = new DatePicker();

        Label hstroom = new Label("Huidige Stroomprijs in kwh: ");
        TextField txtStroom = new TextField();
        Label dataVanafStroomL = new Label("Datum vanaf: ");
        DatePicker dataVanafStroom = new DatePicker();
        Label dataTotStroomL = new Label("Datum tot: ");
        DatePicker dataTotStroom = new DatePicker();

        Button verzend = new Button("Verzend");

        Label output = new Label();

        GridPane.setConstraints(klantnr, 0, 0);
        GridPane.setConstraints(txtKlantnr, 1, 0);
        GridPane.setConstraints(vNaam, 0, 1);
        GridPane.setConstraints(txtVNaam, 1, 1);
        GridPane.setConstraints(aNaam, 0, 2);
        GridPane.setConstraints(txtANaam, 1, 2);
        GridPane.setConstraints(jVoorschot, 0, 3);
        GridPane.setConstraints(txtVoorschot, 1, 3);

        GridPane.setConstraints(hgas, 0, 4);
        GridPane.setConstraints(txtGas, 1, 4);
        GridPane.setConstraints(dataVanafGasL, 2, 4);
        GridPane.setConstraints(dataVanafGas, 3, 4);
        GridPane.setConstraints(dataTotGasL, 4, 4);
        GridPane.setConstraints(dataTotGas, 5, 4);

        GridPane.setConstraints(hstroom, 0, 5);
        GridPane.setConstraints(txtStroom, 1, 5);
        GridPane.setConstraints(dataVanafStroomL, 2, 5);
        GridPane.setConstraints(dataVanafStroom, 3, 5);
        GridPane.setConstraints(dataTotStroomL, 4, 5);
        GridPane.setConstraints(dataTotStroom, 5, 5);

        GridPane.setConstraints(verzend, 0, 6);
        GridPane.setConstraints(output, 0, 7);


        root.getChildren().addAll(klantnr, txtKlantnr, vNaam, txtVNaam, aNaam, txtANaam, jVoorschot, txtVoorschot, hgas, txtGas, dataVanafGasL, dataVanafGas, dataTotGasL, dataTotGas, hstroom, txtStroom, dataVanafStroomL, dataVanafStroom, dataTotStroomL, dataTotStroom, verzend, output);

        verzend.setOnAction(e -> {
            EnergieData data = new EnergieData();

            if (txtVoorschot.getText().isEmpty() || txtGas.getText().isEmpty() || txtStroom.getText().isEmpty()) {
                output.setText("Vul alle velden in");
                return;
            }

            try {
                data.setJaarlijksVoorschot(Double.parseDouble(txtVoorschot.getText()));
                data.setHuidigeGasprijs(Double.parseDouble(txtGas.getText()));
                data.setHuidigeStroomprijs(Double.parseDouble(txtStroom.getText()));

                data.printData(output);
            } catch (NumberFormatException ex) {
                output.setText("Vul geldige getallen in");
            }


        });
        stage.show();
    }


}