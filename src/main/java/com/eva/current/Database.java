package com.eva.current;


import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:8889/current";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean hasKlantEntry(int klantnr) {
        String query = "SELECT COUNT(*) AS count FROM klant WHERE klantnr = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, klantnr); // Set the klantnr parameter
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count") > 0; // Return true if count > 0
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if an error occurs or no rows are found
    }

    // Add a new klant entry
    public static void addKlant(int klantnr, String vNaam, String aNaam, double voorschot, double sPrijs, double gPrijs) {
        String query = "INSERT INTO klant (klantnr, v_naam, a_naam, voorschot, s_prijs, g_prijs) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, klantnr); // Add klantnr
            stmt.setString(2, vNaam); // Add v_naam
            stmt.setString(3, aNaam); // Add a_naam
            stmt.setDouble(4, voorschot); // Add voorschot
            stmt.setDouble(5, sPrijs); // Add s_prijs
            stmt.setDouble(6, gPrijs); // Add g_prijs
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a new verbruik entry
    public static void addVerbruik(int klantId, double vGas, double vStroom) {
        System.out.println("Adding verbruik entry for klant " + klantId);
        String query = "INSERT INTO verbruik (v_gas, v_stroom, klant_id) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, vGas);
            stmt.setDouble(2, vStroom);
            stmt.setInt(3, klantId); // Add klant_id
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

