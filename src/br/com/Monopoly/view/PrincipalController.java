/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.GerenciadorDeJanelas;
import br.com.Monopoly.control.Sessao;
import br.com.Monopoly.model.Funcionalidade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
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
    private ScrollPane spContainer;
    @FXML
    private ImageView ivIcon;
    @FXML
    private MenuItem miNovo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GerenciadorDeJanelas.inserirPainel(spContainer, GerenciadorDeJanelas.carregarComponente("Inicio"));
        Sessao.container = spContainer;
        Platform.runLater(() -> {
            Stage stage = (Stage) apPrincipal.getScene().getWindow();
            if (!stage.getIcons().isEmpty()) {
                ivIcon.setImage(stage.getIcons().get(0));
            }
        });

        miNovo.setDisable(!Sessao.verificarPermissao(Funcionalidade.CADASTRAR));
    }

    @FXML
    public void btFecharActionEvent(ActionEvent actionEvent) {
        ((Stage) apPrincipal.getScene().getWindow()).close();
    }

    @FXML
    public void btMinimizarActionEvent(ActionEvent actionEvent) {
        ((Stage) apPrincipal.getScene().getWindow()).setIconified(true);
    }

    @FXML
    public void miPerfilActionEvent(ActionEvent actionEvent) {
        GerenciadorDeJanelas.abrirJanela(GerenciadorDeJanelas.carregarComponente("GerenciarPerfil", Sessao.usuario.get()), "Perfil - Monopoly Deal", GerenciadorDeJanelas.Tipo.MODAL, GerenciadorDeJanelas.Tipo.UNRESIZABLE).showAndWait();
    }

    @FXML
    public void miNovoUsuarioActionEvent(ActionEvent actionEvent) {
        GerenciadorDeJanelas.abrirJanela(GerenciadorDeJanelas.carregarComponente("GerenciarPerfil"), "Perfil - Monopoly Deal", GerenciadorDeJanelas.Tipo.MODAL, GerenciadorDeJanelas.Tipo.UNRESIZABLE).showAndWait();
    }
}
