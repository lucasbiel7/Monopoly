/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.control;

import java.io.ByteArrayInputStream;
import javafx.scene.image.Image;

/**
 *
 * @author OCTI-Lucas
 */
public class GerenciadorDeImagem {

    public static String IMAGE = "/br.com.Monopoly.view.image/".replace(".", "/");

    public static Image carregarImage(String image) {
        return new Image(GerenciadorDeImagem.class.getResourceAsStream(IMAGE + image));
    }

    public static Image carregarImage(byte[] image) {
        if (image == null) {
            return null;
        }
        return new Image(new ByteArrayInputStream(image));
    }

}
