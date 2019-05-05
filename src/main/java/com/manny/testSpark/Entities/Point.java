package com.manny.testSpark.Entities;

import org.apache.spark.api.java.function.Function;

import java.util.regex.Pattern;

public class Point implements Function<String, DataPoint> {

    private static final Pattern SPACE = Pattern.compile(" ");

    @Override
    public DataPoint call(String line) {
        String[] tok = SPACE.split(line);
        double y = Double.parseDouble(tok[0]);
        double[] x = new double[tok.length-1];

        for(int i = 0; i < tok.length-1; i++){
            x[i] = Double.parseDouble(tok[i+1]);
        }

        return new DataPoint(x, y);
    }
}
