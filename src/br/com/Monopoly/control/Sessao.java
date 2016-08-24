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
import java.util.List;

/**
 *
 * @author OCTI-Lucas
 */
public class Sessao {

    public static Usuario usuario;

    public static boolean acesso(Funcionalidade funcionalidade) {
        return false;
    }

    public static boolean verificarPermissao(Funcionalidade func) {
        if (usuario != null) {
            Permissao permissao = new PermissaoDAO().buscarPorID(new Permissao.PermissaoID(func, usuario));

            if (permissao != null) {
                return true;
            } else {
                return false;
            }
            
        } else {
            return false;
        }
    }
}
