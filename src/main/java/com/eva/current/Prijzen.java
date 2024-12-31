package com.eva.current;

public class Prijzen {
    private final double stroomPrijs;
    private final double gasPrijs;

    public Prijzen(double stroomPrijs, double gasPrijs) {
        this.stroomPrijs = stroomPrijs;
        this.gasPrijs = gasPrijs;
    }

    public double getStroomPrijs() {
        return stroomPrijs;
    }

    public double getGasPrijs() {
        return gasPrijs;
    }
}
