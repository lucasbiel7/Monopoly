/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.control;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OCTI-Lucas
 */
public class Seguranca {

    public static String criptografar(String dado) {
        try {
            if (dado != null) {
                MessageDigest criptografia = MessageDigest.getInstance("SHA1");
                StringBuilder senhaCriptografada = new StringBuilder();
                for (byte digito : criptografia.digest(dado.getBytes(Charset.forName("UTF-8")))) {
                    senhaCriptografada.append(String.format("%02X", 0xFF & digito));
                }
                return senhaCriptografada.toString();
            } else {
                return dado;
            }
        } catch (NoSuchAlgorithmException | NullPointerException ex) {
            Logger.getLogger(Seguranca.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
