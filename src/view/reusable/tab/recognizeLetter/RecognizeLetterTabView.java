package view.reusable.tab.recognizeLetter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import letter.Letter;
import letter.LetterUtils;
import madaline.LetterRecognition;
import neuron.DefaultData;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class RecognizeLetterTabView extends HBox implements Initializable {

    LetterRecognition letterRecognition;

    @FXML
    GridPane letterGrid;

    @FXML
    ComboBox selectLetter;

    @FXML
    GridPane probabilityGrid;

    @FXML
    Label greatestSimilarity;

    public RecognizeLetterTabView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recognizeLetterTabView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.letterRecognition = new LetterRecognition(DefaultData.letterToRecognizePath);
        this.draw();
    }

    private void draw() {
        this.drawLetter();
        this.loadLetters();
        letterRecognition.learn();
        this.drawProbability();
        this.drawSimilarityValue();
    }

    private void clear() {
        this.letterGrid.getChildren().clear();
        this.probabilityGrid.getChildren().clear();
        this.greatestSimilarity.setText("-");
    }

    private void redraw(String path) {
        this.letterRecognition = new LetterRecognition(path);
        this.clear();
        this.draw();
    }

    private void drawLetter() {
        Letter letter = this.letterRecognition.getLetterToRecognize();
        for (int i = 0; i < letter.getHeight(); i++) {
            for (int j = 0; j < letter.getWidth(); j++) {
                Double number = letter.getRepresentation().get(i).get(j);
                Text numberText = new Text(number == 1.0 ? DefaultData.LETTER_CHARACTER : number.toString());
                letterGrid.add(numberText, j, i);
            }
        }
    }

    private void drawProbability() {
        LinkedList<Double> similarity = this.letterRecognition.getLettersSimilarity();

        for (int i = 0; i < this.letterRecognition.getLetters().size(); i++) {
            Letter letter = this.letterRecognition.getLetters().get(i);
            String letterName = "Letter: " + letter.getName().replace(".txt", "");
            String probability = DefaultData.DECIMAL.format(similarity.get(i));
            Text letterText = new Text(letterName);
            Text probabilityText = new Text(probability);
            probabilityGrid.add(letterText, 0, i);
            probabilityGrid.add(probabilityText, 1, i);
        }
    }

    private void drawSimilarityValue() {
        LinkedList<Double> similarity = this.letterRecognition.getLettersSimilarity();
        double maxSimilarity = Collections.max(similarity);
        int indexOfMax = similarity.indexOf(maxSimilarity);
        Letter letter = this.letterRecognition.getLetters().get(indexOfMax);

        greatestSimilarity.setText("Letter " + letter.getName() + " // " + DefaultData.DECIMAL.format(maxSimilarity));
    }

    private void loadLetters() {
        if (this.selectLetter.getItems().size() == 0) {
            LinkedList<Letter> availableLetters = LetterUtils.getAll("/src/letter/toBeTrainedOn");
            for (Letter letter : availableLetters) {
                selectLetter.getItems().add(letter.getName());
            }
            selectLetter.setValue(selectLetter.getItems().get(0));
            selectLetter.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    RecognizeLetterTabView.this.redraw("/src/letter/toBeTrainedOn/" + t1);
                }
            });
        }
    }
}