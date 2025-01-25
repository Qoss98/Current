package com.eva.current;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class EnergyFormPane extends GridPane {
    // Velden definieren
    private final Prijzen prijzen;// Store the Prijzen object
    private int klantnr;

    private final TextField txtVerbruikStroom = new TextField();
    private final TextField txtVerbruikGas = new TextField();


    private final DatePicker dataVanafVerbruik = new DatePicker();
    private final DatePicker dataTotVerbruik = new DatePicker();

    private final Label outputOpgeslagen = new Label();
    private final Label outputPrijs = new Label();
    private final Label outputWeek = new Label();
    private final Label outputMaand = new Label();
    private final Label outputJaar = new Label();

    ArrayList<EnergieData> energieData = new ArrayList<>();

    public EnergyFormPane(Constraints constraints, Prijzen prijzen) {
        this.prijzen = prijzen;
        // GridPane setup
        setVgap(10);
        setHgap(10);

        // labels creeeren
        Label verbruikStroom = new Label("Verbruik Stroom in kWh:");
        Label verbruikGas = new Label("Verbruik Gas in m3:");
        Label dataVanafVerbruikL = new Label("Datum vanaf:");
        Label dataTotVerbruikL = new Label("Datum tot:");

        // Submit button
        Button verzend = new Button("Verzend");
        Button toonGemiddelde = new Button("Toon Gemiddelde");

        // Constraints toevoegen met gebruik van class Constraints
        constraints.InputFields2(verbruikStroom, txtVerbruikStroom, verbruikGas, txtVerbruikGas, dataVanafVerbruikL, dataVanafVerbruik, dataTotVerbruikL, dataTotVerbruik);
        constraints.OutputFields(outputOpgeslagen, outputPrijs, outputWeek, outputMaand, outputJaar);

        // Alles toevoegen aan de GridPane
        getChildren().addAll(
                verbruikStroom, txtVerbruikStroom, verbruikGas,
                txtVerbruikGas, dataVanafVerbruikL, dataVanafVerbruik, dataTotVerbruikL,
                dataTotVerbruik, verzend, toonGemiddelde, outputOpgeslagen, outputPrijs, outputWeek, outputMaand, outputJaar
        );

        // Constraints voor alle Buttons
        GridPane.setConstraints(verzend, 0, 10);
        GridPane.setConstraints(toonGemiddelde, 0, 11);

        // setonAction logica voor de buttons
        verzend.setOnAction(e -> handleSubmit());


        toonGemiddelde.setOnAction(e -> {

            SceneManager sceneManager = new SceneManager((Stage) getScene().getWindow());
            InfoPane infoPane = new InfoPane(sceneManager);
            Scene infoScene = new Scene(infoPane, 1500, 800);

            // Add the scene to the SceneManager
            sceneManager.addScene("info", infoScene);

            // Switch to the InfoPane scene
            sceneManager.switchTo("info");
        });
    }

//    public void setKlantnr(int klantnr) {
//        this.klantnr = klantnr;
//    }
//
//    public int getKlantnr() {
//        return klantnr;
//    }

    private void handleSubmit() {
        try {
            // Validate inputs
            if (txtVerbruikGas.getText().isEmpty() || txtVerbruikStroom.getText().isEmpty()) {
                outputOpgeslagen.setText("Vul alle velden in");
                return;
            }






            Database database = new Database();
            database.addVerbruik(
                    Double.parseDouble(txtVerbruikStroom.getText()),
                    Double.parseDouble(txtVerbruikGas.getText())
            );
            outputOpgeslagen.setText("Data opgeslagen.");
        } catch (NumberFormatException ex) {
            outputOpgeslagen.setText("Vul geldige getallen in");
        }
    }

}
