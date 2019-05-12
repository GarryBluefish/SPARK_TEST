package com.manny.testSpark;

import com.manny.testSpark.Calculating.GradientCalculate;
import com.manny.testSpark.Entities.DataPoint;
import com.manny.testSpark.Entities.ExpData;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GradientTest {

    private JavaSparkContext sparkCtx;
    private double STEP = 1E-8;
    private double TOLERANCE = 1E-1;

    @Before
    public void init() throws IllegalArgumentException {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("junit");
        sparkCtx = new JavaSparkContext(conf);
    }

    @Test
    public void calcGradientTest() {
        final List<DataPoint> nums = new ArrayList<>();

        double[] x1 = {1.0, 90.0, 8100.0};
        double[] x2 = {1.0, 101.0, 10201.0};
        double[] x3 = {1.0, 103.0, 10609.0};
        nums.add(new DataPoint(x1, 249.0));
        nums.add(new DataPoint(x2, 338.0));
        nums.add(new DataPoint(x3, 304.0));

        JavaRDD<DataPoint> rdd = sparkCtx.parallelize(nums).cache();

        ExpData expData = GradientCalculate.train(rdd, STEP, 100, TOLERANCE);


        assertEquals(249, GradientCalculate.getHypothetical(x1, expData), 1.0);
        sparkCtx.stop();
    }
}
