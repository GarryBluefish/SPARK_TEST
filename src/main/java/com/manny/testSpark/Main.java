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

    public static void main(String[] args) {

        if (args.length < 4) {
            System.err.println("Usage: <file> <iters> <step> <testData>");
            System.exit(1);
        }

        double STEP = Double.parseDouble(args[2]); //1E-8
        int ITERATIONS = Integer.parseInt(args[1]); //5000
        double TOLERANCE = 1E-1;

        double[] testData = new double[args.length - 3];
        for (int i = 3; i < args.length; i++) {
            testData[i - 4] = Double.parseDouble(args[i]);
        }

        SparkConf sparkConf = new SparkConf()
                .setAppName("GradientCalculate Spark App")
                .setMaster("local[*]");

        JavaSparkContext spark = new JavaSparkContext(sparkConf);
        JavaRDD<String> lines = spark.textFile(args[0]).cache();

        JavaRDD<DataPoint> points = lines.map(new ParsePoint()).cache();

        double[] weight = train(points, STEP, ITERATIONS, TOLERANCE);

        System.out.println("FOR data " + Arrays.toString(testData) + " EXPECT " + getHypothetical(testData, weight));
    }
}
