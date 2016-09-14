package com.thoughtworks.service;

import com.thoughtworks.Repository.Repository;
import com.thoughtworks.factory.ConfigurationFactory;
import com.thoughtworks.model.Candidate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BootCampServiceTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Repository repository;
    private BootCampService bootCampService;

    @Before
    public void setUp() throws Exception {
        sessionFactory = ConfigurationFactory.createSessionFactory();
        repository = new Repository(sessionFactory);
        session = sessionFactory.openSession();
        bootCampService = new BootCampService(repository);
        deleteCandidates();
    }

    private void deleteCandidates() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from Candidate").executeUpdate();
        transaction.commit();
    }

    @Test
    public void shouldReturnAllCandidates() throws Exception {
        createSomeCandidates(session);

        List<Candidate> registeredCandidates = repository.getRegisteredCandidates();

        assertNotNull(registeredCandidates);
        assertThat(registeredCandidates.size(), is(4));
    }

    @Test
    public void shouldReturnTrueIfCandidateIsRegistered() throws Exception {
        createSomeCandidates(session);
        boolean aliceRegisteredForBootCamp = bootCampService.isRegisteredForBootCamp(new Candidate("alice", 1, "TW-Pune"));
        assertTrue(aliceRegisteredForBootCamp);
    }


    @Test
    public void shouldReturnFalseIfCandidateIsNotRegistered() throws Exception {
        createSomeCandidates(session);
        boolean registeredForBootCamp = bootCampService.isRegisteredForBootCamp(new Candidate("SomeOneElse", 1, "TW-Pune"));
        assertFalse(registeredForBootCamp);
    }

    private void createSomeCandidates(Session session) {
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery("insert into candidate (id, name, company) values (1, 'alice', 'TW-Pune')").executeUpdate();
        session.createNativeQuery("insert into candidate (id, name, company) values (2, 'Hydalice', 'TW-Hyd')").executeUpdate();
        session.createNativeQuery("insert into candidate (id, name, company) values (3, 'GrgnBob', 'TW-Grgn')").executeUpdate();
        session.createNativeQuery("insert into candidate (id, name, company) values (4, 'ChnAlice', 'TW-Chennai')").executeUpdate();
        transaction.commit();
    }

}
