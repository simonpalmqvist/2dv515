package naivebayes;

import core.*;

import java.util.HashMap;
import java.util.Map;

public class NaiveBayesClassifier implements Classifier {

    private Map<String, Map<String, Double>> aggAttributes = new HashMap<>();
    private Map<String, Double> aggClassAttributes = new HashMap<>();
    private double numOfInstances;


    public void train(Dataset train) {

        // Store each attribute name in map
        for(int i = 0; i < train.noAttributes(); i++) {
            aggAttributes.put(train.getAttributeName(i), new HashMap<>());
        }

        // Store each class attribute value
        train.getDistinctClassValues().getNominalValues().forEach(nom -> aggClassAttributes.put(nom, 0.0));

        // Store number of instances
        numOfInstances = train.noInstances();

        for(int i = 0; i < train.noInstances(); i++) {
            Instance instance = train.getInstance(i);

            String classNom = instance.getClassAttribute().nominalValue();

            // Increment classAttribute value that matches instance class attribute value
            aggClassAttributes.put(classNom, aggClassAttributes.get(classNom) + 1.0);

            // For each attribute on instance aggregate value
            for(int j = 0; j < instance.noAttributes(); j++) {
                Attribute attr = instance.getAttribute(j);
                String nom = attr.nominalValue();
                Map<String, Double> aggValue = aggAttributes.get(instance.getAttributeName(j));

                if(aggValue.containsKey(nom)) {
                    aggValue.put(nom, aggValue.get(nom) + 1.0);
                } else {
                    aggValue.put(nom, 1.0);
                }
            }
        }
    }

    public Result classify(Instance inst) {
        Map<String, Double> probability = new HashMap<>();

        // Go through all class attributes
        for(Map.Entry<String, Double> classAttr : aggClassAttributes.entrySet()) {
            probability.put(classAttr.getKey(), classAttr.getValue() / numOfInstances); // Add frequency of class attribute

            for(int i = 0; i < inst.noAttributes(); i++) {
                Attribute attr = inst.getAttribute(i);

                // Get frequency for  attribute to class attribute
                Double p = aggAttributes.get(inst.getAttributeName(i)).get(attr.nominalValue()) / classAttr.getValue();

                // Multiply frequency with probability for class attribute
                probability.put(classAttr.getKey(), p * probability.get(classAttr.getKey()));
            }
        }

        // Return the class attribute nominal value with highest probability
        return new Result(
                probability
                        .entrySet()
                        .stream()
                        .max((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                        .get()
                        .getKey()
        );
    }
}
