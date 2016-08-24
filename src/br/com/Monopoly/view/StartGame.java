/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.GerenciadorDeJanelas;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author OCTI-Lucas
 */
public class StartGame extends Application {

    @Override
    public void start(Stage primaryStage) {
//        GerenciadorDeJanelas.abrirJanela(GerenciadorDeJanelas.carregarComponente("Inicio"), "Monopoly Deal", GerenciadorDeJanelas.Tipo.EXIT_ON_CLOSE,GerenciadorDeJanelas.Tipo.UNRESIZABLE).show();
        GerenciadorDeJanelas.abrirJanela(GerenciadorDeJanelas.carregarComponente("Cadastrar"), "Monopoly Deal", GerenciadorDeJanelas.Tipo.EXIT_ON_CLOSE, GerenciadorDeJanelas.Tipo.UNRESIZABLE).show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
