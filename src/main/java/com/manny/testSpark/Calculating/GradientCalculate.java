package com.manny.testSpark.Calculating;

import com.manny.testSpark.Calculating.Functions.HThetaFunc;
import com.manny.testSpark.Calculating.Functions.NormalizeFunc;
import com.manny.testSpark.Calculating.Сomaprators.ComparatorX;
import com.manny.testSpark.Calculating.Сomaprators.ComparatorY;
import com.manny.testSpark.Entities.DataPoint;
import com.manny.testSpark.Entities.ExpData;
import com.manny.testSpark.Entities.MaxMinValue;
import org.apache.spark.api.java.JavaRDD;

public class GradientCalculate {

    public static ExpData train(JavaRDD<DataPoint> points, double step, int iterations, double tolerance) {

        double[] weight = new double[points.first().getX().length];
        for (int i = 0; i < weight.length; i++) {
            weight[i] = 1;
        }

        long size = points.count();

        // Вычисляем максимальные и минимальные значения Х
        MaxMinValue maxMinValue = getMaxMin(points, weight.length);

        // Нормализация значений Х (от 0 до 1)
        points.foreach(new NormalizeFunc(maxMinValue.getMax(), maxMinValue.getMin()));

        for (int i = 0; i < iterations; i++) {

            // вычисление h(theta)
            points.foreach(new HThetaFunc(weight));

            // вычисление новых theta
            for (int j = 0; j < weight.length; j++) {
                int finalJ = j;
                double summ = points.map(dataPoint ->
                        (dataPoint.gethTheta() - dataPoint.getY()) * dataPoint.getX()[finalJ])
                        .reduce((a, b) -> a + b);

                weight[j] -= (step / size * summ);
            }

            DataPoint temp = points.first();

            ExpData expData = new ExpData(weight, maxMinValue);
            /*if (isTolerance(temp, expData, tolerance)) {
                return expData;
            }*/
        }

        return new ExpData(weight, maxMinValue);
    }

    // проверка разницы между значением Y данных и значения Y предсказанного
    private static boolean isTolerance(DataPoint temp, ExpData expData, double tolerance) {
        double y = getHypotheticalWithoutNorm(temp.getX(), expData.getWeight());
        return Math.abs(y) - Math.abs(temp.getY()) < tolerance;
    }

    // вычисление значения функции без нормализации
    public static double getHypotheticalWithoutNorm(double[] x, double[] weight) {
        double hTheta = 0.0;
        for (int i = 0; i < x.length; i++) {
            hTheta += weight[i] * x[i];
        }
        return hTheta;
    }

    // вычисление значения функции c нормализацией
    public static double getHypothetical(double[] x, ExpData expData) {
        double hTheta = 0.0;
        MaxMinValue maxMinValue = expData.getMaxMinValue();
        for (int i = 0; i < x.length; i++) {
            hTheta += expData.getWeight()[i] * (x[i] - maxMinValue.getMin()[i + 1]) / (maxMinValue.getMax()[i + 1] - maxMinValue.getMin()[i + 1]);
        }
        return (hTheta * (maxMinValue.getMax()[0] - maxMinValue.getMin()[0]) + maxMinValue.getMin()[0]);
    }

    private static MaxMinValue getMaxMin(JavaRDD<DataPoint> points, int size) {
        MaxMinValue maxMinValue = new MaxMinValue();
        maxMinValue.setMax(new double[size + 1]);
        maxMinValue.setMin(new double[size + 1]);

        maxMinValue.getMax()[0] = points.max(new ComparatorY()).getY();
        maxMinValue.getMin()[0] = points.min(new ComparatorY()).getY();

        for (int j = 0; j < size; j++) {
            maxMinValue.getMax()[j + 1] = points.max(new ComparatorX(j)).getX()[j];
            maxMinValue.getMin()[j + 1] = points.min(new ComparatorX(j)).getX()[j];
        }

        return maxMinValue;
    }
}
