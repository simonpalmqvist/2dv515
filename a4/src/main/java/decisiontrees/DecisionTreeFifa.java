package decisiontrees;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.util.Random;

public class DecisionTreeFifa {

    final private String fileName;
    private Instances data;
    private Classifier classifier;

    public DecisionTreeFifa(String fileName) {
        this.fileName = fileName;
    }

    public static void main(String[] args) {
        try {
            DecisionTreeFifa nb = new DecisionTreeFifa("FIFA_skill.arff");
            nb.readData();
            nb.train();
            nb.test();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void readData() throws Exception {
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(fileName);
        data = source.getDataSet();
        data.setClassIndex(3);
    }

    public void train() throws Exception {
        classifier = new J48();
        classifier.buildClassifier(data);
    }

    public void test() throws Exception {
        Evaluation evaluation = new Evaluation(data);
        evaluation.crossValidateModel(classifier, data, 10, new Random(1));

        System.out.println(evaluation.toSummaryString());
        System.out.println(evaluation.toMatrixString());
    }
}
