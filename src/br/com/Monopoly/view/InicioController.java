/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.Sessao;
import br.com.Monopoly.model.Funcionalidade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI-Lucas
 */
public class InicioController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Button btCriarSala;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("teste");
        btCriarSala.setVisible(Sessao.verificarPermissao(Funcionalidade.CRIARSALA));
    }
}
