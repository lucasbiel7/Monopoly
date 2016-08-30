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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
    private TreeView tvAmigos;
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
        inicializadorPainelAmigos();
        tlAtualizaAmigos = new Timeline(new KeyFrame(Duration.seconds(2), (ActionEvent event) -> {
            atualizar();
        }));
        tlAtualizaAmigos.setCycleCount(Timeline.INDEFINITE);
        tlAtualizaAmigos.play();
    }

    public void limpar() {

    }

    public void atualizar() {
        List<Usuario> amigos = new AmigoDAO().buscarAmizades(Sessao.usuario.get()).stream().map((Amigo t) -> {
            if (t.getId().getConvidado().equals(Sessao.usuario.get())) {
                return t.getId().getRemetente();
            } else {
                return t.getId().getConvidado();
            }
        }).collect(Collectors.toList());
        List<Usuario> amigosAdicionados = (List<Usuario>) tiAmigos.getChildren().stream().map(new Function<Object, Usuario>() {
            @Override
            public Usuario apply(Object t) {
                return (Usuario) ((TreeItem) t).getValue();
            }
        }).collect(Collectors.toList());
        List<Usuario> amigosPendentes = (List<Usuario>) tiPendentes.getChildren().stream().map(new Function<Object, Usuario>() {
            @Override
            public Usuario apply(Object t) {
                return (Usuario) ((TreeItem) t).getValue();
            }
        }).collect(Collectors.toList());
        
    }

    public void inicializadorPainelAmigos() {
        tiAmigos.getChildren().clear();
        tiPendentes.getChildren().clear();
        meusAmigos = new AmigoDAO().buscarAmizades(Sessao.usuario.get());
        System.out.println(meusAmigos.size());
        for (Amigo meuAmigo : meusAmigos) {
            if (meuAmigo.isAceito()) {
                if (meuAmigo.getId().getConvidado().equals(Sessao.usuario.get())) {
                    tiAmigos.getChildren().add(new TreeItem(meuAmigo.getId().getRemetente()));
                } else {
                    tiAmigos.getChildren().add(new TreeItem(meuAmigo.getId().getConvidado()));
                }
            } else if (meuAmigo.getId().getConvidado().equals(Sessao.usuario.get())) {
                HBox hbSolicitacao = new HBox();
                TreeItem solicitacao = new TreeItem(meuAmigo.getId().getRemetente());
                Button btAceitarSolicitacao = new Button("Aceitar");
                btAceitarSolicitacao.setOnAction((event) -> {
                    meuAmigo.setAceito(true);
                    new AmigoDAO().editar(meuAmigo);
                    inicializadorPainelAmigos();
                });
                Button btRecusarSolicitacao = new Button("Recusar");
                btRecusarSolicitacao.setOnAction((event) -> {
                    meuAmigo.setDel(true);
                    new AmigoDAO().editar(meuAmigo);
                    inicializadorPainelAmigos();
                });
                hbSolicitacao.getChildren().add(btAceitarSolicitacao);
                hbSolicitacao.getChildren().add(btRecusarSolicitacao);
                hbSolicitacao.setSpacing(20d);
                solicitacao.setGraphic(hbSolicitacao);
                tiPendentes.getChildren().add(solicitacao);
            } else {
                tiPendentes.getChildren().add(new TreeItem(meuAmigo.getId().getConvidado()));
            }

        }
    }

}
