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
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tiAmigos = new TreeItem("Amigos");
        tiPendentes = new TreeItem("Pendencias de Amizade");
        tvAmigos.setRoot(new TreeItem("Lista de Amizades"));
        List<Amigos> meusAmigos = new AmigosDAO().buscarAmizades(Sessao.usuario.get());
        int amigos = 0;
        int pendentes = 0;
        for (Amigos meuAmigo : meusAmigos) {
            if (meuAmigo.isAceito()) {
                if (meuAmigo.getId().getConvidado().equals(Sessao.usuario.get())) {
                    tiAmigos.getChildren().add(meuAmigo.getId().getRemetente());
                } else {
                    tiAmigos.getChildren().add(meuAmigo.getId().getConvidado());
                }
                amigos++;
            }else{
                if (meuAmigo.getId().getConvidado().equals(Sessao.usuario.get())) {
                    tiPendentes.getChildren().add(meuAmigo.getId().getRemetente());
                } else {
                    tiPendentes.getChildren().add(meuAmigo.getId().getConvidado());
                }
                pendentes++;
            }
        }
       
        tvAmigos.getRoot().getChildren().add(tiAmigos);
        tvAmigos.getRoot().getChildren().add(tiPendentes);
        
        btAddAmigos.setOnAction((event)->{
            Stage tela = GerenciadorDeJanelas.abrirJanela(GerenciadorDeJanelas.carregarComponente("BuscarNovoAmigo"), "Encontre seu Amigo !!", GerenciadorDeJanelas.Tipo.UNDECORATED,GerenciadorDeJanelas.Tipo.UNRESIZABLE);
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

}
