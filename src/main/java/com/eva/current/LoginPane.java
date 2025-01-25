package com.eva.current;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

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

    private final Button loginButton;
    private final SceneManager sceneManager;

    //    ArrayList<Prijzen> prijzenLijst = new ArrayList<>();
    private Prijzen prijzen;
    public static int klantnr;

    public LoginPane(SceneManager sceneManager, Constraints constraints) {
        this.sceneManager = sceneManager;
        this.loginButton = new Button("login");

        // GridPane setup
        setVgap(10);
        setHgap(10);

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
        Label output = new Label();

//        ArrayList<EnergieData> prijzen = new ArrayList<>();

        constraints.InputFields3(klantnr, txtKlantnr, vNaam, txtVNaam, aNaam, txtANaam, jVoorschot, txtVoorschot);
        constraints.InputFields1(hgas, txtGas, dataVanafGasL, dataVanafGas, dataTotGasL, dataTotGas);
        constraints.InputFields4(hstroom, txtStroom, dataVanafStroomL, dataVanafStroom, dataTotStroomL, dataTotStroom);

        getChildren().addAll(
                klantnr, txtKlantnr, vNaam, txtVNaam, aNaam, txtANaam,
                jVoorschot, txtVoorschot, hgas, txtGas, dataVanafGasL, dataVanafGas,
                dataTotGasL, dataTotGas, hstroom, txtStroom, dataVanafStroomL, dataVanafStroom,
                dataTotStroomL, dataTotStroom, loginButton, output
        );

        GridPane.setConstraints(loginButton, 0, 12);
        GridPane.setConstraints(output, 0, 13);

        // Set action for the "verzend" button
        setOnAction(sceneManager);
    }

    private void setOnAction(SceneManager sceneManager) {
        loginButton.setOnAction(e -> {
            handleSubmit();

            // Pass the last entered Prijzen to EnergyFormPane

            EnergyFormPane energyFormPane = new EnergyFormPane(new Constraints(), prijzen);
            Scene inputScene = new Scene(energyFormPane, 800, 600);

            sceneManager.addScene("input", inputScene);
            sceneManager.switchTo("input");
        });
    }


    private void handleSubmit() {
        try {
            if (txtGas.getText().isEmpty() || txtStroom.getText().isEmpty()) {
                System.out.println("Please fill in all fields");
                return;
            } else if (Database.hasKlantEntry()) {
                System.out.println("Klantnummer bestaat al");

            } else {

//            Prijzen prijzen = new Prijzen(
//                    Double.parseDouble(txtGas.getText()),
//                    Double.parseDouble(txtStroom.getText()),
//                    Double.parseDouble(txtVoorschot.getText())
//
//            );

                Database database = new Database();
                database.addKlant(
                        Integer.parseInt(txtKlantnr.getText()),
                        txtVNaam.getText(),
                        txtANaam.getText(),
                        Double.parseDouble(txtVoorschot.getText()),
                        Double.parseDouble(txtGas.getText()),
                        Double.parseDouble(txtStroom.getText())
                );

            }

//            prijzenLijst.add(prijzen);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number");
        }
    }

    public void handleLogin() {
        String klantnrText = txtKlantnr.getText();

        if (klantnrText.isEmpty()) {
            System.out.println("Please enter a valid klantnummer");
            return;
        }
        try {
            int klantnr = Integer.parseInt(klantnrText);
            LoginPane.klantnr = klantnr;  // Store the klantnr globally in the Session class
            System.out.println("Login successful! klantnr: " + klantnr);

            // Proceed to the next pane or screen, like EnergyFormPane
            // Example: switch to EnergyFormPane here
        } catch (NumberFormatException e) {
            System.out.println("Invalid klantnummer format");
        }
    }
}
