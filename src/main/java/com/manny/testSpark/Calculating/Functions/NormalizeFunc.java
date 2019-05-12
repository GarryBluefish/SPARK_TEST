package com.manny.testSpark.Calculating.Functions;

import com.manny.testSpark.Entities.DataPoint;
import org.apache.spark.api.java.function.VoidFunction;

/**
 * Функция нормализации значений
 */
public class NormalizeFunc implements VoidFunction<DataPoint> {

    private double[] max;
    private double[] min;

    public NormalizeFunc(double[] max, double[] min) {
        this.max = max;
        this.min = min;
    }

    @Override
    public void call(DataPoint dataPoint) throws Exception {
        dataPoint.setY((dataPoint.getY() - min[0]) / (max[0] - min[0]));
        for (int i = 0; i < dataPoint.getX().length; i++) {
            dataPoint.getX()[i] = (dataPoint.getX()[i] - min[i + 1]) / (max[i + 1] - min[i + 1]);
        }

    }
}
