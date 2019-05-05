package com.manny.testSpark;

import com.manny.testSpark.Calculating.GradientCalculate;
import com.manny.testSpark.Entities.DataPoint;
import com.manny.testSpark.Entities.Point;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

public class Main {

    private static String logFile = "/home/manny/Документы/test.txt";
    private static int ITERATIONS = 5000;
    private static final double TOLERANCE = 1E-11;

    public static void main(String[] args) {

        SparkConf sparkConf = new SparkConf()
                .setAppName("Gradient Spark App")
                .setMaster("local[*]");

        JavaSparkContext spark = new JavaSparkContext(sparkConf);
        JavaRDD<String> lines = spark.textFile(logFile).cache();

        JavaRDD<DataPoint> points = lines.map(new Point()).cache();
        int iterations = 0;

        double[] weight = new double[points.first().getX().length];
        for (int i = 0; i < weight.length; i++) weight[i] = 1;

        long size = points.count();

        do {
            iterations++;
            double[] temp = weight.clone();
            points.map(new GradientCalculate(weight)).foreach(dataPoint -> {
                System.out.println(dataPoint.getY() + " " + Arrays.toString(dataPoint.getX()) + " " + dataPoint.gethTheta());
            });
            double summ;
            for (int j = 0; j < weight.length; j++) {
                int finalJ = j;
                summ = points.map(dataPoint ->
                        (dataPoint.gethTheta() - dataPoint.getY()) * dataPoint.getX()[finalJ])
                .reduce((a, b) -> a + b);

                weight[j] -= TOLERANCE / size * summ;
            }

            if (tolerance(temp, weight, TOLERANCE)) {
                break;
            }

        } while (iterations < ITERATIONS);

        double[] testData = {1.0, 90.0, 8100.0};
        System.out.println("FOR data " +    Arrays.toString(testData) + " EXPECT " + getHipotethical(testData, weight));
    }

    private static boolean tolerance(double[] temp, double[] tetha, double TOLERANCE) {
        double summ = 0;
        for (int i = 0; i < temp.length; i++) {
            summ += (Math.abs(tetha[i]) - Math.abs(temp[i]));
        }
        return summ > TOLERANCE;
    }

    private static double getHipotethical(double[] x, double[] weight) {
        double hTheta = 0.0;
        for (int i = 0; i < x.length; i++) {
            hTheta += weight[i] * x[i];
            System.out.println(x[i]);
        }
        return hTheta;
    }
}
