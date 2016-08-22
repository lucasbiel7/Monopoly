/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.control;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

/**
 *
 * @author OCTI-Lucas
 */
public class Validador {

    public static final String mensagemRequired = " não pode ser vazio!";

    public static boolean required(Node... nodes) {
        for (Node node : nodes) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField.getText().isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, textField.getAccessibleText() + mensagemRequired).showAndWait();
                    return false;
                }
            } else if (node instanceof ComboBox) {
                ComboBox comboBox = (ComboBox) node;
                if (comboBox.getValue() == null) {
                    new Alert(Alert.AlertType.ERROR, comboBox.getAccessibleText() + mensagemRequired).showAndWait();
                    return false;
                }
            } else if (node instanceof TextArea) {
                TextArea textArea = (TextArea) node;
                if (textArea.getText().isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, textArea.getAccessibleText() + mensagemRequired).showAndWait();
                    return false;
                }
            } else if (node instanceof PasswordField) {
                PasswordField textArea = (PasswordField) node;
                if (textArea.getText().isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, textArea.getAccessibleText() + mensagemRequired).showAndWait();
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isEmail(String text) {
        return text.matches("^(\\w{1,}@\\w{1,}\\.\\w{1,})$");
    }

    public static boolean hasDigit(String text) {
        return text.matches(".*([\\d{1,}]).*");
    }

    public static boolean hasWorld(String text) {
        return text.matches(".*([[a-zA-Z]{1,}]).*");
    }

    public static boolean hasSpecialCharacter(String text) {
        return text.matches(".*([\\W{1,}]).*");
    }

    public static void limiter(TextInputControl textInputControl, int max) {
        textInputControl.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length() > max) {
                textInputControl.setText(oldValue);
            }
        });
    }

    public static boolean confirmPassword(PasswordField pfSenha, PasswordField pfConfirmaSenha) {
        return pfSenha.getText().equals(pfConfirmaSenha.getText());
    }

    public static boolean uniqueField(Class classe, String field, Object value) {
//        return new GenericaDAO<>(classe).pegarPorCampo(field, value).isEmpty();
        return false;
    }
}
