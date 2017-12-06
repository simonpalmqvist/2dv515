package kernelmethods;

import core.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KernelMethodClassifier implements Classifier {

    private Dataset data;
    private double offset;


    public void train(Dataset train) {
        data = train;

        ArrayList<Instance> list0 = new ArrayList<>();
        ArrayList<Instance> list1 = new ArrayList<>();

        for(Instance i : data.toList()) {
            double numValue = i.getClassAttribute().numericalValue();

            if(numValue == 0.0) list0.add(i);
            else if(numValue == 1.0) list1.add(i);
        }

        double sum0 = sumOfRBFValues(list0);
        double sum1 = sumOfRBFValues(list1);

        offset = (1.0 / Math.pow(list1.size(), 2)) * sum1 - (1.0 / Math.pow(list0.size(), 2)) * sum0;
    }

    public Result classify(Instance inst) {

        double[] sum = new double[2];
        double[] count = new double[2];
        double[] point = inst.getAttributeArrayNumerical();

        for(Instance t : data.toList()) {
            double[] values = t.getAttributeArrayNumerical();
            int index = (int) t.getClassAttribute().numericalValue();

            sum[index] += rbf(point, values);
            count[index]++;
        }

        double y = (1.0 / count[0]) * sum[0] - (1.0 / count[1]) * sum[1] + offset;

        Result result = new Result(y > 0 ? 0 : 1);

        return result;

    }

    private double sumOfRBFValues(List<Instance> list) {
        double sum = 0;

        for(int i = 0; i < list.size(); i++) {
            double[] values0 = list.get(i).getAttributeArrayNumerical();

            for(int j = 0; j < list.size(); j++) {
                double[] values1 = list.get(j).getAttributeArrayNumerical();

                sum += rbf(values0, values1);
            }
        }

        return sum;
    }

    private double rbf(double[] values0, double[] values1) {
        double gamma = 20.0;
        double squareDistance = 0;

        for(int i = 0; i < values0.length; i++) {
            squareDistance += Math.pow(values0[i] - values1[i], 2);
        }

        return Math.pow(Math.E, -gamma * squareDistance);
    }
}
