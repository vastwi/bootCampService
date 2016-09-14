package com.thoughtworks.factory;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.inject.Singleton;

@Singleton
public class ConfigurationFactory {

    public static SessionFactory createSessionFactory() {
        Configuration cf = new Configuration().configure("hibernate.cfg.xml");
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(cf.getProperties())
                .configure()
                .build();
        return new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

}
