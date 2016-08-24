/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.Alerta;
import br.com.Monopoly.control.Validador;
import br.com.Monopoly.control.dao.PermissaoDAO;
import br.com.Monopoly.control.dao.UsuarioDAO;
import br.com.Monopoly.model.Funcionalidade;
import br.com.Monopoly.model.entity.Permissao;
import br.com.Monopoly.model.entity.Usuario;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.layout.VBox;
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
    private VBox vbPermissoes;

    private Usuario usuario;
    
    private Permissao perm;
    
    private List<Funcionalidade> permissoes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario = new Usuario();
        permissoes = new ArrayList<>();

        for (Funcionalidade func : Funcionalidade.values()) {
            RadioButton rdPerm = new RadioButton(func.getNome());
            rdPerm.setUserData(func);
            rdPerm.setOnAction((event) -> {
                if (rdPerm.isSelected()) {
                    permissoes.add(func);
                } else {
                    permissoes.remove(func);
                }
            });
            vbPermissoes.getChildren().add(rdPerm);

        }

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
        if (Validador.required(tfNome) && Validador.required(tfLogin) && Validador.required(pfSenha)) {
            /**
             * *****************************************************************************************
             * Salvando Usuario
             * ****************************************************************************************
             */
            usuario.setNome(tfNome.getText());
            usuario.setLogin(tfLogin.getText());
            usuario.setSenha(pfSenha.getText());
            new UsuarioDAO().salvar(usuario);

            /**
             * *****************************************************************************************
             * Salvando permissões do Usuario
             * ****************************************************************************************
             */
            for (Funcionalidade permissoe : permissoes) {
                perm = new Permissao(new Permissao.PermissaoID(permissoe, usuario),true,false);
                new PermissaoDAO().salvar(perm);
            }
            
            Alerta.criarAlert(Alerta.tipoAlerta.CONCLUIDO).show();
            
        }

       
    }

}
