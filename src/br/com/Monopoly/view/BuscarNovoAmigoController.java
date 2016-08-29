/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author samuel
 */
public class BuscarNovoAmigoController implements Initializable {
     @FXML
    private AnchorPane apPrincipal;

    @FXML
    private Label lbTitulo;

    @FXML
    private TextField tfNomeBusca;

    @FXML
    private Button btBuscar;

    @FXML
    private ListView<?> lvBusca;

    @FXML
    private Button btFechar;
    
    private Stage me;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                me = (Stage) apPrincipal.getScene().getWindow();
            }
        });
    }
    
    @FXML
    void btBuscarEvent(ActionEvent event) {

    }

    @FXML
    void btFecharEvent(ActionEvent event) {
        me.close();
    }
    
}
