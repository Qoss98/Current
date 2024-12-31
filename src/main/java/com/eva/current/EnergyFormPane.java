package com.eva.current;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class EnergyFormPane extends GridPane {

    // Define the components as fields
    private final TextField txtKlantnr = new TextField();
    private final TextField txtVNaam = new TextField();
    private final TextField txtANaam = new TextField();
    private final TextField txtVoorschot = new TextField();
    private final TextField txtGas = new TextField();
    private final TextField txtStroom = new TextField();
    private final TextField txtVerbruikStroom = new TextField();
    private final TextField txtVerbruikGas = new TextField();

    private final DatePicker dataVanafGas = new DatePicker();
    private final DatePicker dataTotGas = new DatePicker();
    private final DatePicker dataVanafStroom = new DatePicker();
    private final DatePicker dataTotStroom = new DatePicker();
    private final DatePicker dataVanafVerbruik = new DatePicker();
    private final DatePicker dataTotVerbruik = new DatePicker();

    private final Label outputOpgeslagen = new Label();
    private final Label outputWeek = new Label();
    private final Label outputMaand = new Label();
    private final Label outputJaar = new Label();

    ArrayList<EnergieData> energieData = new ArrayList<>();

    public EnergyFormPane(Constraints constraints) {
        // Set up the GridPane
        setVgap(10);
        setHgap(10);

        // Create labels
        Label klantnr = new Label("Klantnummer:");
        Label vNaam = new Label("Voornaam:");
        Label aNaam = new Label("Achternaam:");
        Label jVoorschot = new Label("Jaarlijks Voorschot:");
        Label hgas = new Label("Huidige Gasprijs:");
        Label dataVanafGasL = new Label("Datum vanaf:");
        Label dataTotGasL = new Label("Datum tot:");
        Label hstroom = new Label("Huidige Stroomprijs in kWh:");
        Label dataVanafStroomL = new Label("Datum vanaf:");
        Label dataTotStroomL = new Label("Datum tot:");
        Label verbruikStroom = new Label("Verbruik Stroom in kWh:");
        Label verbruikGas = new Label("Verbruik Gas in m3:");
        Label dataVanafVerbruikL = new Label("Datum vanaf:");
        Label dataTotVerbruikL = new Label("Datum tot:");

        // Submit button
        Button verzend = new Button("Verzend");
        Button toonGemiddelde = new Button("Toon Gemiddelde");

        // Apply constraints using the Constraints class
        constraints.InputFields3(klantnr, txtKlantnr, vNaam, txtVNaam, aNaam, txtANaam, jVoorschot, txtVoorschot);
        constraints.InputFields1(hgas, txtGas, dataVanafGasL, dataVanafGas, dataTotGasL, dataTotGas);
        constraints.InputFields4(hstroom, txtStroom, dataVanafStroomL, dataVanafStroom, dataTotStroomL, dataTotStroom);
        constraints.InputFields2(verbruikStroom, txtVerbruikStroom, verbruikGas, txtVerbruikGas, dataVanafVerbruikL, dataVanafVerbruik, dataTotVerbruikL, dataTotVerbruik);

        // Add all components to the grid
        getChildren().addAll(
                klantnr, txtKlantnr, vNaam, txtVNaam, aNaam, txtANaam,
                jVoorschot, txtVoorschot, hgas, txtGas, dataVanafGasL, dataVanafGas,
                dataTotGasL, dataTotGas, hstroom, txtStroom, dataVanafStroomL, dataVanafStroom,
                dataTotStroomL, dataTotStroom, verbruikStroom, txtVerbruikStroom, verbruikGas,
                txtVerbruikGas, dataVanafVerbruikL, dataVanafVerbruik, dataTotVerbruikL,
                dataTotVerbruik, verzend, toonGemiddelde, outputOpgeslagen, outputWeek, outputMaand, outputJaar
        );

        // Set constraints for the submit button and output label
        GridPane.setConstraints(verzend, 0, 10);
        GridPane.setConstraints(toonGemiddelde, 0, 11);
        GridPane.setConstraints(outputOpgeslagen, 0, 12);
        GridPane.setConstraints(outputWeek, 1, 13);
        GridPane.setConstraints(outputMaand, 2, 13);
        GridPane.setConstraints(outputJaar, 3, 13);

        // Set up the button action
        verzend.setOnAction(e -> handleSubmit());
        DataCalculator dataCalculator = new DataCalculator();
        toonGemiddelde.setOnAction(e -> {
        if (energieData.isEmpty()){
            outputOpgeslagen.setText("Er zijn nog geen gegevens ingevoerd");
        }else{
//            DataCalculator.calculateAverage(energieData);
//            String average = DataCalculator.calculateAverage(energieData);
//            output.setText(average);
//
//            DataCalculator.calculateAverageWeekly(energieData);
            WeeklyDataCalculator weeklyDataCalculator = new WeeklyDataCalculator();
            String averageWeek = weeklyDataCalculator.calculateAverage(energieData);
            outputWeek.setText(averageWeek);

            MonthlyDataCalculator monthlyDataCalculator = new MonthlyDataCalculator();
            String averageMonth = monthlyDataCalculator.calculateAverage(energieData);
            outputMaand.setText(averageMonth);

            YearlyDataCalculator yearlyDataCalculator = new YearlyDataCalculator();
            String averageYear = yearlyDataCalculator.calculateAverage(energieData);
            outputJaar.setText(averageYear);
        }
    });
    }

    // Event handler for the "Verzend" button
    private void handleSubmit() {
        try {
            // Validate inputs
            if (txtVoorschot.getText().isEmpty() || txtGas.getText().isEmpty() || txtStroom.getText().isEmpty()) {
                outputOpgeslagen.setText("Vul alle velden in");
                return;
            }

            // Create EnergieData object
            EnergieData data = new EnergieData(
                    Double.parseDouble(txtVoorschot.getText()),
                    Double.parseDouble(txtGas.getText()),
                    Double.parseDouble(txtStroom.getText()),
                    Double.parseDouble(txtVerbruikStroom.getText()),
                    Double.parseDouble(txtVerbruikGas.getText())
            );

            energieData.add(data);
            outputOpgeslagen.setText("Data opgeslagen. Totaal aantal: " + energieData.size());


            // Print data to the output label
//            data.printData(output);
        } catch (NumberFormatException ex) {
            outputOpgeslagen.setText("Vul geldige getallen in");
        }
    }
}

