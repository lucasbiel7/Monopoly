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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author OCTI-Lucas
 */
public class VisualizarSalaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private FlowPane fpSalas;
    @FXML
    private Pagination pgSalas;
    private List<Sala> salas;
    private Timeline carregarSalas;
    private int linha = 3;
    private int coluna = 3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        salas = new ArrayList<>();
        carregarSalas();
        pgSalas.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int inicio = newValue.intValue() * (linha * coluna);
                int fim = newValue.intValue() * (linha * coluna) + 9;
                inserirSalas(salas.subList(inicio, fim > salas.size() ? salas.size() : fim));
            }
        });
        fpSalas.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                coluna = (int) ((newValue.getWidth()) / 275);
                linha = (int) ((newValue.getHeight()) / 130);
            }
        });
    }

    @FXML
    public void btAtualizarActionEvent(ActionEvent actionEvent) {
        carregarSalas();
    }

    public void carregarSalas() {
        salas = new SalaDAO().buscarTodos();
        int pages = (int) Math.ceil(salas.size() * 1d / (linha * coluna));
        pgSalas.setPageCount(pages);
        int inicio = pgSalas.getCurrentPageIndex() * (linha * coluna);
        int fim = pgSalas.getCurrentPageIndex() * (linha * coluna) + 9;
        inserirSalas(salas.subList(inicio, fim > salas.size() ? salas.size() : fim));
    }

    private void inserirSalas(List<Sala> salas) {
        fpSalas.getChildren().clear();
        for (Sala sala : salas) {
            fpSalas.getChildren().add(GerenciadorDeJanelas.carregarComponente("DescricaoSala", sala));
        }
    }
}
