/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.Alerta;
import br.com.Monopoly.control.GerenciadorDeJanelas;
import br.com.Monopoly.control.Seguranca;
import br.com.Monopoly.control.Sessao;
import br.com.Monopoly.control.dao.UsuarioDAO;
import br.com.Monopoly.model.entity.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI-Lucas
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private TextField tfLogin;

    @FXML
    private PasswordField pfSenha;

    @FXML
    private Button btSubmit;

    @FXML
    private ImageView ivLogo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (Sessao.usuario.getValue() != null) {
                Usuario usuario = Sessao.usuario.getValue();
                usuario.setOnline(false);
                new UsuarioDAO().editar(usuario);
            }
        }));
    }

    @FXML
    void btSubmitEvent(ActionEvent event) {
        Usuario usuario = new UsuarioDAO().login(tfLogin.getText(), Seguranca.criptografar(pfSenha.getText()));
        if (usuario != null) {
            usuario.setOnline(true);
            new UsuarioDAO().editar(usuario);
            Sessao.usuario.setValue(usuario);
            ((Stage) apPrincipal.getScene().getWindow()).close();
            GerenciadorDeJanelas.abrirJanela(GerenciadorDeJanelas.carregarComponente("Principal"), "Tela principal", GerenciadorDeJanelas.Tipo.MAXIMIZED, GerenciadorDeJanelas.Tipo.UNDECORATED).show();
        } else {
            Alerta.criarAlert(Alerta.tipoAlerta.ERROLOGIN).show();
        }
    }

}
