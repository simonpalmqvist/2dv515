package naivebayes;

import naivebayes.core.*;

import java.util.HashMap;
import java.util.Map;

public class NaiveBayesClassifier implements Classifier {

    private Map<String, Map<String, Double>> aggAttributes = new HashMap<>();
    private Map<String, Double> aggClassAttributes = new HashMap<>();
    private double numOfInstances;


    public void train(Dataset train) {

        for(int i = 0; i < train.noAttributes(); i++) {
            aggAttributes.put(train.getAttributeName(i), new HashMap<>());
        }

        train.getDistinctClassValues().getNominalValues().forEach(nom -> aggClassAttributes.put(nom, 0.0));

        numOfInstances = train.noInstances();

        for(int i = 0; i < train.noInstances(); i++) {
            Instance instance = train.getInstance(i);

            String classNom = instance.getClassAttribute().nominalValue();
            aggClassAttributes.put(classNom, aggClassAttributes.get(classNom) + 1.0);

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
        Map<String, Double> probablility = new HashMap<>();

        for(Map.Entry<String, Double> classAttr : aggClassAttributes.entrySet()) {
            probablility.put(classAttr.getKey(), classAttr.getValue() / numOfInstances);

            for(int i = 0; i < inst.noAttributes(); i++) {
                Attribute attr = inst.getAttribute(i);
                Double p = aggAttributes.get(inst.getAttributeName(i)).get(attr.nominalValue()) / classAttr.getValue();

                probablility.put(classAttr.getKey(), p * probablility.get(classAttr.getKey()));
            }
        }

        return new Result(
                probablility
                        .entrySet()
                        .stream()
                        .max((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                        .get()
                        .getKey()
        );
    }
}
