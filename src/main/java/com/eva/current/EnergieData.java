package com.eva.current;

import javafx.scene.control.Label;

public class EnergieData {
    private double jaarlijksVoorschot;
    private double huidigeGasprijs;
    private double huidigeStroomprijs;

    private double verbruikStroom;
    private double verbruikGas;



    public void setJaarlijksVoorschot(double jaarlijksVoorschot) {
        this.jaarlijksVoorschot = jaarlijksVoorschot;
    }

    public void setHuidigeStroomprijs(double huidigeStroomprijs) {
        this.huidigeStroomprijs = huidigeStroomprijs;
    }

    public void setHuidigeGasprijs(double huidigeGasprijs) {
        this.huidigeGasprijs = huidigeGasprijs;
    }

    public void setVerbruikStroom(double verbruikStroom) {
        this.verbruikStroom = verbruikStroom;
    }

    public void setVerbruikGas(double verbruikGas) {
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



