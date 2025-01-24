package com.eva.current;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataCalculator {

    private static double totalVoorschot;
    private static double totalGas = 0;
    private static double totalStroom = 0;
    private static double totalVerbruikStroom = 0;
    private static double totalVerbruikGas = 0;

    final Prijzen prijzen; // Prijzen object om mee te rekenen

    public DataCalculator(Prijzen prijzen) {
        this.prijzen = prijzen;
    }


    public String calculateAverage(ArrayList<EnergieData> energieDataLijst) {
        int count = energieDataLijst.size();

        totalVoorschot = prijzen.getVoorschot();

        for (EnergieData energieData : energieDataLijst) {
            totalVerbruikStroom += energieData.getVerbruikStroom();
            totalVerbruikGas += energieData.getVerbruikGas();
        }

        double avgVoorschot = totalVoorschot / count;
        double avgGas = totalGas / count;
        double avgStroom = totalStroom / count;
        double avgVerbruikStroom = totalVerbruikStroom / count;
        double avgVerbruikGas = totalVerbruikGas / count;

        return "Gemiddelde Jaarlijks Voorschot: " + avgVoorschot + "\n" +
                "Gemiddelde Gasprijs: " + avgGas + "\n" +
                "Gemiddelde Stroomprijs: " + avgStroom + "\n" +
                "Gemiddelde Verbruik Stroom: " + avgVerbruikStroom + "\n" +
                "Gemiddelde Verbruik Gas: " + avgVerbruikGas;
    }

    public String calculateAveragePrices(ArrayList<EnergieData> energieData, Prijzen prijzen) {
        if (energieData.isEmpty()) {
            return "Geen data beschikbaar.";

        }


        double strPrijs = prijzen.getStroomPrijs();
        double gasPrijs = prijzen.getGasPrijs();
        double voorschot = prijzen.getVoorschot();

        double totaalStroomVerbruik = 0;
        double totaalGasVerbruik = 0;

        for (EnergieData energieDatum : energieData) {
            totaalStroomVerbruik += energieDatum.getVerbruikStroom();
            totaalGasVerbruik += energieDatum.getVerbruikGas();
        }

        double kostenStroom = totaalStroomVerbruik * strPrijs;
        double kostenGas = totaalGasVerbruik * gasPrijs;

        double totaleKosten = kostenStroom + kostenGas;

        System.out.println(voorschot);

        System.out.println(voorschot);

        if (voorschot < totaleKosten) {
            return "U heeft te weinig voorschot betaald. Voorschot: " + voorschot + " Uw totale kosten zijn: €" + totaleKosten;
        } else if (voorschot <= totaleKosten) {
            return "Uw voorschot is gelijk aan uw totale kosten. Uw totale kosten zijn: €" + totaleKosten + "\n" +
                    "Uw voorschot is: €" + voorschot;
        }

        return "Gemiddelde stroomprijs €: " + kostenStroom + "\n" +
                "Gemiddelde gasprijs €: " + kostenGas;
    }


//class WeeklyDataCalculator extends DataCalculator {
//    private final Prijzen prijzen; // Prijzen object to calculate prices
//
//    public WeeklyDataCalculator(Prijzen prijzen) {
//        this.prijzen = prijzen;
//    }
//
//    @Override
//    public String calculateAverage(ArrayList<EnergieData> energieDataLijst) {
//        if (energieDataLijst.isEmpty()) {
//            return "Geen data beschikbaar.";
//        }
//
//        StringBuilder weeklyResults = new StringBuilder();
//        int count = 0;
//
//        double weeklyVerbruikStroom = 0;
//        double weeklyVerbruikGas = 0;
//
//        for (EnergieData energieData : energieDataLijst) {
//            weeklyVerbruikStroom += energieData.getVerbruikStroom();
//            weeklyVerbruikGas += energieData.getVerbruikGas();
//            count++;
//
//            // Process data for every full week
//            if (count % 7 == 0) {
//                ArrayList<EnergieData> currentWeekData = new ArrayList<>(energieDataLijst.subList(count - 7, count));
//                String weeklyPrices = calculateAveragePrices(currentWeekData, prijzen);
//
//                weeklyResults.append("Week ").append(count / 7).append(" Gemiddeldes:\n")
//                        .append("Verbruik Stroom: ").append(weeklyVerbruikStroom / 7).append("\n")
//                        .append("Verbruik Gas: ").append(weeklyVerbruikGas / 7).append("\n")
//                        .append(weeklyPrices).append("\n\n");
//
//                // Reset weekly totals
//                weeklyVerbruikStroom = 0;
//                weeklyVerbruikGas = 0;
//            }
//        }
//
//        // Handle remaining data (partial week)
//        int remainingDays = count % 7;
//        if (remainingDays != 0) {
//            ArrayList<EnergieData> remainingWeekData = new ArrayList<>(energieDataLijst.subList(count - remainingDays, count));
//            String weeklyPrices = calculateAveragePrices(remainingWeekData, prijzen);
//
//            weeklyResults.append("Gedeeltelijke week (").append(remainingDays).append(" dagen) Gemiddeldes:\n")
//                    .append("Verbruik Stroom: ").append(weeklyVerbruikStroom / remainingDays).append("\n")
//                    .append("Verbruik Gas: ").append(weeklyVerbruikGas / remainingDays).append("\n")
//                    .append(weeklyPrices).append("\n");
//        }
//
//        return weeklyResults.toString();
//    }
//}


    public String calculateMonthlyAverage() {
        String query = "SELECT CEIL(prijs_id / 4.0) AS month, SUM(v_stroom) AS total_stroom, SUM(v_gas) AS total_gas " +
                "FROM verbruik GROUP BY CEIL(prijs_id / 4.0)";
        StringBuilder monthlyResults = new StringBuilder();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int month = rs.getInt("month");
                double totalStroom = rs.getDouble("total_stroom");
                double totalGas = rs.getDouble("total_gas");

                monthlyResults.append("Maand ").append(month).append(":\n")
                        .append("Totaal Stroom: ").append(totalStroom).append("\n")
                        .append("Totaal Gas: ").append(totalGas).append("\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return monthlyResults.toString();
    }


    public String calculateYearlyAverages() {
        String query = "SELECT CEIL(prijs_id / 52.0) AS year, SUM(v_stroom) AS total_stroom, SUM(v_gas) AS total_gas " +
                "FROM verbruik GROUP BY CEIL(prijs_id / 52.0)";
        StringBuilder yearlyResults = new StringBuilder();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int year = rs.getInt("year");
                double totalStroom = rs.getDouble("total_stroom");
                double totalGas = rs.getDouble("total_gas");

                yearlyResults.append("Year ").append(year).append(":\n")
                        .append("Total Stroom: ").append(totalStroom).append("\n")
                        .append("Total Gas: ").append(totalGas).append("\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return yearlyResults.toString();
    }
}