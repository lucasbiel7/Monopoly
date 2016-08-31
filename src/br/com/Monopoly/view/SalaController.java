/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.Alerta;
import br.com.Monopoly.control.GerenciadorDeImagem;
import br.com.Monopoly.control.GerenciadorDeJanelas;
import br.com.Monopoly.control.Sessao;
import br.com.Monopoly.control.dao.JogadorDAO;
import br.com.Monopoly.control.dao.SalaDAO;
import br.com.Monopoly.model.entity.Jogador;
import br.com.Monopoly.model.entity.Sala;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author OCTI-Lucas
 */
public class SalaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private GridPane gpUsuarios;
    @FXML
    private ImageView ivMaster;
    @FXML
    private Label lbMaster;

    private Sala sala;
    private List<Label> painelJogadores;
    private List<Jogador> jogadores;
private Stage me;
    private Timeline atualizarSala;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            me = (Stage) apPrincipal.getScene().getWindow();
            
            sala = (Sala) apPrincipal.getUserData();
            jogadores = new ArrayList<>();
            carregarEspacoJogadores();
            atualizarSala();
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    Jogador jogador = new JogadorDAO().buscarPorID(new Jogador.JogadorID(Sessao.usuario.getValue(), sala));
                    if (new JogadorDAO().pegarPorSala(sala).size() == 1) {
                        new JogadorDAO().deletar(jogador);
                        new SalaDAO().deletar(sala);
                    } else {
                        new JogadorDAO().deletar(jogador);
                        if (jogador.isCriador()) {
                            List<Jogador> jogadores = new JogadorDAO().pegarPorSala(sala);
                            if (!jogadores.isEmpty()) {
                                jogadores.get(0).setCriador(true);
                                new JogadorDAO().editar(jogadores.get(0));
                            }
                        }
                        GerenciadorDeJanelas.inserirPainel(Sessao.container, GerenciadorDeJanelas.carregarComponente("Inicio"));
                    }
                    atualizarSala.stop();
                }
            }));
        });
    }

    @FXML
    public void btConfiguracaoActionEvent(ActionEvent actionEvent) {
            Stage stConfiguracao = GerenciadorDeJanelas.abrirJanela(GerenciadorDeJanelas.carregarComponente("ConfiguracaoSala"), "Configuração de Sala", GerenciadorDeJanelas.Tipo.MODAL,GerenciadorDeJanelas.Tipo.UNDECORATED,GerenciadorDeJanelas.Tipo.UNRESIZABLE);
            stConfiguracao.initOwner(me);
            stConfiguracao.show();
    }

    @FXML
    public void btComecarActionEvent(ActionEvent actionEvent) {

    }

    @FXML
    public void btFecharSalaActionEvent(ActionEvent actionEvent) {
        Jogador jogador = new JogadorDAO().buscarPorID(new Jogador.JogadorID(Sessao.usuario.getValue(), sala));
        if (new JogadorDAO().pegarPorSala(sala).size() == 1) {
            if (Alerta.confirmacao("Deseja realmente sair da sala?\n"
                    + "Ao sair dessa sala ela também será apagada \n"
                    + "porque você é o ultimo membro!")) {
                new JogadorDAO().deletar(jogador);
                new SalaDAO().deletar(sala);
                atualizarSala.stop();
                GerenciadorDeJanelas.inserirPainel(Sessao.container, GerenciadorDeJanelas.carregarComponente("Inicio"));
            }
        } else {
            new JogadorDAO().deletar(jogador);
            if (jogador.isCriador()) {
                List<Jogador> jogadores = new JogadorDAO().pegarPorSala(sala);
                if (!jogadores.isEmpty()) {
                    jogadores.get(0).setCriador(true);
                    new JogadorDAO().editar(jogadores.get(0));
                }
            }
            atualizarSala.stop();
            GerenciadorDeJanelas.inserirPainel(Sessao.container, GerenciadorDeJanelas.carregarComponente("Inicio"));
        }

    }

    private void carregarEspacoJogadores() {
        painelJogadores = new ArrayList<>();
        for (int i = 0; i < sala.getCapacidade(); i++) {
            Label label = new Label();
            Circle circle = new Circle(50);
            circle.setFill(new ImagePattern(Sessao.fotoPadrao));
            label.setGraphic(circle);
            label.setText("Sem usuário");
            label.setWrapText(true);
            painelJogadores.add(label);
            label.setContentDisplay(ContentDisplay.TOP);
            gpUsuarios.add(label, i % 5, i / 5);
        }
    }

    private void atualizarSala() {
        atualizarSala = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
            atualizarJogadores();
        }));
        atualizarSala.setCycleCount(Timeline.INDEFINITE);
        atualizarSala.play();
    }

    private void atualizarJogadores() {
        jogadores = new JogadorDAO().pegarPorSala(sala);
        for (Jogador jogador : jogadores) {
            if (jogador.isCriador()) {
                if (!jogador.equals(lbMaster.getUserData())) {
                    ivMaster.setImage(GerenciadorDeImagem.carregarImage(jogador.getId().getUsuario().getFoto()));
                    lbMaster.setText(jogador.getId().getUsuario().getNome());
                    lbMaster.setUserData(jogador);
                }
            }
            if (!jogador.equals(painelJogadores.get(jogador.getNumero()).getUserData())) {
                Label label = painelJogadores.get(jogador.getNumero());
                label.setText(jogador.getId().getUsuario().getNome());
                Circle imageView = (Circle) label.getGraphic();
                imageView.setFill(new ImagePattern(GerenciadorDeImagem.carregarImage(jogador.getId().getUsuario().getFoto())));
                label.setUserData(jogador);
            }
        }
    }

}
