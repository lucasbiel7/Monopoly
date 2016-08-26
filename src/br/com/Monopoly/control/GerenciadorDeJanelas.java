/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.control;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 *
 * @author OCTI-Lucas
 */
public class GerenciadorDeJanelas {

    public static String VIEW = "/br.com.Monopoly.control/".replace(".", "/").replace("control", "view");

    public static Parent carregarComponente(String fxml) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(GerenciadorDeJanelas.class.getResource(VIEW + fxml + ".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(GerenciadorDeJanelas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parent;
    }

    public static Parent carregarComponente(String fxml, Object data) {
        Parent parent = carregarComponente(fxml);
        if (parent != null) {
            parent.setUserData(data);
        }
        return parent;
    }

    public enum Tipo {
        EXIT_ON_CLOSE, MAXIMIZED, MODAL, UNDECORATED, UNRESIZABLE;
    }

    public static Stage abrirJanela(Parent componente, String title, Tipo... tipos) {
        Stage stage = new Stage();
        Scene scene = new Scene(componente);
        stage.setScene(scene);
        stage.setTitle(title);
        for (Tipo tipo : tipos) {
            switch (tipo) {
                case EXIT_ON_CLOSE:
                    stage.setOnCloseRequest((WindowEvent event) -> {
                        Platform.exit();
                        System.exit(0);
                    });
                    break;
                case MAXIMIZED:
                    stage.setMaximized(true);
                    break;
                case MODAL:
                    stage.initModality(Modality.WINDOW_MODAL);
                    break;
                case UNDECORATED:
                    stage.initStyle(StageStyle.UNDECORATED);
                    break;
                case UNRESIZABLE:
                    stage.setResizable(false);
                    break;
            }
        }
        return stage;
    }

    public static void inserirPainel(ScrollPane scrollPane, Parent parent) {
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(parent);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), parent);
        fadeTransition.setFromValue(0d);
        fadeTransition.setToValue(1d);
        fadeTransition.playFromStart();
    }
}
