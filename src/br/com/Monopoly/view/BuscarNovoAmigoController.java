/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.Alerta;
import br.com.Monopoly.control.GerenciadorDeImagem;
import br.com.Monopoly.control.Sessao;
import br.com.Monopoly.control.dao.AmigoDAO;
import br.com.Monopoly.control.dao.UsuarioDAO;
import br.com.Monopoly.model.entity.Amigo;
import br.com.Monopoly.model.entity.Usuario;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.layout.HBox;
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
                            HBox hbGrade = new HBox();
                            hbGrade.setMinWidth(40d);
                            hbGrade.setSpacing(35.0);
                            ivFoto.setFitHeight(30.0);
                            ivFoto.setFitWidth(30.0);
                            ivFoto.setPreserveRatio(true);
                            ivFoto.setSmooth(true);
                            Label lbNome = new Label(item.getNome());
                            lbNome.setPrefWidth(200d);
                            Button btAdd = new Button("Solicitar Amizidade");
                            btAdd.setOnAction((event)->{
                                Amigo novaAmizadade = new Amigo(new Amigo.AmigosID(Sessao.usuario.get(), item));
                                novaAmizadade.setAceito(false);
                                new AmigoDAO().salvar(novaAmizadade);
                                Alerta.criarAlert(Alerta.tipoAlerta.CONCLUIDO).show();
                            });
                            hbGrade.getChildren().add(ivFoto);
                            hbGrade.getChildren().add(lbNome);
                            hbGrade.getChildren().add(btAdd);
                            
                            setGraphic(hbGrade);

                        }
                    }
                };
            }
        });

    }

    @FXML
    void btBuscarEvent(ActionEvent event) {
        buscaDeUsuarios = new UsuarioDAO().buscarPorNome(tfNomeBusca.getText());
        List<Usuario> exilados = new ArrayList<>();
        exilados.addAll(Sessao.meusAmigos());
        exilados.add(Sessao.usuario.get());
        buscaDeUsuarios.removeAll(exilados);
        lvBusca.getItems().setAll(buscaDeUsuarios);
        lvBusca.refresh();
    }

    @FXML
    void btFecharEvent(ActionEvent event) {
        me.close();
    }

}
