package com.manny.testSpark.Entities;

import java.io.Serializable;

public class ExpData implements Serializable {

    private double[] weight;
    private MaxMinValue maxMinValue;

    public ExpData(double[] weight, MaxMinValue maxMinValue) {
        this.weight = weight;
        this.maxMinValue = maxMinValue;
    }

    public double[] getWeight() {
        return weight;
    }

    public void setWeight(double[] weight) {
        this.weight = weight;
    }

    public MaxMinValue getMaxMinValue() {
        return maxMinValue;
    }

    public void setMaxMinValue(MaxMinValue maxMinValue) {
        this.maxMinValue = maxMinValue;
    }
}
