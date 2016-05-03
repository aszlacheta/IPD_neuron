package neuron;

import java.util.*;

public class Neuron {

    List<List<Double>> data;
    List<Double> factors = new ArrayList<>();
    List<Double> results;
    Double maxDataSetValue = 0.0;   // used to normalization

    final static Double TEACHING_FACTOR = 0.1;
    final static Double ACCEPTABLE_ERROR_VALUE = 0.00001;

    public Neuron(List<List<Double>> data, List<Double> results) {
        this.data = data;
        this.results = results;
        this.generateFactors();
        normalize();
    }

    public Neuron(List<Double> data, List<Double> factors, List<Double> results) {
        this.factors = factors;
        this.data = new LinkedList<List<Double>>() {{this.push(data);}};
    }

    private void normalize() {
        for (List<Double> num : data) {
            double localMax = Collections.max(num);
            if (maxDataSetValue < localMax) {
                maxDataSetValue = localMax;
            }
        }

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                double value = data.get(i).get(j) / maxDataSetValue;
                data.get(i).set(j, value);
            }
            double resultsValue = results.get(i) / maxDataSetValue;
            results.set(i, resultsValue);
        }
    }

    public void denormalize() {
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                double value = data.get(i).get(j) * maxDataSetValue;
                data.get(i).set(j, value);
            }
            double resultsValue = results.get(i) * maxDataSetValue;
            results.set(i, resultsValue);
        }
    }

    private void generateFactors() {
        Random generator = new Random();
        for (int i = 0; i < data.get(0).size(); i++) {
            factors.add(generator.nextDouble());
        }
    }

    public void learnWithDelta() {
        double maxError;
        int iteration = 0;
        do {
            maxError = 0.0;
            for (int i = 0; i < data.size(); i++) {
                double error = this.getDeltaValue(i);

                this.updateFactors(error, i);
                if (Math.abs(error) > maxError) {
                    maxError = Math.abs(error);
                }
            }
            iteration++;
        } while (maxError >= ACCEPTABLE_ERROR_VALUE && iteration < 100000);
    }


    public double activate(){
       return this.getOutputValue(0);
    }

    private void updateFactors(double deltaValue, int row) {
        for (int i = 0; i < factors.size(); i++) {
            double value = factors.get(i) + (TEACHING_FACTOR * deltaValue * this.data.get(row).get(i));
            factors.set(i, value);
        }
    }

    private double getDeltaValue(int row) {
        return (this.getRealValue(row) - this.getOutputValue(row));
    }

    private double getOutputValue(int row) {
        double sum = 0.0;
        List<Double> data = this.data.get(row);

        // got sum
        for (int i = 0; i < data.size(); i++) {
            sum += data.get(i) * factors.get(i);
        }

        // linear function
        sum = this.linearActivationFunction(sum);

        return sum;
    }

    private double linearActivationFunction(double sum) {
        return sum;
    }

    private double getRealValue(int row) {
        return this.results.get(row);
    }

    public List<Double> getCalculatedEquationValues() {
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            double result = 0.0;
            for (int j = 0; j < data.get(i).size(); j++) {
                result += factors.get(j) * data.get(i).get(j);
            }
            list.add(result);
        }
        return list;
    }

    public List<List<Double>> getData() {
        return data;
    }

    public List<Double> getFactors() {
        return factors;
    }

    public List<Double> getResults() {
        return results;
    }

}
