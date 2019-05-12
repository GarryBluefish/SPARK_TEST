package com.manny.testSpark.Entities;

import java.io.Serializable;

public class TestPoint implements Serializable {

    private double[] x;

    public TestPoint(double[] x) {
        this.x = x;
    }

    public double[] getX() {
        return x;
    }

    public void setX(double[] x) {
        this.x = x;
    }
}
