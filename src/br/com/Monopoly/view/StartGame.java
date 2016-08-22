/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.dao.UsuarioDAO;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author OCTI-Lucas
 */
public class StartGame extends Application {

    @Override
    public void start(Stage primaryStage) {
        new UsuarioDAO().buscarTodos();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
