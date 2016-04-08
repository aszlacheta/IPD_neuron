package view.reusable.tab.classifyPoints.graph;

import Neuron.Neuron;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GraphView extends HBox implements Initializable {

    Neuron neuron;

    ScatterChart<Number, Number> graph;
    NumberAxis xAxis;
    NumberAxis yAxis;
    XYChart.Series negativeData = new XYChart.Series();
    XYChart.Series positiveData = new XYChart.Series();
    XYChart.Series classificationLine = new XYChart.Series();

    public GraphView() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("graphView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setNeuron(Neuron neuron) {
        this.neuron = neuron;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setAxes();
        this.graph = new ScatterChart<>(xAxis, yAxis);
        this.graph.setTitle("Points decomposition");
        this.graph.getData().addAll(positiveData, negativeData, classificationLine);
        this.getChildren().add(this.graph);
    }

    public void loadData() {
        this.drawData();
        this.drawClassificationLine();
    }

    private void setAxes() {
        xAxis = new NumberAxis(-21, 21, 1);
        yAxis = new NumberAxis(-21, 21, 1);
        xAxis.setLabel("X");
        yAxis.setLabel("Y");
    }

    private void drawData() {
        positiveData.setName("Positive item");
        negativeData.setName("Negative item");

        for (int i = 0; i < neuron.getData().size(); i++) {
            List<Double> point = neuron.getData().get(i);
            double classification = neuron.getResults().get(i);
            XYChart.Data graphPoint = new XYChart.Data(point.get(0), point.get(1));

            if (classification == 1) {
                positiveData.getData().add(graphPoint);
            } else if (classification == -1) {
                negativeData.getData().add(graphPoint);
            }
        }
    }

    private void drawClassificationLine() {
        classificationLine.setName("Classification line");
        double w1 = neuron.getFactors().get(0);
        double w2 = neuron.getFactors().get(1);

        for (double i = -8; i < 8; i += 0.1) {
            double x = i;
            double y = (-(w1 / w2) * x) + 0.2 / w2;
            classificationLine.getData().add(new XYChart.Data(x, y));
        }
    }

    public void clearGraph() {
        this.negativeData.getData().clear();
        this.positiveData.getData().clear();
        this.classificationLine.getData().clear();
    }

    public void updateClassificationLineName(String equation) {
        this.classificationLine.setName("Classification line " + equation);
    }
}
