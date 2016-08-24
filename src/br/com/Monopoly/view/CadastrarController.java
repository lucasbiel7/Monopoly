/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.Alerta;
import br.com.Monopoly.control.dao.UsuarioDAO;
import br.com.Monopoly.model.entity.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author samuel
 */
public class CadastrarController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    
    @FXML
    private Label lbTitulo;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfLogin;

    @FXML
    private PasswordField pfSenha;

    @FXML
    private Circle ccFoto;

    @FXML
    private Button btAddFoto;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btCancelar;

    @FXML
    private Button btRemoveFoto;
    
    @FXML
    private AnchorPane apPermissoes;

    @FXML
    private RadioButton rbCadastrar;

    @FXML
    private RadioButton rbCriarSala;

    @FXML
    private RadioButton rbBatePapo;

    @FXML
    private RadioButton rbDeletar;
    
    private Usuario usuario;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario = new Usuario();
    }

    @FXML
    void btAddFotoEvent(ActionEvent event) {

    }

    @FXML
    void btCancelarEvent(ActionEvent event) {

    }

    @FXML
    void btRemoveFotoEvent(ActionEvent event) {

    }

    @FXML
    void btSalvarEvent(ActionEvent event) {
        usuario.setNome(tfNome.getText());
        usuario.setLogin(tfLogin.getText());
        usuario.setSenha(pfSenha.getText());
        new UsuarioDAO().salvar(usuario);
        Alerta.criarAlert(Alerta.tipoAlerta.CONCLUIDO).show();
    }
    
    @FXML
    void rbEvent(ActionEvent event) {

    }
}
