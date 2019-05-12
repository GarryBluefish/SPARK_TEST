package com.manny.testSpark.Calculating.Сomaprators;

import com.manny.testSpark.Entities.DataPoint;

import java.io.Serializable;
import java.util.Comparator;

public class ComparatorX implements Serializable, Comparator<DataPoint> {

    private int j;

    public ComparatorX(int j) {
        this.j = j;
    }

    @Override
    public int compare(DataPoint a, DataPoint b) {
        if (a.getX()[j] < b.getX()[j]) return -1;
        else if (a.getX()[j] > b.getX()[j]) return 1;
        return 0;
    }
}
