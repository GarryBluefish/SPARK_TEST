package com.manny.testSpark;

import com.manny.testSpark.Calculating.GradientCalculate;
import com.manny.testSpark.Entities.DataPoint;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GradientTest {

    private JavaSparkContext sparkCtx;
    private double STEP = 1E-8;

    @Before
    public void init() throws IllegalArgumentException, IOException {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[2]");
        conf.setAppName("junit");
        sparkCtx = new JavaSparkContext(conf);
    }

    @Test
    public void calcGradientTest() {
        final List<DataPoint> nums = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            double[] x = {i};

            nums.add(new DataPoint(x, i * 2));
        }

        double[] weightExp = {2};
        double[] x = {3};

        JavaRDD<DataPoint> rdd = sparkCtx.parallelize(nums);

        double[] weightCalc = GradientCalculate.train(rdd, STEP, 10000);


        assertEquals(GradientCalculate.getHypothetical(x, weightExp), GradientCalculate.getHypothetical(x, weightCalc), 0.5);
        sparkCtx.stop();
    }
}
