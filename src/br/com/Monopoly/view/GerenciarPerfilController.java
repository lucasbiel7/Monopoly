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
import br.com.Monopoly.control.Validador;
import br.com.Monopoly.control.dao.PermissaoDAO;
import br.com.Monopoly.control.dao.UsuarioDAO;
import br.com.Monopoly.model.Funcionalidade;
import br.com.Monopoly.model.entity.Permissao;
import br.com.Monopoly.model.entity.Usuario;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author samuel
 */
public class GerenciarPerfilController implements Initializable {

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

    @FXML
    private VBox vbCampos;

    private Usuario usuario;

    private Stage me;

    private List<Funcionalidade> permissoes;
    private List<RadioButton> listBotao;
    private FileChooser fcImage;
    List<Permissao> permissoesUsuario;

    @FXML
    private Label lbSenha;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * *****************************************************************************************
         * Ações iniciais
         * ****************************************************************************************
         */

        /**
         * Instancia a lista que armazenará as permissoes selecionadas pelos
         * radion butons na tela. *
         */
        permissoes = new ArrayList<>();
        listBotao = new ArrayList<>();

        /**
         * Iniciando o objeto da tela de seleção de arquivos. *
         */
        fcImage = new FileChooser();

        /**
         * Definidno o filtro que a tela de seleção de arquivos terá para busca
         * de fotos. *
         */
        fcImage.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagem", "*.png", "*.jpg"));

        /**
         * Loop para criação dos radios button de permissões da tela. *
         */
        for (Funcionalidade func : Funcionalidade.values()) {
            // Criação do radio button e definição do texto do botão.
            RadioButton rdPerm = new RadioButton(func.getNome());
            // Inserindo a funcionalidade da permissão, o ato que será permitido.
            rdPerm.setUserData(func);
            //Declaração do método de ação ao pressinar o radio button
            rdPerm.setOnAction((event) -> {
                //Caso ele esteja sendo pressionado ele adiciona a funcionalidade na lista de permissoes.
                if (rdPerm.isSelected()) {
                    permissoes.add(func);
                } else {
                    //Caso esteja sendo desmarcado ele removerá a permissoes da lista.
                    permissoes.remove(func);
                }
            });

            /**
             * Inserção do radio button no vbox de permissões. *
             */
            vbPermissoes.getChildren().add(rdPerm);
            listBotao.add(rdPerm);
        }

        Platform.runLater(new Runnable() {

            @Override
            public void run() {

                me = (Stage) apPrincipal.getScene().getWindow();
                usuario = (Usuario) apPrincipal.getUserData();

                if (usuario != null) {
                    /**
                     * Caso seja passado um usuario para a tela. *
                     */
                    tfNome.setText(usuario.getNome());
                    tfLogin.setText(usuario.getLogin());
                    vbCampos.getChildren().remove(pfSenha);
                    vbCampos.getChildren().remove(lbSenha);
                    Button btSenha = new Button("Alterar Senha");
                    btSenha.setOnAction((event) -> {
                        GerenciadorDeJanelas.abrirJanela(GerenciadorDeJanelas.carregarComponente("AlterarSenha", usuario), "Alterar Senha", GerenciadorDeJanelas.Tipo.MODAL, GerenciadorDeJanelas.Tipo.UNDECORATED).show();
                    });
                    vbCampos.getChildren().add(btSenha);
                    lbTitulo.setText("Meu Perfil");

                    if (usuario.getFoto() != null) {
                        ccFoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(usuario.getFoto()))));
                    }
                    permissoesUsuario = new PermissaoDAO().buscarPorUsuario(usuario);
                    for (Permissao permissaoUser : permissoesUsuario) {
                        if (!permissaoUser.isDel()) {
                            permissoes.add(permissaoUser.getId().getFuncionalidade());
                            marcarBotao(permissaoUser.getId().getFuncionalidade());
                        }
                    }
                } else {
                    /**
                     * Instanciando um usuario que será tratado na tela. *
                     */
                    usuario = new Usuario();
                }
            }

            private void marcarBotao(Funcionalidade funcionalidade) {
                for (RadioButton radioButton : listBotao) {
                    Funcionalidade rdFuncionalidade = (Funcionalidade) radioButton.getUserData();
                    if (rdFuncionalidade.equals(funcionalidade)) {
                        radioButton.setSelected(true);
                        break;
                    }
                }
            }
        });
    }

    /**
     * *****************************************************************************************
     * Método chamado pelo botão de add de fotos na tela.
     * *****************************************************************************************
     */
    @FXML
    void btAddFotoEvent(ActionEvent event) {
        /**
         * Exibe a janela de seleção de arquivos para escolha da imagem. *
         */
        File file = fcImage.showOpenDialog(apPrincipal.getScene().getWindow());
        /**
         * Se o usuario estiver selecionado o arquivo a mesma será adicionada ao
         * objeto e ao circulo de foto. *
         */
        if (file != null) {
            try {
                usuario.setFoto(Files.readAllBytes(file.toPath()));
                ccFoto.setFill(new ImagePattern(new Image(new ByteArrayInputStream(usuario.getFoto()))));
            } catch (IOException ex) {
                Logger.getLogger(GerenciarPerfilController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * *****************************************************************************************
     * Método chamado pelo botão de cancelamento.
     * *****************************************************************************************
     */
    @FXML
    void btCancelarEvent(ActionEvent event) {
        /**
         * Caso o botão seja pressionado a tela é fechada. *
         */
        if (apPrincipal.getUserData() != null) {
            Sessao.usuario.set(new UsuarioDAO().buscarPorID(usuario.getId()));
        }
        ((Stage) apPrincipal.getScene().getWindow()).close();
    }

    /**
     * *****************************************************************************************
     * Método chamado pelo botão de remover foto.
     * *****************************************************************************************
     */
    @FXML
    void btRemoveFotoEvent(ActionEvent event) {
        /**
         * Retira a imagem do circulo de foto. *
         */
        ccFoto.setFill(null);
        /**
         * retira a imagem do objeto usuario. *
         */
        usuario.setFoto(null);
    }

    /**
     * *****************************************************************************************
     * Método chamado pelo botão de salvar.
     * *****************************************************************************************
     */
    @FXML
    void btSalvarEvent(ActionEvent event) {

        if (Validador.required(tfNome) && Validador.required(tfLogin) && ((!pfSenha.getText().equals("")) || apPrincipal.getUserData() != null)) {
            /**
             * *****************************************************************************************
             * Salvando Usuario
             * ****************************************************************************************
             */
            /**
             * Inseri a entrada do usuario de nome no objeto *
             */
            usuario.setNome(tfNome.getText());
            /**
             * Inseri a entrada do usuario de login no objeto *
             */
            usuario.setLogin(tfLogin.getText());

            if (apPrincipal.getUserData() == null) {
                /**
                 * Inseri a entrada do usuario de senha no objeto *
                 */
                usuario.setSenha(Seguranca.criptografar(pfSenha.getText()));
            }
            if (apPrincipal.getUserData() == null) {
                /**
                 * Salva o objeto no banco de dados. *
                 */
                new UsuarioDAO().salvar(usuario);

                /**
                 * *****************************************************************************************
                 * Salvando permissões do Usuario
                 * ****************************************************************************************
                 */
                /**
                 * Loop pecorre a lista de permissoes add pelos radio button da
                 * tela. *
                 */
                for (Funcionalidade permissoe : permissoes) {
                    Permissao perm = new Permissao(new Permissao.PermissaoID(permissoe, usuario), true, false);
                    /**
                     * Salva a permissão do usuario no banco de dados. *
                     */
                    new PermissaoDAO().salvar(perm);
                }
            } else {
                new UsuarioDAO().editar(usuario);
                if (usuario.equals(Sessao.usuario.get())) {
                    Sessao.usuario.set(null);
                    Sessao.usuario.set(usuario);
                }
                if (!permissoes.isEmpty()) {

                    for (Funcionalidade funcionadadesSelecionada : permissoes) {
                        boolean permEncontrada = false;
                        for (Permissao permissao : permissoesUsuario) {

                            if (permissoes.indexOf(permissao.getId().getFuncionalidade()) < 0) {
                                deletarPermissao(permissao);
                            } else if (permissao.isDel()) {
                                restaurarPermissao(permissao);
                            }

                            if (funcionadadesSelecionada.equals(permissao.getId().getFuncionalidade())) {
                                permEncontrada = true;
                            }
                        }

                        if (!permEncontrada) {
                            Permissao novaPermissao = new Permissao(new Permissao.PermissaoID(funcionadadesSelecionada, usuario), true, false);
                            new PermissaoDAO().salvar(novaPermissao);
                        }

                    }
                } else {
                    for (Permissao permissao : permissoesUsuario) {
                        if (!permissao.isDel()) {
                            deletarPermissao(permissao);
                        }
                    }
                }

            }

            /**
             * Mostra um pop up de alerta indicando a conclusão do
             * cadastramento. *
             */
            Alerta.criarAlert(Alerta.tipoAlerta.CONCLUIDO).show();
            /**
             * Chama o método do botão de cancelar para o fechamento da tela
             * (Reaproveitamento de código) *
             */
            btCancelarEvent(event);
        }

    }

    public void deletarPermissao(Permissao perm) {
        perm.setDel(true);
        perm.setAtivado(false);
        new PermissaoDAO().editar(perm);
    }

    private void restaurarPermissao(Permissao permissao) {
        permissao.setDel(false);
        permissao.setAtivado(true);
        new PermissaoDAO().editar(permissao);
    }
}
