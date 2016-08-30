/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.control;

import br.com.Monopoly.control.dao.AmigosDAO;
import br.com.Monopoly.control.dao.PermissaoDAO;
import br.com.Monopoly.model.Funcionalidade;
import br.com.Monopoly.model.entity.Amigos;
import br.com.Monopoly.model.entity.Permissao;
import br.com.Monopoly.model.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;

/**
 *
 * @author OCTI-Lucas
 */
public class Sessao {

    public static ScrollPane container;
    public static SimpleObjectProperty<Usuario> usuario = new SimpleObjectProperty<>();
    public static final Image fotoPadrao;

    static {
        fotoPadrao = GerenciadorDeImagem.carregarImage("semusuario.png");
    }

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

    public static List<Usuario> meusAmigos() {
        List<Amigos> minhasAmizades = new AmigosDAO().buscarAmizades(usuario.get());
        List<Usuario> listaDeAmigos = new ArrayList<>();

        for (Amigos meuAmigo : minhasAmizades) {
            if (meuAmigo.getId().getConvidado().equals(Sessao.usuario.get())) {
                listaDeAmigos.add(meuAmigo.getId().getRemetente());
            } else {
                listaDeAmigos.add(meuAmigo.getId().getConvidado());
            }
        }

        return listaDeAmigos;
    }
}
