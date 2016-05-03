package madaline;

import letter.Letter;
import letter.LetterUtils;
import neuron.Neuron;

import java.util.LinkedList;
import java.util.List;

public class LetterRecognition {

    Letter letterToRecognize;
    LinkedList<Letter> letters = new LinkedList<>();
    LinkedList<Neuron> neurons = new LinkedList<>();
    LinkedList<Double> lettersSimilarity = new LinkedList<>();
    int neuronsNumber;


    public LetterRecognition(String path) {
        this.letterToRecognize = new Letter(path);
        this.letters = LetterUtils.getAll("/src/letter/representation");
        this.neuronsNumber = this.letters.get(0).getDimension();
        this.neurons = this.getAllNeurons();
    }

    private LinkedList<Neuron> getAllNeurons() {
        LinkedList<Neuron> neurons = new LinkedList<>();

        for (int i = 0; i < this.letters.size(); i++) {
            List<Double> originalLetter = this.letters.get(i).getNormalized();
            neurons.add(new Neuron(this.letterToRecognize.getNormalized(), originalLetter, null));
        }

        return neurons;
    }

    public void learn() {
        for (Neuron neuron : this.neurons) {
            double similarity = neuron.activate();
            lettersSimilarity.add(similarity);
        }
    }

    public Letter getLetterToRecognize() {
        return letterToRecognize;
    }

    public LinkedList<Letter> getLetters() {
        return letters;
    }

    public LinkedList<Double> getLettersSimilarity() {
        return lettersSimilarity;
    }

}