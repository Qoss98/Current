package com.eva.current;

import javafx.scene.control.Label;

public class EnergieData {

    private final double verbruikStroom;
    private final double verbruikGas;

    public EnergieData(double verbruikStroom, double verbruikGas) {

        this.verbruikStroom = verbruikStroom;
        this.verbruikGas = verbruikGas;
    }

    public double getVerbruikStroom() {
        return verbruikStroom;
    }

    public double getVerbruikGas() {
        return verbruikGas;
    }

}



