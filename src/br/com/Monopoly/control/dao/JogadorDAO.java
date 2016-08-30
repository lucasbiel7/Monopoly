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
import org.hibernate.criterion.Order;
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
        entitys = criteria.add(Restrictions.eq("id.sala", sala)).add(Restrictions.eq("del", false)).addOrder(Order.asc("numero")).list();
        closeSession();
        return entitys;
    }

    public Jogador pegarPorSalaNumero(Sala sala, int numero) {
        entity = (Jogador) criteria.add(Restrictions.eq("id.sala", sala)).add(Restrictions.eq("numero", numero)).add(Restrictions.eq("del", false)).uniqueResult();
        closeSession();
        return entity;
    }

}
