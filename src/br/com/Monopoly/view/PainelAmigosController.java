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
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.util.Callback;
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
    private TreeItem tiAmigos;
    private TreeItem tiPendentes;
    private Stage me;
    private List<Amigo> meusAmigos;
    private Timeline tlAtualizaAmigos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tiAmigos = new TreeItem("Amigos");
        tiPendentes = new TreeItem("Pendencias de Amizade");
        tvAmigos.setRoot(new TreeItem("Lista de Amizades"));
        tvAmigos.getRoot().getChildren().add(tiAmigos);
        tvAmigos.getRoot().getChildren().add(tiPendentes);
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
            }
        });
        tlAtualizaAmigos = new Timeline(new KeyFrame(Duration.seconds(2), (ActionEvent event) -> {
            atualizar();
        }));
        tlAtualizaAmigos.setCycleCount(Timeline.INDEFINITE);
        tlAtualizaAmigos.play();
        tvAmigos.setCellFactory(new Callback<TreeView<Object>, TreeCell<Object>>() {
            @Override
            public TreeCell<Object> call(TreeView<Object> param) {
                return new TreeCell<Object>() {
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
                                        Circle circle = new Circle(5);
                                        circle.setFill(usuario.isOnline() ? Color.GREEN : Color.RED);
                                        setContentDisplay(ContentDisplay.LEFT);
                                        setGraphic(circle);
                                    } else if (amigo.getId().getConvidado().equals(Sessao.usuario.get())) {
                                        HBox hbSolicitacao = new HBox();
                                        Button btAceitarSolicitacao = new Button("Aceitar");
                                        btAceitarSolicitacao.setOnAction((event) -> {
                                            amigo.setAceito(true);
                                            new AmigoDAO().editar(amigo);
                                        });
                                        Button btRecusarSolicitacao = new Button("Recusar");
                                        btRecusarSolicitacao.setOnAction((event) -> {
                                            amigo.setDel(true);
                                            new AmigoDAO().editar(amigo);
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
                };
            }
        });
    }

    public void limpar() {

    }

    public void atualizar() {
        final List<Usuario> amigosPedentesBanco = new AmigoDAO().buscarAmigosPendentes(Sessao.usuario.get()).stream().map((Amigo t) -> {
            if (t.getId().getConvidado().equals(Sessao.usuario.get())) {
                return t.getId().getRemetente();
            } else {
                return t.getId().getConvidado();
            }
        }).collect(Collectors.toList());
        final List<Usuario> amigosAdicionadosBanco = new AmigoDAO().buscarAmigosAceitos(Sessao.usuario.get()).stream().map((Amigo t) -> {
            if (t.getId().getConvidado().equals(Sessao.usuario.get())) {
                return t.getId().getRemetente();
            } else {
                return t.getId().getConvidado();
            }
        }).collect(Collectors.toList());
        final List<Usuario> amigosAdicionados = (List<Usuario>) tiAmigos.getChildren().stream().map(new Function<Object, Usuario>() {
            @Override
            public Usuario apply(Object t) {
                return (Usuario) ((TreeItem) t).getValue();
            }
        }).collect(Collectors.toList());
        final List<Usuario> amigosPendentes = (List<Usuario>) tiPendentes.getChildren().stream().map(new Function<Object, Usuario>() {
            @Override
            public Usuario apply(Object t) {
                return (Usuario) ((TreeItem) t).getValue();
            }
        }).collect(Collectors.toList());
        //Remover
        tiAmigos.getChildren().removeIf((Object t) -> {
            TreeItem treeItem = (TreeItem) t;
            return !amigosAdicionadosBanco.contains(treeItem.getValue());
        });
        tiPendentes.getChildren().removeIf((Object t) -> {
            TreeItem treeItem = (TreeItem) t;
            return !amigosPedentesBanco.contains(treeItem.getValue());
        });
        //Adicionar novos
        amigosPedentesBanco.removeAll(amigosPendentes);
        amigosAdicionadosBanco.removeAll(amigosAdicionados);
        for (Usuario usuario : amigosAdicionadosBanco) {
            adicionar(usuario, tiAmigos);
        }
        for (Usuario usuario : amigosPedentesBanco) {
            adicionar(usuario, tiPendentes);
        }
    }

    private void adicionar(Usuario usuario, TreeItem tiAmigos) {
        TreeItem solicitacao = new TreeItem(usuario);
        tiAmigos.getChildren().add(solicitacao);
    }
}
