/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.control.dao;

import br.com.Monopoly.model.GenericaDAO;
import br.com.Monopoly.model.entity.Amigos;
import br.com.Monopoly.model.entity.Usuario;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author samuel
 */
public class AmigosDAO extends GenericaDAO<Amigos> {

    public List<Amigos> buscarAmizades(Usuario user) {
        entitys = criteria.add(Restrictions.or(Restrictions.eq("id.remetente", user),Restrictions.eq("id.convidado", user))).list();
        closeSession();
        return entitys;
    }
}
