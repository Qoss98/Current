package com.eva.current;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoPane extends BorderPane {
    private final ListView<String> verbruikList = new ListView<>();
    private final ListView<String> monthlyPricesList = new ListView<>();
    private final ListView<String> yearlyPricesList = new ListView<>();

    private final ObservableList<String> verbruikEntries = FXCollections.observableArrayList();
    private final ObservableList<String> monthlyPrices = FXCollections.observableArrayList();
    private final ObservableList<String> yearlyPrices = FXCollections.observableArrayList();



    private final SceneManager sceneManager;

    public InfoPane(SceneManager sceneManager) {
        this.sceneManager = sceneManager;


        setPadding(new Insets(10));


        loadVerbruikEntries();


        // Set up ListViews
        verbruikList.setItems(verbruikEntries);
        monthlyPricesList.setItems(monthlyPrices);
        yearlyPricesList.setItems(yearlyPrices);


        Button deleteButton = new Button("Delete Selected Entry");
        deleteButton.setOnAction(e -> deleteSelectedEntry());

        Button editButton = new Button("Edit Selected Entry");
        editButton.setOnAction(e -> editSelectedEntry());

        Button backButton = new Button("Back");
        backButton.setOnAction(e ->
                sceneManager.switchTo("login")
        );


        VBox leftPanel = new VBox(10, new Label("Verbruik Entries:"), verbruikList, deleteButton, editButton);
        VBox centerPanel = new VBox(10, new Label("Maandelijkse prijzen:"), monthlyPricesList);
        VBox rightPanel = new VBox(10, new Label("Jaarlijkse prijzen:"), yearlyPricesList, backButton);

        setLeft(leftPanel);
        setCenter(centerPanel);
        setRight(rightPanel);
    }

    // Entries laden vanuit de database
    private void loadVerbruikEntries() {
        verbruikEntries.clear();
        String query = "SELECT prijs_id, v_stroom, v_gas FROM verbruik";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int entryId = rs.getInt("prijs_id");
                double vStroom = rs.getDouble("v_stroom");
                double vGas = rs.getDouble("v_gas");

                verbruikEntries.add("Entry " + entryId + ": Stroom=" + vStroom + ", Gas=" + vGas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Delete de geselecteerde verbruik entry
    private void deleteSelectedEntry() {
        String selectedEntry = verbruikList.getSelectionModel().getSelectedItem();
        if (selectedEntry != null) {
            int entryId = Integer.parseInt(selectedEntry.split(":")[0].replace("Entry ", ""));

            String query = "DELETE FROM verbruik WHERE prijs_id = ?";
            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, entryId);
                stmt.executeUpdate();

                // Reload data
                loadVerbruikEntries();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Edit de geselecteerde verbruik entry
    private void editSelectedEntry() {
        String selectedEntry = verbruikList.getSelectionModel().getSelectedItem();
        if (selectedEntry != null) {
            int entryId = Integer.parseInt(selectedEntry.split(":")[0].replace("Entry ", ""));

            // Dialoogvenster voor het bewerken van de invoer
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Edit Entry");
            dialog.setHeaderText("Edit Verbruik Entry");
            dialog.setContentText("Voer nieuwe waarde in voor Stroom en Gas (comma-separated):");

            dialog.showAndWait().ifPresent(newValues -> {
                String[] values = newValues.split(",");
                if (values.length == 2) {
                    try {
                        double newStroom = Double.parseDouble(values[0].trim());
                        double newGas = Double.parseDouble(values[1].trim());

                        String query = "UPDATE verbruik SET v_stroom = ?, v_gas = ? WHERE prijs_id = ?";
                        try (Connection conn = Database.getConnection();
                             PreparedStatement stmt = conn.prepareStatement(query)) {
                            stmt.setDouble(1, newStroom);
                            stmt.setDouble(2, newGas);
                            stmt.setInt(3, entryId);
                            stmt.executeUpdate();


                            loadVerbruikEntries();

                        }
                    } catch (NumberFormatException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
