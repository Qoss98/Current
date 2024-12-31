package com.eva.current;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Constraints {
     void InputFields2(Label verbruikStroom, TextField txtVerbruikStroom, Label verbruikGas, TextField txtVerbruikGas, Label dataVanafVerbruikL, DatePicker dataVanafVerbruik, Label dataTotVerbruikL, DatePicker dataTotVerbruik) {
        GridPane.setConstraints(verbruikStroom, 0, 6);
        GridPane.setConstraints(txtVerbruikStroom, 1, 6);
        GridPane.setConstraints(verbruikGas, 0, 7);
        GridPane.setConstraints(txtVerbruikGas, 1, 7);
        GridPane.setConstraints(dataVanafVerbruikL, 0, 8);
        GridPane.setConstraints(dataVanafVerbruik, 1, 8);
        GridPane.setConstraints(dataTotVerbruikL, 0, 9);
        GridPane.setConstraints(dataTotVerbruik, 1, 9);
    }

    void InputFields3(Label klantnr, TextField txtKlantnr, Label vNaam, TextField txtVNaam, Label aNaam, TextField txtANaam, Label jVoorschot, TextField txtVoorschot) {
        GridPane.setConstraints(klantnr, 0, 0);
        GridPane.setConstraints(txtKlantnr, 1, 0);
        GridPane.setConstraints(vNaam, 0, 1);
        GridPane.setConstraints(txtVNaam, 1, 1);
        GridPane.setConstraints(aNaam, 0, 2);
        GridPane.setConstraints(txtANaam, 1, 2);
        GridPane.setConstraints(jVoorschot, 0, 4);
        GridPane.setConstraints(txtVoorschot, 1, 4);
    }

    void InputFields4(Label hstroom, TextField txtStroom, Label dataVanafStroomL, DatePicker dataVanafStroom, Label dataTotStroomL, DatePicker dataTotStroom) {
        GridPane.setConstraints(hstroom, 0, 5);
        GridPane.setConstraints(txtStroom, 1, 5);
        GridPane.setConstraints(dataVanafStroomL, 2, 5);
        GridPane.setConstraints(dataVanafStroom, 3, 5);
        GridPane.setConstraints(dataTotStroomL, 4, 5);
        GridPane.setConstraints(dataTotStroom, 5, 5);
    }

    void InputFields1(Label hgas, TextField txtGas, Label dataVanafGasL, DatePicker dataVanafGas, Label dataTotGasL, DatePicker dataTotGas) {
        GridPane.setConstraints(hgas, 0, 3);
        GridPane.setConstraints(txtGas, 1, 3);
        GridPane.setConstraints(dataVanafGasL, 2, 3);
        GridPane.setConstraints(dataVanafGas, 3, 3);
        GridPane.setConstraints(dataTotGasL, 4, 3);
        GridPane.setConstraints(dataTotGas, 5, 3);
    }

    void OutputFields(Label outputOpgeslagen, Label outputWeek, Label outputMaand, Label outputJaar) {
        GridPane.setConstraints(outputOpgeslagen, 0, 12);
        GridPane.setConstraints(outputWeek, 1, 13);
        GridPane.setConstraints(outputMaand, 2, 13);
        GridPane.setConstraints(outputJaar, 3, 13);
    }
}
