package com.eva.current;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class LoginPane extends GridPane {

    private final TextField txtKlantnr = new TextField();
    private final TextField txtVNaam = new TextField();
    private final TextField txtANaam = new TextField();
    private final TextField txtVoorschot = new TextField();
    private final TextField txtGas = new TextField();
    private final TextField txtStroom = new TextField();

    private final DatePicker dataVanafGas = new DatePicker();
    private final DatePicker dataTotGas = new DatePicker();
    private final DatePicker dataVanafStroom = new DatePicker();
    private final DatePicker dataTotStroom = new DatePicker();

    private final Label output = new Label();
    private final Label klantnr = new Label("Klantnummer:");
    private final Label vNaam = new Label("Voornaam:");
    private final Label aNaam = new Label("Achternaam:");
    private final Label jVoorschot = new Label("Jaarlijks Voorschot:");
    private final Label hgas = new Label("Huidige Gasprijs:");
    private final Label dataVanafGasL = new Label("Datum vanaf:");
    private final Label dataTotGasL = new Label("Datum tot:");
    private final Label hstroom = new Label("Huidige Stroomprijs in kWh:");
    private final Label dataVanafStroomL = new Label("Datum vanaf:");
    private final Label dataTotStroomL = new Label("Datum tot:");

    private final Button verzend;

//    ArrayList<Prijzen> prijzenLijst = new ArrayList<>();
    private Prijzen prijzen;

    public LoginPane(SceneManager sceneManager, Constraints constraints) {
        this.verzend = new Button("Verzend");

        // GridPane setup
        setVgap(10);
        setHgap(10);

//        ArrayList<EnergieData> prijzen = new ArrayList<>();

        // Constraints toevoegen met gebruik van class Constraints
        constraints.InputFields3(klantnr, txtKlantnr, vNaam, txtVNaam, aNaam, txtANaam, jVoorschot, txtVoorschot);
        constraints.InputFields1(hgas, txtGas, dataVanafGasL, dataVanafGas, dataTotGasL, dataTotGas);
        constraints.InputFields4(hstroom, txtStroom, dataVanafStroomL, dataVanafStroom, dataTotStroomL, dataTotStroom);

        // Alles toevoegen aan de GridPane
        getChildren().addAll(
                klantnr, txtKlantnr, vNaam, txtVNaam, aNaam, txtANaam,
                jVoorschot, txtVoorschot, hgas, txtGas, dataVanafGasL, dataVanafGas,
                dataTotGasL, dataTotGas, hstroom, txtStroom, dataVanafStroomL, dataVanafStroom,
                dataTotStroomL, dataTotStroom, verzend, output
        );

        // Constraints handmatig voor de "verzend" button, want makkelijker
        GridPane.setConstraints(verzend, 0, 11);
        GridPane.setConstraints(output, 0, 13);

        // Set action voor de "verzend" button
        setOnAction(sceneManager);
    }

    private void setOnAction(SceneManager sceneManager) {
        verzend.setOnAction(e -> {
            handleSubmit();

            //Parse text naar double en initieer prijzen object
            prijzen = new Prijzen(
                    Double.parseDouble(txtGas.getText()),
                    Double.parseDouble(txtStroom.getText()),
                    Double.parseDouble(txtVoorschot.getText())
            );

            // Creeer een nieuwe EnergyFormPane en switch naar de input scene
            EnergyFormPane energyFormPane = new EnergyFormPane(new Constraints(), prijzen);
            Scene inputScene = new Scene(energyFormPane, 800, 600);

            sceneManager.addScene("input", inputScene);
            sceneManager.switchTo("input");
        });
    }



    private void handleSubmit() {
        try {
            if (txtGas.getText().isEmpty() || txtStroom.getText().isEmpty()) {
                output.setText("Vul alle velden in!");
                return;
            }

            Prijzen prijzen = new Prijzen(
                    Double.parseDouble(txtGas.getText()),
                    Double.parseDouble(txtStroom.getText()),
                    Double.parseDouble(txtVoorschot.getText())

            );

//            prijzenLijst.add(prijzen);
        } catch (NumberFormatException e) {
            output.setText("Alleen geldige nummers a.u.b.");
        }
    }
}
