package com.manny.testSpark.Calculating;

import com.manny.testSpark.Entities.DataPoint;
import org.apache.spark.api.java.JavaRDD;

public class GradientCalculate {

    public static double[] train(JavaRDD<DataPoint> points, double TOLERANCE, int ITERATIONS) {
        int iterations = 0;

        double[] weight = new double[points.first().getX().length];
        for (int i = 0; i < weight.length; i++) weight[i] = 1;

        long size = points.count();

        do {
            iterations++;
            double[] temp = weight.clone();
            double summ;


            points.map(new HThetaCalculate(weight));
            for (int j = 0; j < weight.length; j++) {
                int finalJ = j;
                summ = points.map(dataPoint ->
                        (dataPoint.gethTheta() - dataPoint.getY()) * dataPoint.getX()[finalJ])
                        .reduce((a, b) -> a + b);

                weight[j] -= TOLERANCE / size * summ;
            }

            if (isTolerance(temp, weight, TOLERANCE)) {
                break;
            }

        } while (iterations < ITERATIONS);

        return weight;
    }

    private static boolean isTolerance(double[] temp, double[] theta, double TOLERANCE) {
        double summ = 0;
        for (int i = 0; i < temp.length; i++) {
            summ += (Math.abs(theta[i]) - Math.abs(temp[i]));
        }
        return summ > TOLERANCE;
    }

    public static double getHypothetical(double[] x, double[] weight) {
        double hTheta = 0.0;
        for (int i = 0; i < x.length; i++) {
            hTheta += weight[i] * x[i];
        }
        return hTheta;
    }
}
