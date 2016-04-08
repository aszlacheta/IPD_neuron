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

package view.reusable.tab.solveEquations;

import Neuron.DefaultData;
import Neuron.Neuron;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Sample custom control hosting EQUATION_DATA_5x3 text field and EQUATION_DATA_5x3 button.
 */
public class SolveEquationsTabView extends VBox implements Initializable {

    Neuron neuron;

    @FXML
    GridPane equationsPane;

    @FXML
    GridPane factorsContainer;

    @FXML
    GridPane calculatedValues;

    public SolveEquationsTabView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("solveEquationsTabView.fxml"));
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
        this.initializeNeuron(DefaultData.EQUATION_DATA_5x3, DefaultData.EQUATION_RESULTS_5x3);
        this.drawAll();
    }

    private void drawAll() {
        this.clear();
        this.drawEquation();
        this.drawFactors();
        this.drawCalculatedValues();
    }

    private void initializeNeuron(List<List<Double>> data, List<Double> results) {
        this.neuron = new Neuron(data, results);
        this.neuron.learn();
        this.neuron.denormalize();
    }

    private void drawEquation() {
        for (int i = 0; i < this.neuron.getData().size(); i++) {
            for (int j = 0; j < this.neuron.getData().get(i).size(); j++) {
                Text category = new Text("(" + (i + 1) + ")");
                equationsPane.add(category, 0, i);
            }
        }

        for (int i = 0; i < this.neuron.getData().size(); i++) {
            int unknownVariablesNumber = this.neuron.getData().get(i).size();
            for (int j = 0; j < unknownVariablesNumber; j++) {
                double value = this.neuron.getData().get(i).get(j);
                String text = Math.round(value) + "x" + (j + 1) + " ";
                if (j != unknownVariablesNumber - 1) {
                    text += "+";
                } else {
                    text += "=";
                }
                equationsPane.add(new Text(text), j + 1, i);
            }
            Text category = new Text(this.neuron.getResults().get(i) + "");
            equationsPane.add(category, unknownVariablesNumber + 1, i);
        }
    }

    private void drawFactors() {
        for (int i = 0; i < this.neuron.getFactors().size(); i++) {
            HBox factorContainer = new HBox();
            Text factorName = new Text("Factor " + (i+1) + ":   ");
            Text factorValue = new Text(DefaultData.DECIMAL.format(this.neuron.getFactors().get(i)) + "");
            factorContainer.getChildren().addAll(factorName, factorValue);
            this.factorsContainer.add(factorContainer, 0, i);
        }
    }

    private void drawCalculatedValues() {
        List<Double> list = this.neuron.getCalculatedEquationValues();
        for (int i = 0; i < list.size(); i++) {
            Text factorName = new Text(DefaultData.DECIMAL.format(list.get(i)) + "");
            this.calculatedValues.add(factorName, 0, i);
        }
    }

    private void clear() {
        this.equationsPane.getChildren().clear();
        this.factorsContainer.getChildren().clear();
        this.calculatedValues.getChildren().clear();
    }

    @FXML
    private void importEquation5x3() {
        this.initializeNeuron(DefaultData.EQUATION_DATA_5x3, DefaultData.EQUATION_RESULTS_5x3);
        this.drawAll();
    }

    @FXML
    private void importEquation5x5() {
        this.initializeNeuron(DefaultData.EQUATION_DATA_5x5, DefaultData.EQUATION_RESULTS_5x5);
        this.drawAll();
    }
}
