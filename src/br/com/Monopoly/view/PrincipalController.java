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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI-Lucas
 */
public class PrincipalController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ImageView ivIcon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void btMaximizarActionEvent(ActionEvent actionEvent) {

    }

    @FXML
    public void btFecharActionEvent(ActionEvent actionEvent) {
        ((Stage) apPrincipal.getScene().getWindow()).close();
    }

    @FXML
    public void btMinimizarActionEvent(ActionEvent actionEvent) {
        ((Stage) apPrincipal.getScene().getWindow()).setIconified(true);
    }

}
