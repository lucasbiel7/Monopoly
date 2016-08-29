/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.Sessao;
import br.com.Monopoly.model.entity.Usuario;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
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
        lbNome.setText(Sessao.usuario.get().getNome());

        if (Sessao.usuario.getValue().getFoto() != null) {
            ciFoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(Sessao.usuario.get().getFoto()))));
        }

        Sessao.usuario.addListener((ObservableValue<? extends Usuario> observable, Usuario oldValue, Usuario newValue) -> {
            if (newValue != null) {
                if (newValue.getFoto() != null) {
                    ciFoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(Sessao.usuario.get().getFoto()))));
                } else {
                    ciFoto.setFill(null);
                }
            }
        });

    }

}
