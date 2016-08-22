/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author samuel
 */
public interface AcoesBD<Entidade> {
    public void salvar(Entidade e);
    public void editar(Entidade e);
    public Entidade buscarPorID(Serializable id);
    public List<Entidade> buscarPorParametro(String campo,Object valor);
    public List<Entidade> buscarTodos();
    public void deletar(Entidade e);
}
