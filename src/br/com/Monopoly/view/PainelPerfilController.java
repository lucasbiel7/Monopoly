/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.Sessao;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author samuel
 */
public class PainelPerfilController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private VBox vbPerfil;

    @FXML
    private Circle ciFoto;

    @FXML
    private Label lbNome;

    @FXML
    private Label lbNumeroPartidas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbNome.setText(Sessao.usuario.getNome());
        
        if(Sessao.usuario.getFoto() != null){
            ciFoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(Sessao.usuario.getFoto()))));
        }
        
    }

}
