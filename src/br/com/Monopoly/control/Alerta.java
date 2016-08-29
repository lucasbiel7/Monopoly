/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.control;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 *
 * @author samuel
 */
public class Alerta {

    public static Alert criarAlert(tipoAlerta ta) {
        String titulo = "Ocorreu um erro !!!";
        String mensagem = "Vish :( algo errado";
        AlertType tipo = Alert.AlertType.INFORMATION;

        switch (ta) {
            case CAMPOVAZIO:
                titulo = "Preencha todos os campos.";
                mensagem = "Há campos vazios, preencha todos os campos para continuar.";
                break;
            case CONFIRMACAO:
                titulo = "Deseja continuar ?";
                mensagem = "Deseja realmente realizar esta ação ?";
                tipo = AlertType.CONFIRMATION;
                break;
            case ERROLOGIN:
                titulo = "Login inválido.";
                mensagem = "Login e/ou Senha invalidos, tente novamente";
                tipo = AlertType.ERROR;
                break;
            case CONCLUIDO:
                titulo = "Concluido com sucesso.";
                mensagem = "Concluido com sucesso.";
                break;
        }
        Alert alert = new Alert(tipo, mensagem);
        alert.setTitle(titulo);

        return alert;

    }

    public static boolean confirmacao(String msg) {
        Alert alert = new Alert(AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO);
        return alert.showAndWait().orElse(ButtonType.NO).equals(ButtonType.YES);
    }

    public enum tipoAlerta {
        ERROLOGIN(),
        CAMPOVAZIO(),
        CONFIRMACAO(),
        CONCLUIDO();
    }
}
