package com.manny.testSpark;

import com.manny.testSpark.Calculating.Functions.HThetaFunc;
import com.manny.testSpark.Entities.DataPoint;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkException;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HThetaTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private JavaSparkContext sparkCtx;

    @Before
    public void init() throws IllegalArgumentException, IOException {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[2]");
        conf.setAppName("junit");
        sparkCtx = new JavaSparkContext(conf);
    }

    @Test
    public void calcHThetaTest() {
        final List<DataPoint> nums = new ArrayList<>();
        double[] x = {1, 2, 3};
        double[] weight = {1, 1, 1};
        nums.add(new DataPoint(x, 1));
        JavaRDD<DataPoint> rdd = sparkCtx.parallelize(nums).cache();
        rdd.foreach(new HThetaFunc(weight));

        assertEquals(6, rdd.collect().get(0).gethTheta(), 0);
        sparkCtx.stop();
    }

    @Test
    public void differentSizeTest() {
        final List<DataPoint> nums = new ArrayList<>();
        double[] x = {1, 2, 3};
        double[] weight = {1, 1};
        nums.add(new DataPoint(x, 1));


        //thrown.expect(ArrayIndexOutOfBoundsException.class);
        thrown.expect(SparkException.class);
        //thrown.expectMessage("Weight size != DataPoint size");
        JavaRDD<DataPoint> rdd = sparkCtx.parallelize(nums).cache();
        rdd.foreach(new HThetaFunc(weight));
        rdd.collect();

        sparkCtx.stop();
    }
}
