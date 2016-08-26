/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.Alerta;
import br.com.Monopoly.control.Seguranca;
import br.com.Monopoly.control.Sessao;
import br.com.Monopoly.control.Validador;
import br.com.Monopoly.control.dao.UsuarioDAO;
import br.com.Monopoly.model.entity.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author samuel
 */
public class AlterarSenhaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private PasswordField pfSenhaAtual;
    
    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private PasswordField pfNovaSenha1;

    @FXML
    private PasswordField pfNovaSenha2;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btCancelar;

    private Stage me;
    
    private Usuario usuario;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                me = (Stage) pfSenhaAtual.getScene().getWindow();
                
                usuario = (Usuario) apPrincipal.getUserData();
            }
        });
    }

    @FXML
    void btCancelarEvent(ActionEvent event) {
        me.close();
    }

    @FXML
    void btSalvarEvent(ActionEvent event) {
        if (Validador.required(pfSenhaAtual) && Validador.required(pfNovaSenha1) && Validador.required(pfNovaSenha2)) {
            if (pfNovaSenha1.getText().equals(pfNovaSenha2.getText()) && usuario.getSenha().equals(Seguranca.criptografar(pfSenhaAtual.getText()))) {
                usuario.setSenha(Seguranca.criptografar(pfNovaSenha1.getText()));
                new UsuarioDAO().editar(usuario);
                Alerta.criarAlert(Alerta.tipoAlerta.CONCLUIDO).show();
                me.close();
            } else {
                Alert erro = Alerta.criarAlert(Alerta.tipoAlerta.ERROLOGIN);
                erro.setHeaderText("Nova senha invalida.");
                erro.setContentText("Erro na confirmação da senha. Digite novamente.");
                erro.show();
                pfNovaSenha2.setText("");
                pfNovaSenha2.requestFocus();
            }
        }
    }

}
