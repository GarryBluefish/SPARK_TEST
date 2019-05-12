package com.manny.testSpark;

import com.manny.testSpark.Entities.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

import static com.manny.testSpark.Calculating.GradientCalculate.getHypothetical;
import static com.manny.testSpark.Calculating.GradientCalculate.train;

public class Main {

    public static void main(String[] args) {

        if (args.length < 4) {
            System.err.println("Usage: <file> <iters> <step> <testData>");
            System.exit(1);
        }

        double STEP = Double.parseDouble(args[2]); //1E-8
        int ITERATIONS = Integer.parseInt(args[1]); //100
        double TOLERANCE = 1E-8;

        SparkConf sparkConf = new SparkConf()
                .setAppName("GradientCalculate Spark App")
                .setMaster("local[*]");

        JavaSparkContext spark = new JavaSparkContext(sparkConf);
        JavaRDD<String> lines = spark.textFile(args[0]).cache();
        TestPoint testData = spark.textFile(args[3]).map(new TestParsePoint()).collect().get(0);

        JavaRDD<DataPoint> points = lines.map(new ParsePoint()).cache();
        points.foreach(dataPoint -> System.out.println(dataPoint.getY()));

        ExpData expData = train(points, STEP, ITERATIONS, TOLERANCE);

        System.out.println("FOR data " + Arrays.toString(testData.getX()) + " EXPECT " + getHypothetical(testData.getX(), expData));
    }
}
