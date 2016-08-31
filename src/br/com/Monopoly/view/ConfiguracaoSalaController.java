/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author samuel
 */
public class ConfiguracaoSalaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private Label lbTitulo;

    @FXML
    private HBox hbLinhaNumeroDeJogadores;

    @FXML
    private Label lbNumeroDeJogadores;

    @FXML
    private Spinner<Integer> spNumeroDeJogadores;

    @FXML
    private Button btCancelar;

    @FXML
    private Button btSalvar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spNumeroDeJogadores.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 5, 5));
    }    
    
     @FXML
    void btCancelarEvent(ActionEvent event) {

    }

    @FXML
    void btSalvarEvent(ActionEvent event) {

    }

}
