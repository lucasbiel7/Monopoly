/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Monopoly.control;

import br.com.Monopoly.model.Databases;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author OCTI-Lucas
 */
public class AcessoBanco {

    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    static {
        Configuration configuration = new Configuration().configure();
        setarConfiguracoes(configuration);
        System.out.println(configuration.getProperties());
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.configure().buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void setarConfiguracoes(Configuration configuration) {
        for (Databases databases : Databases.values()) {
            System.out.println(databases);
            if (conexaoValida(databases)) {
                System.out.println("valida");
                configuration.getProperties().setProperty("hibernate.connection.url", "jdbc:mysql://" + databases.getEndereco() + "/" + databases.getBanco() + "?zeroDateTimeBehavior=convertToNull");
                configuration.getProperties().setProperty("hibernate.connection.username", databases.getUsuario());
                configuration.getProperties().setProperty("hibernate.connection.password", databases.getSenha());
                break;
            }
        }
    }

    public static boolean conexaoValida(Databases databases) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://" + databases.getEndereco() + "/" + databases.getBanco() + "?zeroDateTimeBehavior=convertToNull", databases.getUsuario(), databases.getSenha());
            if (connection != null) {
                connection.close();
                return true;
            }
        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(AcessoBanco.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Next Connection");
        }
        return false;
    }
}
