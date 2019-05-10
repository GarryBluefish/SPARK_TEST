package com.manny.testSpark.Entities;

import java.io.Serializable;

public class DataPoint implements Serializable {

    private double[] x;
    private double y;
    private double hTheta;

    public DataPoint(double[] x, double y) {
        this.x = x;
        this.y = y;
        this.hTheta = 0;
    }

    public double[] getX() {
        return x;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double gethTheta() {
        return hTheta;
    }

    public void sethTheta(double hTheta) {
        this.hTheta = hTheta;
    }
}
