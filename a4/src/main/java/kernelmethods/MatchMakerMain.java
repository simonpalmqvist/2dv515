package kernelmethods;

import core.Evaluator;

import java.io.File;

public class MatchMakerMain {

    public static void main(String[] args) {
        File file = new File("src/main/resources/matchmaker_fixed.arff");
        String absolutePath = file.getAbsolutePath();

        Evaluator evaluator = new Evaluator(new KernelMethodClassifier(), absolutePath);

        evaluator.evaluateWholeSet();
    }

}