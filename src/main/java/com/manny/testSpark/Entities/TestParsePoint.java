package com.manny.testSpark.Entities;

import org.apache.spark.api.java.function.Function;

public class TestParsePoint implements Function<String, TestPoint> {

    @Override
    public TestPoint call(String s) throws Exception {
        String[] tok = s.split(" ");
        double[] x = new double[tok.length];

        for (int i = 0; i < tok.length; i++) {
            x[i] = Double.parseDouble(tok[i]);
        }

        return new TestPoint(x);
    }
}
