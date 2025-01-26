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
        loadMonthlyAverages();
        loadYearlyAverages();


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

        Button refreshMonthlyButton = new Button("Refresh Monthly Averages");
        refreshMonthlyButton.setOnAction(e -> loadMonthlyAverages());

        Button refreshYearlyButton = new Button("Refresh Yearly Averages");
        refreshYearlyButton.setOnAction(e -> loadYearlyAverages());

// Add buttons to the UI


        VBox leftPanel = new VBox(10, new Label("Verbruik Entries:"), verbruikList, deleteButton, editButton);
        VBox centerPanel = new VBox(10, new Label("Maandelijkse prijzen:"), monthlyPricesList, refreshMonthlyButton);
        VBox rightPanel = new VBox(10, new Label("Jaarlijkse prijzen:"), yearlyPricesList, refreshYearlyButton, backButton);

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

        // Calculate and display monthly and yearly averages
        String monthlyAverages = calculateMonthlyAverages();
        String yearlyAverages = calculateYearlyAverages();

        // Update the ListViews
        monthlyPrices.clear();
        monthlyPrices.add(monthlyAverages);

        yearlyPrices.clear();
        yearlyPrices.add(yearlyAverages);
        loadMonthlyAverages();
        loadYearlyAverages();
    }


    private void loadMonthlyAverages() {
        monthlyPrices.clear(); // Clear the existing entries

        String query = "SELECT CEIL(prijs_id / 4.0) AS month, AVG(v_stroom) AS avg_stroom, AVG(v_gas) AS avg_gas, " +
                "AVG(s_prijs) AS avg_stroom_prijs, AVG(g_prijs) AS avg_gas_prijs " +
                "FROM verbruik JOIN klant ON verbruik.klant_id = klant.klantnr " +
                "GROUP BY CEIL(prijs_id / 4.0)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int month = rs.getInt("month");
                double avgStroom = rs.getDouble("avg_stroom");
                double avgGas = rs.getDouble("avg_gas");
                double avgStroomPrijs = rs.getDouble("avg_stroom_prijs");
                double avgGasPrijs = rs.getDouble("avg_gas_prijs");

                // Format the result and add it to the monthlyPrices list
                String result = String.format("Month %d:\n" +
                                "  Average Stroom Usage: %.2f kWh\n" +
                                "  Average Gas Usage: %.2f m3\n" +
                                "  Average Stroom Price: €%.2f\n" +
                                "  Average Gas Price: €%.2f\n",
                        month, avgStroom, avgGas, avgStroomPrijs, avgGasPrijs);
                monthlyPrices.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadYearlyAverages() {
        yearlyPrices.clear(); // Clear the existing entries

        String query = "SELECT CEIL(prijs_id / 52.0) AS year, AVG(v_stroom) AS avg_stroom, AVG(v_gas) AS avg_gas, " +
                "AVG(s_prijs) AS avg_stroom_prijs, AVG(g_prijs) AS avg_gas_prijs " +
                "FROM verbruik JOIN klant ON verbruik.klant_id = klant.klantnr " +
                "GROUP BY CEIL(prijs_id / 52.0)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int year = rs.getInt("year");
                double avgStroom = rs.getDouble("avg_stroom");
                double avgGas = rs.getDouble("avg_gas");
                double avgStroomPrijs = rs.getDouble("avg_stroom_prijs");
                double avgGasPrijs = rs.getDouble("avg_gas_prijs");

                // Format the result and add it to the yearlyPrices list
                String result = String.format("Year %d:\n" +
                                "  Average Stroom Usage: %.2f kWh\n" +
                                "  Average Gas Usage: %.2f m3\n" +
                                "  Average Stroom Price: €%.2f\n" +
                                "  Average Gas Price: €%.2f\n",
                        year, avgStroom, avgGas, avgStroomPrijs, avgGasPrijs);
                yearlyPrices.add(result);
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

    public String calculateMonthlyAverages() {
        String query = "SELECT CEIL(prijs_id / 4.0) AS month, AVG(v_stroom) AS avg_stroom, AVG(v_gas) AS avg_gas, " +
                "AVG(s_prijs) AS avg_stroom_prijs, AVG(g_prijs) AS avg_gas_prijs " +
                "FROM verbruik JOIN klant ON verbruik.klant_id = klant.klantnr " +
                "GROUP BY CEIL(prijs_id / 4.0)";
        StringBuilder monthlyResults = new StringBuilder();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int month = rs.getInt("month");
                double avgStroom = rs.getDouble("avg_stroom");
                double avgGas = rs.getDouble("avg_gas");
                double avgStroomPrijs = rs.getDouble("avg_stroom_prijs");
                double avgGasPrijs = rs.getDouble("avg_gas_prijs");

                monthlyResults.append("Month ").append(month).append(":\n")
                        .append("  Average Stroom Usage: ").append(avgStroom).append(" kWh\n")
                        .append("  Average Gas Usage: ").append(avgGas).append(" m3\n")
                        .append("  Average Stroom Price: €").append(avgStroomPrijs).append("\n")
                        .append("  Average Gas Price: €").append(avgGasPrijs).append("\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return monthlyResults.toString();
    }

    public String calculateYearlyAverages() {
        String query = "SELECT CEIL(prijs_id / 52.0) AS year, AVG(v_stroom) AS avg_stroom, AVG(v_gas) AS avg_gas, " +
                "AVG(s_prijs) AS avg_stroom_prijs, AVG(g_prijs) AS avg_gas_prijs " +
                "FROM verbruik JOIN klant ON verbruik.klant_id = klant.klantnr " +
                "GROUP BY CEIL(prijs_id / 52.0)";
        StringBuilder yearlyResults = new StringBuilder();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int year = rs.getInt("year");
                double avgStroom = rs.getDouble("avg_stroom");
                double avgGas = rs.getDouble("avg_gas");
                double avgStroomPrijs = rs.getDouble("avg_stroom_prijs");
                double avgGasPrijs = rs.getDouble("avg_gas_prijs");

                yearlyResults.append("Year ").append(year).append(":\n")
                        .append("  Average Stroom Usage: ").append(avgStroom).append(" kWh\n")
                        .append("  Average Gas Usage: ").append(avgGas).append(" m3\n")
                        .append("  Average Stroom Price: €").append(avgStroomPrijs).append("\n")
                        .append("  Average Gas Price: €").append(avgGasPrijs).append("\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return yearlyResults.toString();
    }
}
