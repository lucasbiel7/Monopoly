/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.control.dao;

import br.com.Monopoly.model.GenericaDAO;
import br.com.Monopoly.model.entity.Permissao;
import br.com.Monopoly.model.entity.Usuario;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author samuel
 */
public class PermissaoDAO extends GenericaDAO<Permissao> {

    public List<Permissao> buscarPorUsuario(Usuario user) {
        entitys = criteria.add(Restrictions.eq("id.usuario", user)).list();
        closeSession();
        return entitys;
    }

}
