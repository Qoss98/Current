package com.eva.current;

import javafx.scene.control.Label;

public class EnergieData {
    private double jaarlijksVoorschot;
    private double huidigeGasprijs;
    private double huidigeStroomprijs;



    public void setJaarlijksVoorschot(double jaarlijksVoorschot) {
        this.jaarlijksVoorschot = jaarlijksVoorschot;
    }

    public void setHuidigeStroomprijs(double huidigeStroomprijs) {
        this.huidigeStroomprijs = huidigeStroomprijs;
    }

    public void setHuidigeGasprijs(double huidigeGasprijs) {
        this.huidigeGasprijs = huidigeGasprijs;
    }

    public void printData(Label output) {
        String data = "Jaarlijks Voorschot: " + jaarlijksVoorschot + "\n" +
                "Gasprijs: " + huidigeGasprijs + "\n" +
                "Stroomprijs: " + huidigeStroomprijs;
        output.setText(data);
    }
}



