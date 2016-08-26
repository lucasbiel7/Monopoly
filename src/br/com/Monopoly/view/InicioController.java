/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.GerenciadorDeJanelas;
import br.com.Monopoly.control.Sessao;
import br.com.Monopoly.control.dao.JogadorDAO;
import br.com.Monopoly.control.dao.SalaDAO;
import br.com.Monopoly.model.Funcionalidade;
import br.com.Monopoly.model.entity.Jogador;
import br.com.Monopoly.model.entity.Sala;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI-Lucas
 */
public class InicioController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Button btCriarSala;
    @FXML
    private SplitPane spPainel;
    @FXML
    private ScrollPane spPerfil;
    @FXML
    private ScrollPane spConteudo;
    @FXML
    private ScrollPane spAmigos;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btCriarSala.setVisible(Sessao.verificarPermissao(Funcionalidade.CRIARSALA));
        GerenciadorDeJanelas.inserirPainel(spPerfil, GerenciadorDeJanelas.carregarComponente("PainelPerfil"));
    }

    @FXML
    public void btCriarSalaActionEvent(ActionEvent actionEvent) {
        Sala sala = new Sala();
        sala.setCapacidade(5);
        sala.setJogando(false);
        new SalaDAO().salvar(sala);
        Jogador jogador = new Jogador();
        jogador.setId(new Jogador.JogadorID(Sessao.usuario, sala));
        jogador.setCriador(true);
        jogador.setNumero(0);
        new JogadorDAO().salvar(jogador);
        GerenciadorDeJanelas.inserirPainel(Sessao.container, GerenciadorDeJanelas.carregarComponente("Sala", sala));
    }
}
