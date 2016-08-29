/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.view;

import br.com.Monopoly.control.GerenciadorDeJanelas;
import br.com.Monopoly.control.Seguranca;
import br.com.Monopoly.control.Sessao;
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
        //Desenvolvedor
//        Sessao.usuario = new UsuarioDAO().login("master", "123");
        Sessao.usuario.set(new UsuarioDAO().login("lucas2", Seguranca.criptografar("123")));
        if(Sessao.usuario.getValue() == null){
            Sessao.usuario.set(new UsuarioDAO().login("s", Seguranca.criptografar("s")));
            if(Sessao.usuario.getValue() == null){
                Sessao.usuario.setValue(new UsuarioDAO().buscarTodos().get(0));
            }
        }
        
        GerenciadorDeJanelas.abrirJanela(GerenciadorDeJanelas.carregarComponente("Principal"), "Tela principal", GerenciadorDeJanelas.Tipo.MAXIMIZED, GerenciadorDeJanelas.Tipo.UNDECORATED).show();
//        GerenciadorDeJanelas.abrirJanela(GerenciadorDeJanelas.carregarComponente("GerenciarPerfil"), "Perfil - Monopoly Deal", GerenciadorDeJanelas.Tipo.EXIT_ON_CLOSE, GerenciadorDeJanelas.Tipo.UNRESIZABLE).show();
//Instalador
//        GerenciadorDeJanelas.abrirJanela(GerenciadorDeJanelas.carregarComponente("Login"), "Monopoly Deal", GerenciadorDeJanelas.Tipo.EXIT_ON_CLOSE,GerenciadorDeJanelas.Tipo.UNRESIZABLE).show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
