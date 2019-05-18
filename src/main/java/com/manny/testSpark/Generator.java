package com.manny.testSpark;

import com.manny.testSpark.Entities.DataPoint;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.Random;

public class Generator {

    public static double[] generateWeight(int count) {
        Random r = new Random();

        double[] weight = new double[count];
        for (int i = 0; i < weight.length; i++) {
            weight[i] = (double) Math.round((r.nextGaussian() + r.nextInt(10) - 5) * 1000) / 1000;
        }

        if (count > 2) {
            weight[r.nextInt(count)] += 100;
        }

        return weight;
    }

    public static JavaRDD<DataPoint> generateData(int size, double[] weight, JavaSparkContext context) {
        Random r = new Random();

        ArrayList<DataPoint> dataPoints = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            //DataPoint d = new DataPoint();
            double[] x = new double[weight.length];
            double summ = 0;
            for (int j = 0; j < weight.length; j++) {
                x[j] = i;
                summ += weight[j] * i;
            }
            DataPoint d = new DataPoint(x, summ + r.nextInt(6) - 3);
            dataPoints.add(d);
        }

        return context.parallelize(dataPoints)/*.cache()*/;
    }
}
