package com.eva.current;

import javafx.scene.control.Label;

public class EnergieData {

    private final double jaarlijksVoorschot;
    private final double huidigeGasprijs;
    private final double huidigeStroomprijs;
    private final double verbruikStroom;
    private final double verbruikGas;

    public EnergieData(double jaarlijksVoorschot, double huidigeGasprijs, double huidigeStroomprijs, double verbruikStroom, double verbruikGas) {
        this.jaarlijksVoorschot = jaarlijksVoorschot;
        this.huidigeGasprijs = huidigeGasprijs;
        this.huidigeStroomprijs = huidigeStroomprijs;
        this.verbruikStroom = verbruikStroom;
        this.verbruikGas = verbruikGas;
    }

    public double getJaarlijksVoorschot() {
        return jaarlijksVoorschot;
    }

    public double getHuidigeGasprijs() {
        return huidigeGasprijs;
    }

    public double getHuidigeStroomprijs() {
        return huidigeStroomprijs;
    }

    public double getVerbruikStroom() {
        return verbruikStroom;
    }

    public double getVerbruikGas() {
        return verbruikGas;
    }






//    public void printData(Label output) {
//        String data = "Jaarlijks Voorschot: " + jaarlijksVoorschot + "\n" +
//                "Gasprijs: " + huidigeGasprijs + "\n" +
//                "Stroomprijs: " + huidigeStroomprijs
//                + "\n" + "Verbruik Stroom: " + verbruikStroom + "\n" + "Verbruik Gas: " + verbruikGas;
//        output.setText(data);
//    }

}



