package com.thoughtworks.service;

import com.thoughtworks.Repository.Repository;
import com.thoughtworks.model.Candidate;
import com.thoughtworks.model.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @InjectMocks
    LoginService loginService;
    @Mock
    BootCampService bootCampService;
    @Mock
    Repository repository;
    @Captor
    ArgumentCaptor<Log> logArgumentCaptor;
    private String thoughtWorks;

    @Before
    public void setUp() throws Exception {
        thoughtWorks = "ThoughtWorks";
    }

    @Test
    public void shouldReturnFalseForCandidatesFromOtherCompany() throws Exception {
        Candidate larryPage = new Candidate("Larry Page", 1, "Google");
        when(bootCampService.isRegisteredForBootCamp(larryPage)).thenReturn(true);

        boolean allowedToEnterBootCamp = loginService.login(larryPage);

        assertFalse(allowedToEnterBootCamp);
    }

    @Test
    public void shouldNotAllowUnregisteredThoughtWorker() throws Exception {
        Candidate aliceFromTW = new Candidate("Alice", 2, thoughtWorks);
        when(bootCampService.isRegisteredForBootCamp(aliceFromTW)).thenReturn(false);

        boolean allowedToEnter = loginService.login(aliceFromTW);

        assertFalse(allowedToEnter);
    }

    @Test
    public void shouldAllowRegisteredThoughtWorker() throws Exception {
        Candidate bobFromTWRegisteredCandidate = new Candidate("Bob", 2, thoughtWorks);
        when(repository.getRegisteredCandidates()).thenReturn(
                asList(new Candidate("Alice", 3, thoughtWorks), new Candidate("who?", 4, "UnKnown"), new Candidate("Bob", 2, thoughtWorks))
        );

        when(bootCampService.isRegisteredForBootCamp(bobFromTWRegisteredCandidate)).thenReturn(true);

        assertTrue(loginService.login(bobFromTWRegisteredCandidate));
    }

    @Test
    public void ShouldNotAllowInvalidCandidate() throws Exception {
        assertFalse(loginService.login(null));
        verifyNoMoreInteractions(repository);

        assertFalse(loginService.login(new Candidate("x", 1, "y")));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldSaveLogForAuthenticatedUser() throws Exception {
        Candidate registeredCandidate = new Candidate("Elon Musk", 1, thoughtWorks);
        when(bootCampService.isRegisteredForBootCamp(registeredCandidate)).thenReturn(true);

        loginService.login(registeredCandidate);

        verify(repository).save(logArgumentCaptor.capture());
        assertThat(logArgumentCaptor.getValue().getCandidateId(), is(1));
    }
}
