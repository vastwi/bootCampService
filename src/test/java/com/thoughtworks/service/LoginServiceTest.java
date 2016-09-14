package com.thoughtworks.service;

import com.thoughtworks.Repository.Repository;
import com.thoughtworks.model.Candidate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @InjectMocks
    LoginService loginService;
    @Mock
    BootCampService bootCampService;
    @Mock
    Repository repository;
    private String thoughtWorks;

    @Before
    public void setUp() throws Exception {
        thoughtWorks = "ThoughtWorks";
    }

    @Test
    public void shouldReturnFalseForCandidatesFromOtherCompany() throws Exception {
        Candidate larryPage = new Candidate("Larry Page", 1, "Google");
        when(bootCampService.isRegisteredForBootCamp(larryPage)).thenReturn(false);

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
    }
}
