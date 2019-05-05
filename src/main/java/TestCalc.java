import java.util.ArrayList;

public class TestCalc {

    public static final double TOLERANCE = 1E-11;

    public static ArrayList<Double> arrayTetha = new ArrayList<>(); //на 1 больше чем xArray
    public static ArrayList<Double[]> dataset = new ArrayList<>();
    public static ArrayList<Double> arrayY = new ArrayList<>();
    public static double step = 0.0001;

    public static int iterator = 0;
    public static void test() {
        /*dataset.add(new Double[]{ 200.0 }); // feature vector 1
        dataset.add(new Double[]{ 300.0 }); // feature vector 2
        dataset.add(new Double[]{ 900.0 });
        dataset.add(new Double[]{ 800.0 });
        dataset.add(new Double[]{ 400.0 });
        dataset.add(new Double[]{ 500.0 });*/

        dataset.add(new Double[] { 1.0, 90.0, 8100.0 }); // feature vector of house#1
        dataset.add(new Double[] { 2.0, 101.0, 10201.0 }); // feature vector of house#2
        dataset.add(new Double[] { 3.0, 103.0, 10609.0 });

        /*arrayY.add(2000.0);
        arrayY.add(3000.0);
        arrayY.add(9000.0);
        arrayY.add(8000.0);
        arrayY.add(4000.0);
        arrayY.add(5000.0);*/

        arrayY.add(249.0); // price label of house#1
        arrayY.add(338.0); // price label of house#2
        arrayY.add(304.0);

        for (int i = 0; i < dataset.get(0).length; i++) {
            arrayTetha.add(1.0);
        }
    }

    public static void train() {
        test();
        ArrayList<Double> temp = new ArrayList<>();

        do {
            iterator++;
            for (int j = 0; j < dataset.get(0).length; j++) {
                ArrayList<Double> hTheta = new ArrayList<>();
                double summ = 0.0;
                for (int i = 0; i < dataset.size(); i++) {
                    hTheta.add(0.0);
                    for (int j1 = 0; j1 < dataset.get(0).length; j1++) {
                        hTheta.set(i, hTheta.get(i) + (arrayTetha.get(j1) * dataset.get(i)[j1]));
                    }
                    summ += (hTheta.get(i) - arrayY.get(i)) * dataset.get(i)[j];
                }

                arrayTetha.set(j, arrayTetha.get(j) - TOLERANCE / dataset.size() * summ);
            }

            if(tolerance(temp, arrayTetha, TOLERANCE)) {
                System.out.println("breaker");
                break;
            }
            temp.clear();

        } while (iterator <= 5000);

    }



    public static double getHipotethic(Double[] x) {
        double hTheta = 0.0;
        for (int i = 0; i < x.length; i++) {
            hTheta += arrayTetha.get(i) * x[i];
            System.out.println(arrayTetha.get(i));
            System.out.println(x[i]);
            System.out.println();
        }
        return  hTheta;
    }

    public static boolean tolerance(ArrayList<Double> temp, ArrayList<Double> tetha, double TOLERANCE) {
        double summ = 0;
        for (int i = 0; i < temp.size(); i ++){
            summ += (Math.abs(tetha.get(i)) - Math.abs(temp.get(i)));
        }
        return summ > TOLERANCE;
    }


    public static double summ(ArrayList<Double> temp) {
        double summ = 0;
        for (Double aDouble : temp) {
            summ += aDouble;
        }
        return summ;
    }

    public static void main(String[] args) {
        train();
        System.out.println(iterator);
        System.out.println(getHipotethic(new Double[]{ 1.0, 90.0, 8100.0 }));
    }
}
