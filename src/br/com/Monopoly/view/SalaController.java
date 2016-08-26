/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.Sessao;
import br.com.Monopoly.control.dao.JogadorDAO;
import br.com.Monopoly.model.entity.Jogador;
import br.com.Monopoly.model.entity.Sala;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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

    private Sala sala;
    private List<ImageView> imagemJogadores;
    private List<Jogador> jogadores;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            sala = (Sala) apPrincipal.getUserData();
            carregarEspacoJogadores();
        });
    }

    @FXML
    public void btConfiguracaoActionEvent(ActionEvent actionEvent) {

    }

    @FXML
    public void btComecarActionEvent(ActionEvent actionEvent) {

    }

    @FXML
    public void btFecharSalaActionEvent(ActionEvent actionEvent) {
        Jogador jogador = new JogadorDAO().buscarPorID(new Jogador.JogadorID(Sessao.usuario, sala));
        new JogadorDAO().deletar(jogador);
    }

    private void carregarEspacoJogadores() {
        for (int i = 0; i < sala.getCapacidade(); i++) {
            ImageView imageView = new ImageView();
            imagemJogadores.add(imageView);
        }
    }
}
