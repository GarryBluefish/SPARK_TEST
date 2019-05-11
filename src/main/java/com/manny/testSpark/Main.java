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

    private static final double STEP = 1E-8;
    private static String dataFile = "/home/manny/Документы/test.txt";
    private static int ITERATIONS = 1000;
    private static double TOLERANCE = 1E-1;

    public static void main(String[] args) {

        /*if (args.length < 2) {
            System.err.println("Usage: JavaHdfsLR <file> <iters>");
            System.exit(1);
        }*/

        SparkConf sparkConf = new SparkConf()
                .setAppName("GradientCalculate Spark App")
                .setMaster("local[*]");

        JavaSparkContext spark = new JavaSparkContext(sparkConf);
        JavaRDD<String> lines = spark.textFile(dataFile).cache();

        JavaRDD<DataPoint> points = lines.map(new ParsePoint()).cache();

        double[] weight = train(points, STEP, ITERATIONS, TOLERANCE);

        double[] testData = {1.0, 90.0, 8100.0};
        System.out.println("FOR data " + Arrays.toString(testData) + " EXPECT " + getHypothetical(testData, weight));
    }
}
