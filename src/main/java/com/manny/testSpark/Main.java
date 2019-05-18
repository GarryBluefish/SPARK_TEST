package com.manny.testSpark;

import com.manny.testSpark.Entities.DataPoint;
import com.manny.testSpark.Entities.ExpData;
import com.manny.testSpark.Entities.ParsePoint;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

import static com.manny.testSpark.Calculating.GradientCalculate.getHypothetical;
import static com.manny.testSpark.Calculating.GradientCalculate.train;

public class Main {

    public static void main(String[] args) {

        JavaRDD<DataPoint> points;
        List<DataPoint> origin;
        double[] weight = null;
        double TOLERANCE = 1E-1;
        double STEP = 1e-15;
        int ITERATIONS;

        if (args.length < 4) {
            System.err.println("Usage:<generate> <count> <size> <iters>");
            System.exit(1);
        }

        SparkConf sparkConf = new SparkConf()
                .setAppName("GradientCalculate Spark App")
                .setMaster("local[*]");

        JavaSparkContext spark = new JavaSparkContext(sparkConf);

        STEP = Double.parseDouble(args[2]);
        ITERATIONS = Integer.parseInt(args[2]);
        JavaRDD<String> lines = spark.textFile(args[1]).cache();
        points = lines.map(new ParsePoint()).cache();
        origin = points.collect();

        ExpData expData = train(points, STEP, ITERATIONS, TOLERANCE);

        List<DataPoint> collect = points.cache().collect();
        printer(weight, expData, collect, origin);
    }

    private static void printer(double[] weight, ExpData expData, List<DataPoint> points, List<DataPoint> original) {
        System.out.print("Generated formula:\nY = ");
        for (int i = 0; i < weight.length; i++) {
            System.out.print(weight[i] + "*X" + i);
            if (i < weight.length - 1) {
                System.out.print(" + ");
            }
        }

        System.out.println("\n");

        double minY = expData.getMaxMinValue().getMin()[0];
        double maxY = expData.getMaxMinValue().getMax()[0];


        for (int i = 0; i < points.size(); i++) {
            double denormalizeY = points.get(i).getY() * (maxY - minY) + minY;
            double a = getHypothetical(original.get(i).getX(), expData);

            double exp = 0;
            for (double v : weight) {
                exp += v * i;
            }
            System.out.println("X = " + Arrays.toString(original.get(i).getX()));
            System.out.println("With Noise Y = " + denormalizeY + "; Expect Y =  " + exp + "; Calc Y = " + a + "\n");
        }
    }
}
