/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.Alerta;
import br.com.Monopoly.control.dao.SalaDAO;
import br.com.Monopoly.model.entity.Sala;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author samuel
 */
public class ConfiguracaoSalaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private Label lbTitulo;

    @FXML
    private HBox hbLinhaNumeroDeJogadores;

    @FXML
    private Label lbNumeroDeJogadores;

    @FXML
    private Spinner<Integer> spNumeroDeJogadores;

    @FXML
    private Button btCancelar;

    @FXML
    private Button btSalvar;

    private Stage me;

    private Sala sala;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            me = (Stage) apPrincipal.getScene().getWindow();
            sala = (Sala) apPrincipal.getUserData();
            spNumeroDeJogadores.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 5, sala.getCapacidade()));
        });

        
    }

    @FXML
    void btCancelarEvent(ActionEvent event) {
        me.close();
    }

    @FXML
    void btSalvarEvent(ActionEvent event) {
        if (sala != null) {
            int quantidadeJogador = spNumeroDeJogadores.getValue();

            sala.setCapacidade(quantidadeJogador);
            new SalaDAO().editar(sala);
            Alerta.criarAlert(Alerta.tipoAlerta.CONCLUIDO).show();
            me.close();
        } else {
            System.out.println("null");
        }
    }

}
