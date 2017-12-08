package project.recommendations;

import java.util.Map;

public class Pearson {

    public static double calculateScore(Map<String, Double> a, Map<String, Double> b) {
        double sumA = 0;
        double sumB = 0;
        double sumAsq = 0;
        double sumBsq = 0;
        double pSum = 0;
        double counter = 0;

        for(String id : a.keySet()) {
            double aValue = a.get(id);

            if(b.containsKey(id)) {
                double bValue = b.get(id);

                sumA += aValue;
                sumB += bValue;
                sumAsq += Math.pow(aValue, 2);
                sumBsq += Math.pow(bValue, 2);
                pSum += aValue * bValue;
                counter++;
            }
        }

        if(counter == 0) return 0;

        double num = pSum - (sumA  * sumB / counter);
        double den = Math.sqrt((sumAsq - Math.pow(sumA, 2) / counter) * (sumBsq - Math.pow(sumB, 2) / counter));

        if(den == 0) return 0;

        return num / den;
    }
}
