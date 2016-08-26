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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author OCTI-Lucas
 */
public class DescricaoSalaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private GridPane gpPessoas;
    @FXML
    private ImageView ivMaster;
    @FXML
    private Label lbMaster;

    private Sala sala;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
            sala = (Sala) apPrincipal.getUserData();
        });
    }
    
}
