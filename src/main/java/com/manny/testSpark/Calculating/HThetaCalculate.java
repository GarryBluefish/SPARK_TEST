package com.manny.testSpark.Calculating;

import com.manny.testSpark.Entities.DataPoint;
import org.apache.spark.api.java.function.Function;

public class HThetaCalculate implements Function<DataPoint, DataPoint> {
    private double[] theta;

    public HThetaCalculate(double[] theta) {
        this.theta = theta;
    }

    @Override
    public DataPoint call(DataPoint p) {
        if (theta.length != p.getX().length) throw new ArrayIndexOutOfBoundsException("Weight size != DataPoint size");
        double hTheta = dot(theta, p.getX());
        p.sethTheta(hTheta);
        return p;
    }

    private double dot(double[] a, double[] b) {
        double x = 0;
        for (int i = 0; i < a.length; i++) {
            x += (a[i] * b[i]);
        }
        return x;
    }
}
