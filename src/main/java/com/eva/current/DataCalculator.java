package com.eva.current;

import java.util.ArrayList;

public class DataCalculator {

    private static double totalVoorschot = 0;
    private static double totalGas = 0;
    private static double totalStroom = 0;
    private static double totalVerbruikStroom = 0;
    private static double totalVerbruikGas = 0;


    public String calculateAverage(ArrayList<EnergieData> energieDataLijst) {
        int count = energieDataLijst.size();

        for (EnergieData energieData : energieDataLijst) {
//            totalVoorschot = energieData.getJaarlijksVoorschot();
//            totalGas += energieData.getHuidigeGasprijs();
//            totalStroom += energieData.getHuidigeStroomprijs();
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
        if ( energieData.isEmpty()) {
            return "Geen data beschikbaar.";
        }


        double strPrijs = prijzen.getStroomPrijs();
        double gasPrijs = prijzen.getGasPrijs();

        double totaalStroomVerbruik = 0;
        double totaalGasVerbruik = 0;

        for (EnergieData energieDatum : energieData) {
            totaalStroomVerbruik += energieDatum.getVerbruikStroom();
            totaalGasVerbruik += energieDatum.getVerbruikGas();
        }

        double kostenStroom = totaalStroomVerbruik * strPrijs;
        double kostenGas = totaalGasVerbruik * gasPrijs;

        return "Gemiddelde stroomprijs: " + kostenStroom + "\n" +
                "Gemiddelde gasprijs: " + kostenGas;
    }
    }



class WeeklyDataCalculator extends DataCalculator {
    @Override
    public String calculateAverage(ArrayList<EnergieData> energieDataLijst) {
        if (energieDataLijst.isEmpty()) {
            return "Geen data beschikbaar.";
        }

        StringBuilder weeklyResults = new StringBuilder();
        int count = 0;

        double weeklyVoorschot = 0;
        double weeklyGas = 0;
        double weeklyStroom = 0;
        double weeklyVerbruikStroom = 0;
        double weeklyVerbruikGas = 0;

        for (EnergieData energieData : energieDataLijst) {
//            weeklyVoorschot += energieData.getJaarlijksVoorschot();
//            weeklyGas += energieData.getHuidigeGasprijs();
//            weeklyStroom += energieData.getHuidigeStroomprijs();
            weeklyVerbruikStroom += energieData.getVerbruikStroom();
            weeklyVerbruikGas += energieData.getVerbruikGas();
            count++;

            if (count % 7 == 0) {
                weeklyResults.append("Week ").append(count / 7).append(" Gemiddeldes:\n")
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
            weeklyResults.append("Gedeeltelijke week (").append(remainingDays).append(" dagen) Gemiddeldes:\n")
                    .append("Voorschot: ").append(weeklyVoorschot / remainingDays).append("\n")
                    .append("Gasprijs: ").append(weeklyGas / remainingDays).append("\n")
                    .append("Stroomprijs: ").append(weeklyStroom / remainingDays).append("\n")
                    .append("Verbruik Stroom: ").append(weeklyVerbruikStroom / remainingDays).append("\n")
                    .append("Verbruik Gas: ").append(weeklyVerbruikGas / remainingDays).append("\n");
        }

        return weeklyResults.toString();
    }
}

class MonthlyDataCalculator extends DataCalculator {
    @Override
    public String calculateAverage(ArrayList<EnergieData> energieDataLijst) {
        if (energieDataLijst.isEmpty()) {
            return "Geen data beschikbaar.";
        }

        StringBuilder monthlyResults = new StringBuilder();
        int count = 0;

        double monthlyVoorschot = 0;
        double monthlyGas = 0;
        double monthlyStroom = 0;
        double monthlyVerbruikStroom = 0;
        double monthlyVerbruikGas = 0;

        for (EnergieData energieData : energieDataLijst) {
//            monthlyVoorschot += energieData.getJaarlijksVoorschot();
//            monthlyGas += energieData.getHuidigeGasprijs();
//            monthlyStroom += energieData.getHuidigeStroomprijs();
            monthlyVerbruikStroom += energieData.getVerbruikStroom();
            monthlyVerbruikGas += energieData.getVerbruikGas();
            count++;

            if (count % 30 == 0) {
                monthlyResults.append("Maand ").append(count / 30).append(" Gemiddeldes:\n")
                        .append("Voorschot: ").append(monthlyVoorschot / 30).append("\n")
                        .append("Gasprijs: ").append(monthlyGas / 30).append("\n")
                        .append("Stroomprijs: ").append(monthlyStroom / 30).append("\n")
                        .append("Verbruik Stroom: ").append(monthlyVerbruikStroom / 30).append("\n")
                        .append("Verbruik Gas: ").append(monthlyVerbruikGas / 30).append("\n\n");
            }
            else if (count % 31 == 0) {
                monthlyResults.append("Maand ").append(count / 31).append(" Gemiddeldes:\n")
                        .append("Voorschot: ").append(monthlyVoorschot / 31).append("\n")
                        .append("Gasprijs: ").append(monthlyGas / 31).append("\n")
                        .append("Stroomprijs: ").append(monthlyStroom / 31).append("\n")
                        .append("Verbruik Stroom: ").append(monthlyVerbruikStroom / 31).append("\n")
                        .append("Verbruik Gas: ").append(monthlyVerbruikGas / 31).append("\n\n");
            }

            monthlyVoorschot = 0;
            monthlyGas = 0;
            monthlyStroom = 0;
            monthlyVerbruikStroom = 0;
            monthlyVerbruikGas = 0;
        }

        if (count % 30 != 0) {
            double remainingDaysDouble = count % 30.5;
            int remainingDays = (int) Math.round(remainingDaysDouble);
            monthlyResults.append("Gedeeltelijke maand (").append(remainingDays).append(" dagen) Gemiddeldes:\n")
                    .append("Voorschot: ").append(monthlyVoorschot / remainingDays).append("\n")
                    .append("Gasprijs: ").append(monthlyGas / remainingDays).append("\n")
                    .append("Stroomprijs: ").append(monthlyStroom / remainingDays).append("\n")
                    .append("Verbruik Stroom: ").append(monthlyVerbruikStroom / remainingDays).append("\n")
                    .append("Verbruik Gas: ").append(monthlyVerbruikGas / remainingDays).append("\n");
        }
        return monthlyResults.toString();
    }
}

class YearlyDataCalculator extends DataCalculator {
    @Override
    public String calculateAverage(ArrayList<EnergieData> energieDataLijst) {
        if (energieDataLijst.isEmpty()) {
            return "Geen data beschikbaar.";
        }

        StringBuilder yearlyResults = new StringBuilder();
        int count = 0;

        double yearlyVoorschot = 0;
        double yearlyGas = 0;
        double yearlyStroom = 0;
        double yearlyVerbruikStroom = 0;
        double yearlyVerbruikGas = 0;

        for (EnergieData energieData : energieDataLijst) {
//            yearlyVoorschot += energieData.getJaarlijksVoorschot();
//            yearlyGas += energieData.getHuidigeGasprijs();
//            yearlyStroom += energieData.getHuidigeStroomprijs();
            yearlyVerbruikStroom += energieData.getVerbruikStroom();
            yearlyVerbruikGas += energieData.getVerbruikGas();
            count++;

            if (count % 365 == 0) {
                yearlyResults.append("Jaar ").append(count / 365).append(" Gemiddeldes:\n")
                        .append("Voorschot: ").append(yearlyVoorschot / 365).append("\n")
                        .append("Gasprijs: ").append(yearlyGas / 365).append("\n")
                        .append("Stroomprijs: ").append(yearlyStroom / 365).append("\n")
                        .append("Verbruik Stroom: ").append(yearlyVerbruikStroom / 365).append("\n")
                        .append("Verbruik Gas: ").append(yearlyVerbruikGas / 365).append("\n\n");

                yearlyVoorschot = 0;
                yearlyGas = 0;
                yearlyStroom = 0;
                yearlyVerbruikStroom = 0;
                yearlyVerbruikGas = 0;
            }
        }

        if (count % 365 != 0) {
            int remainingDays = count % 365;
            yearlyResults.append("Gedeeltelijk jaar (").append(remainingDays).append(" dagen) Gemiddeldes:\n")
                    .append("Voorschot: ").append(yearlyVoorschot / remainingDays).append("\n")
                    .append("Gasprijs: ").append(yearlyGas / remainingDays).append("\n")
                    .append("Stroomprijs: ").append(yearlyStroom / remainingDays).append("\n")
                    .append("Verbruik Stroom: ").append(yearlyVerbruikStroom / remainingDays).append("\n")
                    .append("Verbruik Gas: ").append(yearlyVerbruikGas / remainingDays).append("\n\n");
        }
        return yearlyResults.toString();
    }

    public String calculateAveragePrices(ArrayList<Prijzen> prijzenLijst, ArrayList<EnergieData> energieData) {
        if (prijzenLijst.isEmpty() || energieData.isEmpty()) {
            return "Geen data beschikbaar.";
        }

        Prijzen laatstePrijzen = prijzenLijst.get(prijzenLijst.size() - 1);
        double strPrijs = laatstePrijzen.getStroomPrijs();
        double gasPrijs = laatstePrijzen.getGasPrijs();

        double totaalStroomVerbruik = 0;
        double totaalGasVerbruik = 0;

        for (EnergieData energieDatum : energieData) {
            totaalStroomVerbruik += energieDatum.getVerbruikStroom();
            totaalGasVerbruik += energieDatum.getVerbruikGas();
        }

        double kostenStroom = totaalStroomVerbruik * strPrijs;
        double kostenGas = totaalGasVerbruik * gasPrijs;

        return "Gemiddelde stroomkosten: " + kostenStroom + "\n" +
                "Gemiddelde gaskosten: " + kostenGas;
    }
}