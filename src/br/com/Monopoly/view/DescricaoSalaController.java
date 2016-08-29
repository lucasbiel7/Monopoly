/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.GerenciadorDeImagem;
import br.com.Monopoly.control.GerenciadorDeJanelas;
import br.com.Monopoly.control.Sessao;
import br.com.Monopoly.control.dao.JogadorDAO;
import br.com.Monopoly.model.entity.Jogador;
import br.com.Monopoly.model.entity.Sala;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author OCTI-Lucas
 */
public class DescricaoSalaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private GridPane gpPessoas;
    @FXML
    private ImageView ivMaster;
    @FXML
    private Label lbMaster;

    private Sala sala;

    private Timeline atualizarPessoas;
    private List<Circle> pessoas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        pessoas = new ArrayList<>();
        Platform.runLater(() -> {
            sala = (Sala) apPrincipal.getUserData();
            carregarSala();
            atualizarPessoas = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    atualizarSala();
                }
            }));
            atualizarPessoas.setCycleCount(Timeline.INDEFINITE);
            atualizarPessoas.play();
            Sessao.container.contentProperty().addListener((ObservableValue<? extends Node> observable, Node oldValue, Node newValue) -> {
                atualizarPessoas.stop();
            });
            apPrincipal.getParent().getChildrenUnmodifiable().addListener((ListChangeListener.Change<? extends Node> c) -> {
                if (!c.getList().contains(apPrincipal)) {
                    atualizarPessoas.stop();
                }
            });
        });
    }

    private void carregarSala() {
        Image image = GerenciadorDeImagem.carregarImage("semusuario.png");
        for (int i = 0; i < sala.getCapacidade(); i++) {
            Circle circle = new Circle(20);
            circle.setFill(new ImagePattern(image));
            gpPessoas.add(circle, i % 5, i / 5);
            pessoas.add(circle);
        }
    }

    private void atualizarSala() {
        List<Jogador> jogadores = new JogadorDAO().pegarPorSala(sala);
        for (Jogador jogador : jogadores) {
            if (jogador.isCriador()) {
                if (!jogador.equals(lbMaster.getUserData())) {
                    ivMaster.setImage(GerenciadorDeImagem.carregarImage(jogador.getId().getUsuario().getFoto()));
                    lbMaster.setText(jogador.getId().getUsuario().getNome());
                    lbMaster.setUserData(jogador);
                }
            }
            if (!jogador.equals(pessoas.get(jogador.getNumero()).getUserData())) {
                Circle circle = pessoas.get(jogador.getNumero());
                circle.setFill(new ImagePattern(GerenciadorDeImagem.carregarImage(jogador.getId().getUsuario().getFoto())));
                circle.setUserData(jogador);
            }
        }
    }

    @FXML
    public void btEntrarActionEvent(ActionEvent actionEvent) {
        Jogador jogador = new Jogador();
        jogador.setId(new Jogador.JogadorID(Sessao.usuario.get(), sala));
        jogador.setDel(false);
        for (int i = 0; i < sala.getCapacidade(); i++) {
            if (new JogadorDAO().pegarPorSalaNumero(sala, i) == null) {
                jogador.setNumero(i);
            }
        }
        if (new JogadorDAO().buscarPorID(new Jogador.JogadorID(Sessao.usuario.get(), sala)) == null) {
            new JogadorDAO().salvar(jogador);
        } else {
            new JogadorDAO().editar(jogador);
        }
        GerenciadorDeJanelas.inserirPainel(Sessao.container, GerenciadorDeJanelas.carregarComponente("Sala", sala));
    }
}
