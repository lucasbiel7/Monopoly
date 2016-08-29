/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.GerenciadorDeImagem;
import br.com.Monopoly.control.dao.UsuarioDAO;
import br.com.Monopoly.model.entity.Usuario;
import com.sun.javafx.collections.ElementObservableListDecorator;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author samuel
 */
public class BuscarNovoAmigoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private Label lbTitulo;

    @FXML
    private TextField tfNomeBusca;

    @FXML
    private Button btBuscar;

    @FXML
    private ListView<Usuario> lvBusca;

    @FXML
    private Button btFechar;

    private List<Usuario> buscaDeUsuarios;

    private Stage me;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                me = (Stage) apPrincipal.getScene().getWindow();
            }
        });

        buscaDeUsuarios = new ArrayList<>();

        lvBusca.setCellFactory(new Callback<ListView<Usuario>, ListCell<Usuario>>() {
            @Override
            public ListCell<Usuario> call(ListView<Usuario> param) {
                return new ListCell<Usuario>() {
                    @Override
                    protected void updateItem(Usuario item, boolean empty) {
                        super.updateItem(item, empty);
                        setMinHeight(40.0);
                        setPrefHeight(40.0);
                        setMaxHeight(40.0);
                        if (empty) {
                            setText("");
                            setGraphic(null);
                        } else {
                            ImageView ivFoto = new ImageView(GerenciadorDeImagem.carregarImage(item.getFoto()));
                            ivFoto.setFitHeight(30.0);
                            ivFoto.setFitWidth(30.0);
                            ivFoto.setPreserveRatio(true);
                            ivFoto.setSmooth(true);
                            setGraphic(ivFoto);
                            setText(item.getNome());

                        }
                    }
                };
            }
        });

    }

    @FXML
    void btBuscarEvent(ActionEvent event) {
        lvBusca.getItems().setAll(new UsuarioDAO().buscarPorNome(tfNomeBusca.getText()));
        lvBusca.refresh();
    }

    @FXML
    void btFecharEvent(ActionEvent event) {
        me.close();
    }

}
