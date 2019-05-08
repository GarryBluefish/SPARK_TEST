package com.manny.testSpark;

import com.manny.testSpark.Entities.DataPoint;
import com.manny.testSpark.Entities.ParsePoint;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

import static com.manny.testSpark.Calculating.GradientCalculate.getHypothetical;
import static com.manny.testSpark.Calculating.GradientCalculate.train;

public class Main {

    private static final double TOLERANCE = 1E-11;
    private static String logFile = "/home/manny/Документы/test.txt";
    private static int ITERATIONS = 5000;

    public static void main(String[] args) {

        SparkConf sparkConf = new SparkConf()
                .setAppName("GradientCalculate Spark App")
                .setMaster("local[4]");

        JavaSparkContext spark = new JavaSparkContext(sparkConf);
        JavaRDD<String> lines = spark.textFile(logFile).cache();

        JavaRDD<DataPoint> points = lines.map(new ParsePoint()).cache();

        double[] weight = train(points, TOLERANCE, ITERATIONS);

        double[] testData = {1.0, 90.0, 8100.0};
        System.out.println("FOR data " + Arrays.toString(testData) + " EXPECT " + getHypothetical(testData, weight));
    }
}
