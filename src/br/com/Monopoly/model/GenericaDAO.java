/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.model;

import br.com.Monopoly.control.AcessoBanco;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author OCTI-Lucas
 * @param <Entity>
 */
public class GenericaDAO<Entity extends Serializable> implements AcoesBD<Entity> {

    protected Entity entity;
    protected List<Entity> entitys;
    protected Class<Entity> classe;
    protected Criteria criteria;
    protected Session session;

    public GenericaDAO() {
        session = AcessoBanco.getSessionFactory().openSession();
        classe = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        criteria = session.createCriteria(classe);
        entitys = new ArrayList<>();
    }

    @Override
    public void salvar(Entity entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        closeSession();
    }

    @Override
    public void editar(Entity entity) {
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        closeSession();
    }

    @Override
    public void deletar(Entity entity) {
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        closeSession();
    }

    @Override

    public Entity buscarPorID(Serializable id) {
        entity = (Entity) session.get(classe, id);
        closeSession();
        return entity;
    }

    @Override
    public List<Entity> buscarTodos() {
        entitys = criteria.list();
        closeSession();
        return entitys;
    }

    protected void closeSession() {
        if (session.isOpen()) {
            session.flush();
            session.close();
        }
    }

    @Override
    public List<Entity> buscarPorParametro(String campo, Object valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
