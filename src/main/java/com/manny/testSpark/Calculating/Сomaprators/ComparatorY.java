package com.manny.testSpark.Calculating.Сomaprators;

import com.manny.testSpark.Entities.DataPoint;

import java.io.Serializable;
import java.util.Comparator;

public class ComparatorY implements Serializable, Comparator<DataPoint> {

    @Override
    public int compare(DataPoint a, DataPoint b) {
        if (a.getY() < b.getY()) return -1;
        else if (a.getY() > b.getY()) return 1;
        return 0;
    }
}
