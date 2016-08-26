/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.control;

import br.com.Monopoly.control.dao.PermissaoDAO;
import br.com.Monopoly.model.Funcionalidade;
import br.com.Monopoly.model.entity.Permissao;
import br.com.Monopoly.model.entity.Usuario;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ScrollPane;

/**
 *
 * @author OCTI-Lucas
 */
public class Sessao {

    public static ScrollPane container;
    public static SimpleObjectProperty<Usuario> usuario=new SimpleObjectProperty<>();

    public static boolean verificarPermissao(Funcionalidade func) {
        if (usuario.getValue() != null) {
            Permissao permissao = new PermissaoDAO().buscarPorID(new Permissao.PermissaoID(func, usuario.getValue()));
            if (permissao != null) {
                return !permissao.isDel();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
