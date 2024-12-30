package com.eva.current;

import java.util.ArrayList;

public class DataCalculator {

    private static double totalVoorschot = 0;
    private static double totalGas = 0;
    private static double totalStroom = 0;
    private static double totalVerbruikStroom = 0;
    private static double totalVerbruikGas = 0;


    public static String calculateAverage(ArrayList<EnergieData> energieDataLijst) {
        int count = energieDataLijst.size();

        // add the variables up
        for (EnergieData energieData : energieDataLijst) {
            totalVoorschot = energieData.getJaarlijksVoorschot();
            totalGas += energieData.getHuidigeGasprijs();
            totalStroom += energieData.getHuidigeStroomprijs();
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

    public static String calculateAverageWeekly(ArrayList<EnergieData> energieDataLijst){
        return "Wekelijks gemiddelde niet beschikbaar";
    }

}


class WeeklyDataCalculator extends DataCalculator {
    public static String calculateAverage(ArrayList<EnergieData> energieDataLijst) {
        if (energieDataLijst.isEmpty()) {
            return "No data available.";
        }

        StringBuilder weeklyResults = new StringBuilder();
        int count = 0;

        double weeklyVoorschot = 0;
        double weeklyGas = 0;
        double weeklyStroom = 0;
        double weeklyVerbruikStroom = 0;
        double weeklyVerbruikGas = 0;

        for (EnergieData energieData : energieDataLijst) {
            weeklyVoorschot += energieData.getJaarlijksVoorschot();
            weeklyGas += energieData.getHuidigeGasprijs();
            weeklyStroom += energieData.getHuidigeStroomprijs();
            weeklyVerbruikStroom += energieData.getVerbruikStroom();
            weeklyVerbruikGas += energieData.getVerbruikGas();
            count++;

            if (count % 7 == 0) {
                weeklyResults.append("Week ").append(count / 7).append(" Averages:\n")
                        .append("Voorschot: ").append(weeklyVoorschot / 7).append("\n")
                        .append("Gasprijs: ").append(weeklyGas / 7).append("\n")
                        .append("Stroomprijs: ").append(weeklyStroom / 7).append("\n")
                        .append("Verbruik Stroom: ").append(weeklyVerbruikStroom / 7).append("\n")
                        .append("Verbruik Gas: ").append(weeklyVerbruikGas / 7).append("\n\n");

                weeklyVoorschot = 0;
                weeklyGas = 0;
                weeklyStroom = 0;
                weeklyVerbruikStroom = 0;
                weeklyVerbruikGas = 0;
            }
        }

        if (count % 7 != 0) {
            int remainingDays = count % 7;
            weeklyResults.append("Partial Week (").append(remainingDays).append(" days) Averages:\n")
                    .append("Voorschot: ").append(weeklyVoorschot / remainingDays).append("\n")
                    .append("Gasprijs: ").append(weeklyGas / remainingDays).append("\n")
                    .append("Stroomprijs: ").append(weeklyStroom / remainingDays).append("\n")
                    .append("Verbruik Stroom: ").append(weeklyVerbruikStroom / remainingDays).append("\n")
                    .append("Verbruik Gas: ").append(weeklyVerbruikGas / remainingDays).append("\n");
        }

        return weeklyResults.toString();
    }
}
