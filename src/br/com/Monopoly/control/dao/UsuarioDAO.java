/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.control.dao;

import br.com.Monopoly.model.entity.Usuario;
import br.com.Monopoly.model.GenericaDAO;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI-Lucas
 */
public class UsuarioDAO extends GenericaDAO<Usuario> {

    public Usuario login(String login, String senha){
        entity = (Usuario) criteria.add(Restrictions.eq("login", login)).add(Restrictions.eq("senha", senha)).add(Restrictions.eq("del", false)).uniqueResult();
        closeSession();
        return entity;
    }
    
    
}
