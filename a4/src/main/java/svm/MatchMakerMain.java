package svm;

import core.*;
import libsvm.*;

import java.io.File;

public class MatchMakerMain implements Classifier {

    private Dataset data;
    private svm_model model;

    public static void main(String[] args) {
        File file = new File("src/main/resources/matchmaker_fixed.arff");
        String absolutePath = file.getAbsolutePath();

        Evaluator evaluator = new Evaluator(new MatchMakerMain(), absolutePath);

        evaluator.evaluateWholeSet();
    }

    @Override
    public void train(Dataset train) {
        data = train;

        int noInstances = data.noInstances();

        svm_problem problem = new svm_problem();

        problem.y = new double[noInstances];
        problem.l = noInstances;
        problem.x = new svm_node[noInstances][data.noAttributes() -1]; // Can be removed?

        for(int i = 0; i < data.noInstances(); i++) {
            Instance instance = data.getInstance(i);

            double[] values = instance.getAttributeArrayNumerical();
            problem.x[i] = new svm_node[data.noAttributes() -1];


            for(int a = 0; a < data.noAttributes() -1; a++) {
                svm_node node = new svm_node();
                node.index = a;
                node.value = values[a];
                problem.x[i][a] = node;
            }

            problem.y[i] = instance.getClassAttribute().numericalValue();
        }

        svm_parameter parameter = new svm_parameter();

        parameter.probability = 1;
        parameter.gamma = 0.5;
        parameter.nu = 0.5;
        parameter.C = 100;
        parameter.svm_type = svm_parameter.C_SVC;
        parameter.kernel_type = svm_parameter.RBF;
        parameter.cache_size = 20000;
        parameter.eps = 0.001;

        model = svm.svm_train(problem, parameter);
    }

    @Override
    public Result classify(Instance inst) {
        int numberOfClasses = data.noClassValues();

        double[] values = inst.getAttributeArrayNumerical();

        svm_node[] nodes = new svm_node[values.length];

        for(int a = 0; a < inst.noAttributes() -1; a++) {
            svm_node node = new svm_node();
            node.index = a;
            node.value = values[a];
            nodes[a] = node;
        }

        int[] labels = new int[numberOfClasses];
        double[] problemEstimates = new double[numberOfClasses];

        svm.svm_get_labels(model, labels);

        double classifyValue = svm.svm_predict_probability(model, nodes, problemEstimates);

        return new Result(classifyValue);
    }
}
