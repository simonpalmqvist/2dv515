package naivebayes;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.util.Random;

public class NaiveBayesWiki {

    final private String fileName;
    private Instances data;
    private Classifier classifier;


    public NaiveBayesWiki(String fileName) {
        this.fileName = fileName;
    }

    public static void main(String[] args) {
        try {
            NaiveBayesWiki nb = new NaiveBayesWiki("wikipedia_70.arff");
            nb.readData();
            nb.train();
            nb.test();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void readData() throws Exception {
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(fileName);
        Instances rawData = source.getDataSet();

        StringToWordVector words = new StringToWordVector(10000);
        words.setLowerCaseTokens(true);
        words.setInputFormat(rawData);

        data = Filter.useFilter(rawData, words);
        data.setClassIndex(0);
    }

    public void train() throws Exception {
        classifier = new NaiveBayesMultinomial();
        classifier.buildClassifier(data);
    }

    public void test() throws Exception {
        Evaluation evaluation = new Evaluation(data);
        evaluation.crossValidateModel(classifier, data, 10, new Random(1));

        System.out.println(evaluation.toSummaryString());
        System.out.println(evaluation.toMatrixString());
    }
}
