/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.Sessao;
import br.com.Monopoly.control.dao.AmigosDAO;
import br.com.Monopoly.model.entity.Amigos;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

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

    private TreeItem tiAmigos;

    private TreeItem tiPendentes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tiAmigos = new TreeItem("Amigos");
        tiPendentes = new TreeItem("Pendencias de Amizade");
        tvAmigos.setRoot(new TreeItem("Lista de Amizades"));
        List<Amigos> meusAmigos = new AmigosDAO().buscarAmizades(Sessao.usuario);
        for (Amigos meuAmigo : meusAmigos) {
            if (meuAmigo.isAceito()) {
                if (meuAmigo.getId().getConvidado().equals(Sessao.usuario)) {
                    tiAmigos.getChildren().add(meuAmigo.getId().getRemetente());
                } else {
                    tiAmigos.getChildren().add(meuAmigo.getId().getConvidado());
                }
            }else{
                if (meuAmigo.getId().getConvidado().equals(Sessao.usuario)) {
                    tiPendentes.getChildren().add(meuAmigo.getId().getRemetente());
                } else {
                    tiPendentes.getChildren().add(meuAmigo.getId().getConvidado());
                }
            }
        }
        tvAmigos.getRoot().getChildren().add(tiAmigos);
        tvAmigos.getRoot().getChildren().add(tiPendentes);
        
    }

}
