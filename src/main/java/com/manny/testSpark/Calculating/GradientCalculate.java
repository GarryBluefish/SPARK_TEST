package com.manny.testSpark.Calculating;

import com.manny.testSpark.Entities.DataPoint;
import org.apache.spark.api.java.JavaRDD;

public class GradientCalculate {

    public static double[] train(JavaRDD<DataPoint> points, double step, int iterations, double tolerance) {

        double[] weight = new double[points.first().getX().length];
        for (int i = 0; i < weight.length; i++) {
            weight[i] = 1;
        }

        long size = points.count();

        for (int i = 0; i < iterations; i++) {

            points.foreach(new HThetaCalculate(weight));
            for (int j = 0; j < weight.length; j++) {
                int finalJ = j;
                double summ = points.map(dataPoint ->
                        (dataPoint.gethTheta() - dataPoint.getY()) * dataPoint.getX()[finalJ])
                        .reduce((a, b) -> a + b);

                weight[j] -= step / size * summ;
            }

            DataPoint temp = points.first();

            if (isTolerance(temp, weight, tolerance)) {
                break;
            }
        }

        return weight;
    }

    private static boolean isTolerance(DataPoint temp, double[] theta, double tolerance) {
        double y = getHypothetical(temp.getX(), theta);
        return Math.abs(y) - Math.abs(temp.getY()) < tolerance;
    }

    public static double getHypothetical(double[] x, double[] weight) {
        double hTheta = 0.0;
        for (int i = 0; i < x.length; i++) {
            hTheta += weight[i] * x[i];
        }
        return hTheta;
    }
}
