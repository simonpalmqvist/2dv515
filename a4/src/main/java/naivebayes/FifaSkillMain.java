package naivebayes;

import naivebayes.core.Evaluator;

import java.io.File;

public class FifaSkillMain {

    public static void main(String[] args) {
        File file = new File("src/main/resources/FIFA_skill_nominal.arff");
        String absolutePath = file.getAbsolutePath();
        System.out.println(absolutePath);
        Evaluator evaluator = new Evaluator(new NaiveBayesClassifier(), absolutePath);

        evaluator.evaluateWholeSet();
        evaluator.evaluateCV();
    }
}
