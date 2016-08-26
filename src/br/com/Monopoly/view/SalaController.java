/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.model.entity.Sala;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author OCTI-Lucas
 */
public class SalaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private GridPane gpUsuarios;

    private Sala sala;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {

        });
    }

    @FXML
    public void btConfiguracaoActionEvent(ActionEvent actionEvent) {
        
    }

    @FXML
    public void btComecarActionEvent(ActionEvent actionEvent) {

    }

    @FXML
    public void btFecharSalaActionEvent(ActionEvent actionEvent) {

    }
}
