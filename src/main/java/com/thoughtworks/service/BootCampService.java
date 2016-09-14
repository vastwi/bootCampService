package com.thoughtworks.service;

import com.thoughtworks.model.Candidate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class BootCampService {

    private final SessionFactory sessionFactory;
    private List<Integer> registeredCandidates;
    private Transaction transaction;

    public BootCampService(List<Integer> registeredCandidates, SessionFactory sessionFactory) {
        this.registeredCandidates = registeredCandidates;
        this.sessionFactory = sessionFactory;
    }

    public boolean isRegisteredForBootCamp(Candidate candidateId) {
        return registeredCandidates.contains(candidateId);
    }

    public void register(Candidate candidate, LocalDateTime registerTime) {
        save(candidate, registerTime);
    }

    public void save(Candidate candidate, LocalDateTime registerTime) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(candidate);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
    }

}
