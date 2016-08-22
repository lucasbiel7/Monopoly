/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.control;

import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author OCTI-Lucas
 */
public class AcessoBanco {

    private static SessionFactory sessionFactory;

    static {
//        ServiceRegistry serviceRegistry =
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
