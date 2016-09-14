package com.thoughtworks;

import com.thoughtworks.model.Candidate;
import com.thoughtworks.service.BootCampService;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class BootCampSystem {

    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry registry;

    public static void main(String[] args) {
        sessionFactory = createSessionFactory();
        BootCampService bootCampService = new BootCampService(null, sessionFactory);
        bootCampService.save(new Candidate("some", 1, "TW"), null);
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(registry);
    }

    public static SessionFactory createSessionFactory() {
        Configuration cf = new Configuration().configure("hibernate.cfg.xml");
        registry = new StandardServiceRegistryBuilder()
                .applySettings(cf.getProperties())
                .configure()
                .build();
        return new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }
}
