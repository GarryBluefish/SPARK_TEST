package com.manny.testSpark.Calculating;

import com.manny.testSpark.Entities.DataPoint;
import org.apache.spark.api.java.function.VoidFunction;

public class HThetaCalculate implements VoidFunction<DataPoint> {
    private double[] theta;

    public HThetaCalculate(double[] theta) {
        this.theta = theta;
    }

    private double dot(double[] a, double[] b) {
        double x = 0;
        for (int i = 0; i < a.length; i++) {
            x += (a[i] * b[i]);
        }
        return x;
    }

    @Override
    public void call(DataPoint p) {
        if (theta.length != p.getX().length) throw new ArrayIndexOutOfBoundsException("Weight size != DataPoint size");
        p.sethTheta(dot(theta, p.getX()));
    }
}
