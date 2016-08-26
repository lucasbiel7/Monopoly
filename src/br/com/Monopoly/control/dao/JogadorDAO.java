/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.control.dao;

import br.com.Monopoly.model.GenericaDAO;
import br.com.Monopoly.model.entity.Jogador;
import br.com.Monopoly.model.entity.Sala;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI-Lucas
 */
public class JogadorDAO extends GenericaDAO<Jogador> {

    @Override
    public void deletar(Jogador entity) {
        entity.setDel(true);
        editar(entity);
    }

    public List<Jogador> pegarPorSala(Sala sala) {
        entitys = criteria.add(Restrictions.eq("id.sala", sala)).add(Restrictions.eq("del", false)).list();
        closeSession();
        return entitys;
    }

}