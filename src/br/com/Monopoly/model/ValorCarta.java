/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.model;

/**
 *
 * @author samuel
 */
public enum ValorCarta {
    MIL(1),
    DOIS(2),
    TRES(3),
    QUATRO(4),
    CINCO(5),
    DEZ(10),
    SEMVALOR();
    
    private int valor;

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    private ValorCarta() {
    }

    private ValorCarta(int valor) {
        this.valor = valor;
    }
    
    
}
