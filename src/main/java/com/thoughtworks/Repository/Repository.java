package com.thoughtworks.Repository;


import com.thoughtworks.model.Candidate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;


public class Repository {
    private final SessionFactory sessionFactory;

    public Repository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Object entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
    }

    public List<Candidate> getRegisteredCandidates() {
        List<Candidate> candidates = sessionFactory.openSession()
                .createQuery("from Candidate cs")
                .list();
        return candidates == null ? Collections.emptyList() : candidates;

    }

    public Candidate find(int candidateId) {
        return (Candidate) sessionFactory.openSession().createQuery("from Candidate cs where cs.id = :filterId")
                .setParameter("filterId", candidateId)
                .uniqueResult();
    }

    public void register(Candidate candidate) {
        save(candidate);
    }
}
