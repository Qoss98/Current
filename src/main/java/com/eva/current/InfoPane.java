package com.eva.current;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


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

        // Laden van data
        loadVerbruikEntries();
        loadMonthlyAverages();
        loadYearlyAverages();


        // Set up van ListViews
        verbruikList.setItems(verbruikEntries);
        monthlyPricesList.setItems(monthlyPrices);
        yearlyPricesList.setItems(yearlyPrices);


        Button deleteButton = new Button("Delete Entry");
        deleteButton.setOnAction(e -> deleteSelectedEntry());

        Button editButton = new Button("Edit Entry");
        editButton.setOnAction(e -> editSelectedEntry());

        Button backButton = new Button("Terug");
        backButton.setOnAction(e ->
                sceneManager.previousScene()
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

        // Herladen van data
        loadMonthlyAverages();
        loadYearlyAverages();
    }


    private void loadMonthlyAverages() {
        monthlyPrices.clear();

        String query = "SELECT CEIL(prijs_id / 4.0) AS month, " +
                "voorschot, " +
                "AVG(v_stroom) AS avg_stroom, " +
                "AVG(v_gas) AS avg_gas, " +
                "SUM(v_stroom * s_prijs) AS total_stroom_prijs, " +
                "SUM(v_gas * g_prijs) AS total_gas_prijs " +
                "FROM verbruik JOIN klant " +
                "ON verbruik.klant_id = klant.klantnr " +
                "GROUP BY CEIL(prijs_id / 4.0), voorschot";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int month = rs.getInt("month");
                double avgStroom = rs.getDouble("avg_stroom");
                double avgGas = rs.getDouble("avg_gas");
                double totalStroomPrijs = rs.getDouble("total_stroom_prijs");
                double totalGasPrijs = rs.getDouble("total_gas_prijs");

                // Format het resultaat en voeg het toe aan de monthlyPrices lijst
                String result = String.format("Maand %d:\n" +
                                "  Gemiddeld Stroomverbruik: %.2f kWh\n" +
                                "  Gemiddeld Gasverbruik: %.2f m3\n" +
                                "  Totaalprijs Stroom: €%.2f\n" +
                                "  Totaalprijs Gas: €%.2f\n",
                        month, avgStroom, avgGas, totalStroomPrijs, totalGasPrijs);
                monthlyPrices.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadYearlyAverages() {
        yearlyPrices.clear();

        String query = "SELECT CEIL(prijs_id / 52.0) AS year, " +
                "voorschot, " +
                "AVG(v_stroom) AS avg_stroom, " +
                "AVG(v_gas) AS avg_gas, " +
                "SUM(v_stroom * s_prijs) AS total_stroom_prijs, " +
                "SUM(v_gas * g_prijs) AS total_gas_prijs " +
                "FROM verbruik JOIN klant " +
                "ON verbruik.klant_id = klant.klantnr " +
                "GROUP BY CEIL(prijs_id / 52.0), voorschot";


        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int year = rs.getInt("year");
                double avgStroom = rs.getDouble("avg_stroom");
                double avgGas = rs.getDouble("avg_gas");
                double totalStroomPrijs = rs.getDouble("total_stroom_prijs");
                double totalGasPrijs = rs.getDouble("total_gas_prijs");
                double voorschot = rs.getDouble("voorschot");

                // Formatteren van de resultaten en toevoegen aan de yearlyPrices lijst
                String result = String.format("Jaar %d:\n" +
                                "  Gemiddeld Stroomverbruik: %.2f kWh\n" +
                                "  Gemiddeld Gasverbruik: %.2f m3\n" +
                                "  Totaalprijs Stroom: €%.2f\n" +
                                "  Totaalprijs Gas: €%.2f\n" +
                                "  Voorschot: €%.2f\n",
                        year, avgStroom, avgGas, totalStroomPrijs, totalGasPrijs, voorschot);

                yearlyPrices.add(result);


                if(totalStroomPrijs + totalGasPrijs > voorschot){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Waarschuwing");
                    alert.setHeaderText("U heeft meer verbruikt dan uw voorschot");
                    alert.setContentText("U heeft meer verbruikt dan uw voorschot. Uw voorschot is €" + voorschot + " en uw totale kosten zijn €" + (totalStroomPrijs + totalGasPrijs));
                    alert.showAndWait();
                }
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

                // Herladen van data
                loadVerbruikEntries();
                loadMonthlyAverages();
                loadYearlyAverages();

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
            dialog.setContentText("Voer nieuwe waarde in voor Stroom en Gas (komma-gescheiden):");

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
                            loadMonthlyAverages();
                            loadYearlyAverages();

                        }
                    } catch (NumberFormatException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
