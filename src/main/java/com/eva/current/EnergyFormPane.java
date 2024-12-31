package com.eva.current;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class EnergyFormPane extends GridPane {
    // Velden definieren
    private final TextField txtVerbruikStroom = new TextField();
    private final TextField txtVerbruikGas = new TextField();


    private final DatePicker dataVanafVerbruik = new DatePicker();
    private final DatePicker dataTotVerbruik = new DatePicker();

    private final Label outputOpgeslagen = new Label();
    private final Label outputWeek = new Label();
    private final Label outputMaand = new Label();
    private final Label outputJaar = new Label();

    ArrayList<EnergieData> energieData = new ArrayList<>();

    public EnergyFormPane(Constraints constraints) {
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
        constraints.OutputFields(outputOpgeslagen, outputWeek, outputMaand, outputJaar);

        // Alles toevoegen aan de GridPane
        getChildren().addAll(
                verbruikStroom, txtVerbruikStroom, verbruikGas,
                txtVerbruikGas, dataVanafVerbruikL, dataVanafVerbruik, dataTotVerbruikL,
                dataTotVerbruik, verzend, toonGemiddelde, outputOpgeslagen, outputWeek, outputMaand, outputJaar
        );

        // Constraints voor alle Buttons
        GridPane.setConstraints(verzend, 0, 10);
        GridPane.setConstraints(toonGemiddelde, 0, 11);

        // setonAction logica voor de buttons
        verzend.setOnAction(e -> handleSubmit());
        toonGemiddelde.setOnAction(e -> {
            if (energieData.isEmpty()) {
                outputOpgeslagen.setText("Er zijn nog geen gegevens ingevoerd");
            } else {
                WeeklyDataCalculator weeklyDataCalculator = new WeeklyDataCalculator();
                String averageWeek = weeklyDataCalculator.calculateAverage(energieData);
                outputWeek.setText(averageWeek);

                MonthlyDataCalculator monthlyDataCalculator = new MonthlyDataCalculator();
                String averageMonth = monthlyDataCalculator.calculateAverage(energieData);
                outputMaand.setText(averageMonth);

                YearlyDataCalculator yearlyDataCalculator = new YearlyDataCalculator();
                String averageYear = yearlyDataCalculator.calculateAverage(energieData);
                outputJaar.setText(averageYear);
//        }
//    });
            }

            // Event handler voor de "Verzend" button

        });
    }

    private void handleSubmit() {
        try {
            // Validate inputs
            if (txtVerbruikGas.getText().isEmpty() || txtVerbruikStroom.getText().isEmpty()) {
                outputOpgeslagen.setText("Vul alle velden in");
                return;
            }

            // EnergieData object creeeren
            EnergieData data = new EnergieData(
//                    Double.parseDouble(txtVoorschot.getText()),
//                    Double.parseDouble(txtGas.getText()),
//                    Double.parseDouble(txtStroom.getText()),
                    Double.parseDouble(txtVerbruikStroom.getText()),
                    Double.parseDouble(txtVerbruikGas.getText())
            );

            energieData.add(data);
            outputOpgeslagen.setText("Data opgeslagen. Totaal aantal: " + energieData.size());
        }
        // Catch met foutmelding
        catch (NumberFormatException ex) {
            outputOpgeslagen.setText("Vul geldige getallen in");
        }
    }
}
