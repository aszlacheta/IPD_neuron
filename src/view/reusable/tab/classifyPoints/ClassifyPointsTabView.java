/*
 * Copyright (c) 2011, 2014 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package view.reusable.tab.classifyPoints;

import Neuron.DefaultData;
import Neuron.Neuron;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import view.reusable.tab.classifyPoints.graph.GraphView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Sample custom control hosting EQUATION_DATA_5x3 text field and EQUATION_DATA_5x3 button.
 */
public class ClassifyPointsTabView extends HBox implements Initializable {

    Neuron neuron;

    @FXML
    GraphView graph;

    @FXML
    GridPane dataWrapper;

    @FXML
    GridPane factorsWrapper;

    public ClassifyPointsTabView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("classifyPointsTabView.fxml"));
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
        this.initializeNeuron();
        this.graph.setNeuron(neuron);
        this.graph.loadData();
        this.drawAll();
    }

    private void initializeNeuron() {
        this.neuron = new Neuron(DefaultData.DATA_IN, DefaultData.RESULTS);
        this.neuron.learn();
        this.neuron.denormalize();
    }

    private void drawAll() {
        this.drawPointsList();
        this.drawFactors();
        this.updateLineEquation();
    }

    private void drawPointsList() {
        for (int i = 0; i < this.neuron.getData().size(); i++) {
            for (int j = 0; j < this.neuron.getData().get(i).size(); j++) {
                Text x = new Text("x" + j + "=" + this.neuron.getData().get(i).get(j));
                dataWrapper.add(x, j, i);
            }
            Text result = new Text(" = " + this.neuron.getResults().get(i));
            dataWrapper.add(result, this.neuron.getData().get(i).size(), i);
        }
    }

    private void drawFactors() {
        for (int i = 0; i < this.neuron.getFactors().size(); i++) {
            Text factor = new Text("w" + (i + 1) + " = " + (this.neuron.getFactors().get(i)));
            this.factorsWrapper.add(factor, 0, i);
        }
    }

    private void updateLineEquation() {
        double w1 = neuron.getFactors().get(0);
        double w2 = neuron.getFactors().get(1);
        this.graph.updateClassificationLineName("y = " + DefaultData.DECIMAL.format(-(w1 / w2)) + "x");
    }

    private void clear() {
        this.graph.clearGraph();
        this.dataWrapper.getChildren().clear();
        this.factorsWrapper.getChildren().clear();
    }

    @FXML
    private void learn() {
        this.clear();
        this.initializeNeuron();
        this.graph.loadData();
        this.drawAll();
    }
}
