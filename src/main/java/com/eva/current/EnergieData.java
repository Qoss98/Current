package com.eva.current;

import javafx.scene.control.Label;

public class EnergieData {
    private double jaarlijksVoorschot;
    private double huidigeGasprijs;
    private double huidigeStroomprijs;

    private double verbruikStroom;
    private double verbruikGas;
    private double totaalPrijs;

    public EnergieData(double jaarlijksVoorschot, double huidigeGasprijs, double huidigeStroomprijs, double verbruikStroom, double verbruikGas) {
        this.jaarlijksVoorschot = jaarlijksVoorschot;
        this.huidigeGasprijs = huidigeGasprijs;
        this.huidigeStroomprijs = huidigeStroomprijs;
        this.verbruikStroom = verbruikStroom;
        this.verbruikGas = verbruikGas;
    }


    public void printData(Label output) {
        String data = "Jaarlijks Voorschot: " + jaarlijksVoorschot + "\n" +
                "Gasprijs: " + huidigeGasprijs + "\n" +
                "Stroomprijs: " + huidigeStroomprijs
                + "\n" + "Verbruik Stroom: " + verbruikStroom + "\n" + "Verbruik Gas: " + verbruikGas;
        output.setText(data);
    }


}



