/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.GerenciadorDeJanelas;
import br.com.Monopoly.control.Sessao;
import br.com.Monopoly.control.dao.AmigosDAO;
import br.com.Monopoly.model.entity.Amigos;
import br.com.Monopoly.model.entity.Usuario;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    private TreeItem tiAmigos;

    private TreeItem tiPendentes;

    private Stage me;

    private List<Usuario> amigos;

    private List<Usuario> pendentes;

    private List<Amigos> meusAmigos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tiAmigos = new TreeItem("Amigos");
        tiPendentes = new TreeItem("Pendencias de Amizade");
        tvAmigos.setRoot(new TreeItem("Lista de Amizades"));
        amigos = new ArrayList<>();
        pendentes = new ArrayList<>();

        inicializadorPainelAmigos();

        tvAmigos.getRoot().getChildren().add(tiAmigos);
        tvAmigos.getRoot().getChildren().add(tiPendentes);

        Timeline tlAtualizaAmigos = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Atualizar();
            }
        }));
        tlAtualizaAmigos.setCycleCount(Timeline.INDEFINITE);
        tlAtualizaAmigos.play();
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
    }

    public void limpar() {

    }

    public void Atualizar() {

    }

    public void inicializadorPainelAmigos() {
        tiAmigos.getChildren().clear();
        tiPendentes.getChildren().clear();
        meusAmigos = new AmigosDAO().buscarAmizades(Sessao.usuario.get());

        for (Amigos meuAmigo : meusAmigos) {
            if (meuAmigo.isAceito()) {
                if (meuAmigo.getId().getConvidado().equals(Sessao.usuario.get())) {
                    amigos.add(meuAmigo.getId().getRemetente());
                    tiAmigos.getChildren().add(new TreeItem(meuAmigo.getId().getRemetente()));
                } else {
                    tiAmigos.getChildren().add(new TreeItem(meuAmigo.getId().getConvidado()));
                    amigos.add(meuAmigo.getId().getConvidado());
                }
            } else if (meuAmigo.getId().getConvidado().equals(Sessao.usuario.get())) {

                HBox hbSolicitacao = new HBox();
                TreeItem solicitacao = new TreeItem(meuAmigo.getId().getRemetente());
                Button btAceitarSolicitacao = new Button("Aceitar");
                btAceitarSolicitacao.setOnAction((event) -> {
                    meuAmigo.setAceito(true);
                    new AmigosDAO().editar(meuAmigo);
                    inicializadorPainelAmigos();
                });

                Button btRecusarSolicitacao = new Button("Recusar");
                btRecusarSolicitacao.setOnAction((event) -> {
                    meuAmigo.setDel(true);
                    new AmigosDAO().editar(meuAmigo);
                    inicializadorPainelAmigos();
                });

                hbSolicitacao.getChildren().add(btAceitarSolicitacao);
                hbSolicitacao.getChildren().add(btRecusarSolicitacao);
                hbSolicitacao.setSpacing(20d);
                solicitacao.setGraphic(hbSolicitacao);
                pendentes.add(meuAmigo.getId().getRemetente());
                tiPendentes.getChildren().add(solicitacao);

            } else {
                pendentes.add(meuAmigo.getId().getConvidado());
                tiPendentes.getChildren().add(new TreeItem(meuAmigo.getId().getConvidado()));
            }

        }
    }

}
