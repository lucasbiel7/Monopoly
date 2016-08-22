/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.model;

/**
 *
 * @author OCTI-Lucas
 */
public enum Databases {

    DBLucas("root", "OC2016", "monopoly");

    private String usuario;
    private String senha;
    private String banco;

    private Databases(String usuario, String senha, String banco) {
        this.usuario = usuario;
        this.senha = senha;
        this.banco = banco;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

}
