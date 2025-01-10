package com.eva.current;

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
        if ( energieData.isEmpty()) {
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

        if (voorschot < totaleKosten) {
            return "U heeft te weinig voorschot betaald. Voorschot: " + voorschot + " Uw totale kosten zijn: €" + totaleKosten;
        } else if (voorschot <= totaleKosten) {
            return "Uw voorschot is gelijk aan uw totale kosten. Uw totale kosten zijn: €" + totaleKosten + "\n" +
                    "Uw voorschot is: €" + voorschot;
        }

        return "Gemiddelde stroomprijs €: " + kostenStroom + "\n" +
                "Gemiddelde gasprijs €: " + kostenGas;
    }
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


class MonthlyDataCalculator extends DataCalculator {
    public MonthlyDataCalculator(Prijzen prijzen) {
        super(prijzen);
    }
//    private final Prijzen prijzen; // Prijzen object om mee te rekenen

//    public MonthlyDataCalculator(Prijzen prijzen) {
//        this.prijzen = prijzen;
//    }

    @Override
    public String calculateAverage(ArrayList<EnergieData> energieDataLijst) {
        if (energieDataLijst.isEmpty()) {
            return "Geen data beschikbaar.";
        }

        // StringBuilder slaat de resultaten op
        StringBuilder monthlyResults = new StringBuilder();
        // Counter om bij te houden hoeveel weken we hebben gehad
        int count = 0;

        double monthlyVerbruikStroom = 0;
        double monthlyVerbruikGas = 0;

        // Loop door alle energieData en tel de nieuwe verbruiken op
        for (EnergieData energieData : energieDataLijst) {
            monthlyVerbruikStroom += energieData.getVerbruikStroom();
            monthlyVerbruikGas += energieData.getVerbruikGas();
            count++;

            // Process elke vier weken als maand
            if (count % 4 == 0) {
                // Maak een nieuwe lijst met de laatste vier weken
                ArrayList<EnergieData> currentMonthData = new ArrayList<>(energieDataLijst.subList(count - 4, count));
                // Bereken de gemiddelde prijzen voor de maand
                String monthlyPrices = calculateAveragePrices(currentMonthData, prijzen);

                monthlyResults.append("Maand ").append(count / 4).append(" Gemiddeldes:\n")
                        .append("Verbruik Stroom: ").append(monthlyVerbruikStroom / 4).append("\n")
                        .append("Verbruik Gas: ").append(monthlyVerbruikGas / 4).append("\n")
                        .append(monthlyPrices).append("\n\n");

                // Reset maandelijkse totalen
                monthlyVerbruikStroom = 0;
                monthlyVerbruikGas = 0;
            }
        }

        // Handle gedeeltelijke maand (minder dan 4 weken)
        int remainingWeeks = count % 4;
        if (remainingWeeks != 0) {
            ArrayList<EnergieData> remainingMonthData = new ArrayList<>(energieDataLijst.subList(count - remainingWeeks, count));
            String monthlyPrices = calculateAveragePrices(remainingMonthData, prijzen);

            monthlyResults.append("Gedeeltelijke maand (").append(remainingWeeks).append(" weken) Gemiddeldes:\n")
                    .append("Verbruik Stroom: ").append(monthlyVerbruikStroom / remainingWeeks).append("\n")
                    .append("Verbruik Gas: ").append(monthlyVerbruikGas / remainingWeeks).append("\n")
                    .append(monthlyPrices).append("\n");
        }

        return monthlyResults.toString();
    }
}


class YearlyDataCalculator extends DataCalculator {
    public YearlyDataCalculator(Prijzen prijzen) {
        super(prijzen);
    }
//    private final Prijzen prijzen; // Prijzen object om mee te rekenen
//
//    public YearlyDataCalculator(Prijzen prijzen) {
//        this.prijzen = prijzen;
//    }

    @Override
    public String calculateAverage(ArrayList<EnergieData> energieDataLijst) {
        if (energieDataLijst.isEmpty()) {
            return "Geen data beschikbaar.";
        }

        StringBuilder yearlyResults = new StringBuilder();
        int count = 0;

        double yearlyVerbruikStroom = 0;
        double yearlyVerbruikGas = 0;

        for (EnergieData energieData : energieDataLijst) {
            yearlyVerbruikStroom += energieData.getVerbruikStroom();
            yearlyVerbruikGas += energieData.getVerbruikGas();
            count++;

            if (count % 52 == 0) {
                ArrayList<EnergieData> currentYearData = new ArrayList<>(energieDataLijst.subList(count - 52, count));
                String yearlyPrices = calculateAveragePrices(currentYearData, prijzen);
                yearlyResults.append("Jaar ").append(count / 52).append(" Gemiddeldes:\n")
                        .append("Verbruik Stroom: ").append(yearlyVerbruikStroom).append("\n")
                        .append("Verbruik Gas: ").append(yearlyVerbruikGas).append("\n\n")
                        .append(yearlyPrices).append("\n\n");

                yearlyVerbruikStroom = 0;
                yearlyVerbruikGas = 0;
            }
        }

        if (count % 365 != 0) {
            int remainingDays = count % 365;
            yearlyResults.append("Gedeeltelijk jaar (").append(remainingDays).append(" dagen) Gemiddeldes:\n")
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

        return "Gemiddelde stroomkosten: €" + kostenStroom + "\n" +
                "Gemiddelde gaskosten: €" + kostenGas;
    }
}