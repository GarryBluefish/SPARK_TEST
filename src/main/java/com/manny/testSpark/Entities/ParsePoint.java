package com.manny.testSpark.Entities;

import org.apache.spark.api.java.function.Function;

public class ParsePoint implements Function<String, DataPoint> {

    @Override
    public DataPoint call(String line) {
        String[] tok = line.split(" ");
        double y = Double.parseDouble(tok[0]);
        double[] x = new double[tok.length - 1];

        for (int i = 0; i < tok.length - 1; i++) {
            x[i] = Double.parseDouble(tok[i + 1]);
        }

        return new DataPoint(x, y);
    }
}
