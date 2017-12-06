package neuralnetworks;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.util.Random;

public class MatchMakerMain {

    final private String fileName;
    private Instances data;
    private Classifier classifier;

    public MatchMakerMain(String fileName) {
        this.fileName = fileName;
    }

    public static void main(String[] args) {
        try {
            MatchMakerMain matchMaker = new MatchMakerMain("matchmaker_fixed.arff");
            matchMaker.readData();
            matchMaker.train();
            matchMaker.test();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void readData() throws Exception {
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(fileName);
        data = source.getDataSet();
        data.setClassIndex(8);
    }

    private void train() throws Exception {
        classifier = new MultilayerPerceptron();
        classifier.buildClassifier(data);
    }

    private void test() throws Exception {
        Evaluation evaluation = new Evaluation(data);
        evaluation.crossValidateModel(classifier, data, 10, new Random(1));

        System.out.println(evaluation.toSummaryString());
        System.out.println(evaluation.toMatrixString());
    }
}