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

        Label verbruikStroom = new Label("Verbruik Stroom in kwh: ");
        TextField txtVerbruikStroom = new TextField();
        Label verbruikGas = new Label("Verbruik Gas in m3: ");
        TextField txtVerbruikGas = new TextField();
        Label dataVanafVerbruikL = new Label("Datum vanaf: ");
        DatePicker dataVanafVerbruik = new DatePicker();
        Label dataTotVerbruikL = new Label("Datum tot: ");
        DatePicker dataTotVerbruik = new DatePicker();

        Button verzend = new Button("Verzend");

        Label output = new Label();

        Constraints constraints = new Constraints();
        constraints.InputFields3(klantnr, txtKlantnr, vNaam, txtVNaam, aNaam, txtANaam, jVoorschot, txtVoorschot);
        constraints.InputFields1(hgas, txtGas, dataVanafGasL, dataVanafGas, dataTotGasL, dataTotGas);
        constraints.InputFields4(hstroom, txtStroom, dataVanafStroomL, dataVanafStroom, dataTotStroomL, dataTotStroom);
        constraints.InputFields2(verbruikStroom, txtVerbruikStroom, verbruikGas, txtVerbruikGas, dataVanafVerbruikL, dataVanafVerbruik, dataTotVerbruikL, dataTotVerbruik);

        GridPane.setConstraints(verzend, 0, 10);
        GridPane.setConstraints(output, 0, 11);


        root.getChildren().addAll(klantnr, txtKlantnr, vNaam, txtVNaam, aNaam, txtANaam, jVoorschot, txtVoorschot, hgas, txtGas, dataVanafGasL, dataVanafGas, dataTotGasL, dataTotGas, hstroom, txtStroom, dataVanafStroomL, dataVanafStroom, dataTotStroomL, dataTotStroom, verbruikStroom, txtVerbruikStroom, verbruikGas, txtVerbruikGas, dataVanafVerbruikL, dataVanafVerbruik, dataTotVerbruikL, dataTotVerbruik, verzend, output);

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