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
public enum Funcionalidade {
    CADASTRAR("Cadastrar"),
    CRIARSALA("Criar Sala"),
    APAGAR("Apagar"),
    BATEPAPO("Bate-Papo");

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private Funcionalidade() {
    }

    private Funcionalidade(String nome) {
        this.nome = nome;
    }
}
