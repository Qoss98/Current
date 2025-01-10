package com.eva.current;

public class Prijzen {
    private double stroomPrijs;
    private double gasPrijs;
    double voorschot;

//    public Prijzen() {
//        this.stroomPrijs = stroomPrijs;
//        this.gasPrijs = gasPrijs;
//    }

    public Prijzen(double stroomPrijs, double gasPrijs, double voorschot) {
        this.stroomPrijs = stroomPrijs;
        this.gasPrijs = gasPrijs;
        this.voorschot = voorschot;
    }

    public double getStroomPrijs() {
        return stroomPrijs;
    }

    public double getGasPrijs() {
        return gasPrijs;
    }

    public double getVoorschot() {
        return voorschot;
    }
}
