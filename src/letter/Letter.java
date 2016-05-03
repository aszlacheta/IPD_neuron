package letter;

import java.util.LinkedList;

public class Letter {

    private LinkedList<LinkedList<Double>> representation = new LinkedList<>();
    private LinkedList<Double> normalized = new LinkedList<>();
    private int dimension;
    private int width;
    private int height;
    private String name;


    public Letter(String path) {
        Letter parsedLetter = LetterUtils.get(path);
        this.representation = parsedLetter.getRepresentation();
        this.width = parsedLetter.getWidth();
        this.height = parsedLetter.getHeight();
        this.dimension = parsedLetter.getDimension();
        this.name = parsedLetter.getName();
        this.normalized = this.normalize();
    }

    public Letter(LinkedList<LinkedList<Double>> representation, String name) {
        this.representation = representation;
        this.name = name;
        this.width = this.representation.get(0).size();
        this.height = this.representation.size();
        this.dimension = this.width * this.height;
    }

    ////////////////////////////

    public LinkedList<Double> normalize() {
        LinkedList<Double> normalized = new LinkedList<>();
        LinkedList<int[]> ones = new LinkedList<>();

        for (int i = 0; i < this.representation.size(); i++) {
            for (int j = 0; j < this.representation.size(); j++) {
                Double number = this.representation.get(i).get(j);
                normalized.add(number);

                if (number == 1.0) {
                    int[] position = {i, j};
                    ones.add(position);
                }
            }
        }

        for (int[] onePosition : ones) {
            int normalizedPosition = onePosition[0] * this.getHeight() + onePosition[1];
            normalized.set(normalizedPosition, 1 / Math.sqrt(ones.size()));
        }

        return normalized;
    }

    ////////////////////////////

    public LinkedList<LinkedList<Double>> getRepresentation() {
        return representation;
    }

    public int getDimension() {
        return dimension;
    }

    public LinkedList<Double> getNormalized() {
        return normalized;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}