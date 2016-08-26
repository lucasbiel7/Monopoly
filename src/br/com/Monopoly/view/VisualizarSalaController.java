/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.GerenciadorDeJanelas;
import br.com.Monopoly.control.dao.SalaDAO;
import br.com.Monopoly.model.entity.Sala;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author OCTI-Lucas
 */
public class VisualizarSalaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    private Timeline carregarSalas;

    @FXML
    private GridPane gpSalas;

    private List<Sala> salasAdicionadas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        salasAdicionadas = new ArrayList<>();
        carregarSalas();
    }

    @FXML
    public void btAtualizarActionEvent(ActionEvent actionEvent) {

    }

    public void carregarSalas() {
        for (Sala sala : new SalaDAO().buscarTodos()) {
            if (!salasAdicionadas.contains(sala)) {
                salasAdicionadas.add(sala);
                gpSalas.add(GerenciadorDeJanelas.carregarComponente("DescricaoSala"), salasAdicionadas.indexOf(sala), 0);
            }

        }
    }
}
