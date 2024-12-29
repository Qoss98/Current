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
}
