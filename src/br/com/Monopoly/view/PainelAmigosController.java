/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.GerenciadorDeJanelas;
import br.com.Monopoly.control.Sessao;
import br.com.Monopoly.control.dao.AmigoDAO;
import br.com.Monopoly.model.entity.Amigo;
import br.com.Monopoly.model.entity.Usuario;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author samuel
 */
public class PainelAmigosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TreeView<Object> tvAmigos;
    @FXML
    private Button btAddAmigos;
//Atributos da classe    
    private TreeItem<Object> tiRoot;
    private TreeItem<Object> tiAmigos;
    private TreeItem<Object> tiPendentes;
    private Stage me;
    private List<Amigo> meusAmigos;
    private Timeline tlAtualizaAmigos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tiRoot = new TreeItem("Lista de Amizades");
        tiAmigos = new TreeItem("Amigos");
        tiPendentes = new TreeItem("Pendencias de Amizade");
        tvAmigos.setRoot(tiRoot);
        tiRoot.getChildren().add(tiAmigos);
        tiRoot.getChildren().add(tiPendentes);
        tiRoot.setExpanded(true);
        btAddAmigos.setOnAction((event) -> {
            Stage tela = GerenciadorDeJanelas.abrirJanela(GerenciadorDeJanelas.carregarComponente("BuscarNovoAmigo"), "Encontre seu Amigo !!", GerenciadorDeJanelas.Tipo.UNDECORATED, GerenciadorDeJanelas.Tipo.UNRESIZABLE);
            tela.initModality(Modality.WINDOW_MODAL);
            tela.initOwner(me);
            tela.show();
        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                me = (Stage) apPrincipal.getScene().getWindow();
                Sessao.container.contentProperty().addListener(new ChangeListener<Node>() {
                    @Override
                    public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                        System.out.println(newValue);
                        tlAtualizaAmigos.stop();
                    }
                });
            }
        });
        tlAtualizaAmigos = new Timeline(new KeyFrame(Duration.seconds(15), (ActionEvent event) -> {
            atualizar();
        }));
        atualizar();
        tlAtualizaAmigos.setCycleCount(Timeline.INDEFINITE);
        tlAtualizaAmigos.play();
        tvAmigos.setCellFactory((TreeView<Object> param) -> new TreeCell<Object>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                if (empty) {
                    setText("");
                    setGraphic(null);
                } else {
                    setText(item.toString());
                    if (item instanceof Usuario) {
                        Usuario usuario = (Usuario) item;
                        Amigo amigo = new AmigoDAO().buscarAmizade(Sessao.usuario.get(), usuario);
                        if (amigo != null) {
                            if (amigo.isAceito()) {
                                setText(usuario.getNome());
                                if (usuario.isOnline()) {
                                    Circle circle = new Circle(5);
                                    circle.setFill(Color.GREEN);
                                    setContentDisplay(ContentDisplay.LEFT);
                                    setGraphic(circle);
                                }
                            } else if (amigo.getId().getConvidado().equals(Sessao.usuario.get())) {
                                HBox hbSolicitacao = new HBox();
                                Button btAceitarSolicitacao = new Button("Aceitar");
                                btAceitarSolicitacao.setOnAction((event) -> {
                                    amigo.setAceito(true);
                                    new AmigoDAO().editar(amigo);
                                    atualizar();
                                });
                                Button btRecusarSolicitacao = new Button("Recusar");
                                btRecusarSolicitacao.setOnAction((event) -> {
                                    amigo.setDel(true);
                                    new AmigoDAO().editar(amigo);
                                    atualizar();
                                });
                                hbSolicitacao.getChildren().add(btAceitarSolicitacao);
                                hbSolicitacao.getChildren().add(btRecusarSolicitacao);
                                hbSolicitacao.setSpacing(20d);
                                setGraphic(hbSolicitacao);
                                setContentDisplay(ContentDisplay.RIGHT);
                            }
                        } else {
                            setText("");
                            setGraphic(null);
                        }
                    }
                }
            }
        });
    }

    public void atualizar() {
        //Lista de amigos pendentes na base de dados
        final List<Usuario> amigosPendentesBanco = new AmigoDAO().buscarAmigosPendentes(Sessao.usuario.get()).stream().map((Amigo t) -> {
            if (t.getId().getConvidado().equals(Sessao.usuario.get())) {
                return t.getId().getRemetente();
            } else {
                return t.getId().getConvidado();
            }
        }).collect(Collectors.toList());
        //Lista de amigos adicionados atualizada com a base de dados
        final List<Usuario> amigosAdicionadosBanco = new AmigoDAO().buscarAmigosAceitos(Sessao.usuario.get()).stream().map((Amigo t) -> {
            if (t.getId().getConvidado().equals(Sessao.usuario.get())) {
                return t.getId().getRemetente();
            } else {
                return t.getId().getConvidado();
            }
        }).collect(Collectors.toList());
        //Pegando todos usuarios ja no treeview
        final List<Usuario> amigosAdicionados = (List<Usuario>) tiAmigos.getChildren().stream().map((Object t) -> (Usuario) ((TreeItem) t).getValue()).collect(Collectors.toList());
        //Atualizanndo online
        for (Usuario usuario : amigosAdicionados) {
            if (amigosAdicionadosBanco.contains(usuario)) {
                Usuario usuarioBanco = amigosAdicionadosBanco.get(amigosAdicionadosBanco.indexOf(usuario));
                if (usuarioBanco.isOnline() != usuario.isOnline()) {
                    for (TreeItem<Object> treeItem : tiAmigos.getChildren()) {
                        if (treeItem.getValue().equals(usuario)) {
                            treeItem.setValue(usuarioBanco);
                        }
                    }
                }
            }
        }
        //Pegando todos os amigos pendentes que já estão na telas
        final List<Usuario> amigosPendentes = (List<Usuario>) tiPendentes.getChildren().stream().map((Object t) -> (Usuario) ((TreeItem) t).getValue()).collect(Collectors.toList());
        //Remover
        tiAmigos.getChildren().removeIf((Object t) -> {
            TreeItem treeItem = (TreeItem) t;
            return !amigosAdicionadosBanco.contains(treeItem.getValue());
        });
        tiPendentes.getChildren().removeIf((Object t) -> {
            TreeItem treeItem = (TreeItem) t;
            return !amigosPendentesBanco.contains(treeItem.getValue());
        });
        //Adicionar novos
        amigosPendentesBanco.removeAll(amigosPendentes);
        amigosAdicionadosBanco.removeAll(amigosAdicionados);
        for (Usuario usuario : amigosAdicionadosBanco) {
            adicionar(usuario, tiAmigos);
        }
        for (Usuario usuario : amigosPendentesBanco) {
            adicionar(usuario, tiPendentes);
        }
    }

    private void adicionar(Usuario usuario, TreeItem tiAmigos) {
        TreeItem solicitacao = new TreeItem(usuario);
        tiAmigos.getChildren().add(solicitacao);
    }
}
