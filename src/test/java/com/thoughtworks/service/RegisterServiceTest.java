package com.thoughtworks.service;

import com.thoughtworks.model.Candidate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceTest {

    @InjectMocks
    RegisterService registerService;
    @Mock
    BootCampService bootCampService;
    private String thoughtWorks;

    @Before
    public void setUp() throws Exception {
        thoughtWorks = "ThoughtWorks";
    }

    @Test
    public void shouldReturnFalseForCandidatesFromOtherCompany() throws Exception {
        Candidate larryPage = new Candidate("Larry Page", 1, "Google");
        when(bootCampService.isRegisteredForBootCamp(larryPage)).thenReturn(false);

        boolean allowedToEnterBootCamp = registerService.enter(larryPage);

        assertFalse(allowedToEnterBootCamp);
    }

    @Test
    public void shouldNotAllowUnregisteredThoughtWorker() throws Exception {
        Candidate aliceFromTW = new Candidate("Alice", 2, thoughtWorks);
        when(bootCampService.isRegisteredForBootCamp(aliceFromTW)).thenReturn(false);

        boolean allowedToEnter = registerService.enter(aliceFromTW);

        assertFalse(allowedToEnter);
    }

    @Test
    public void shouldAllowRegisteredThoughtWorker() throws Exception {
        Candidate bobFromTWRegisteredCandidate = new Candidate("Bob", 2, thoughtWorks);
        when(bootCampService.isRegisteredForBootCamp(bobFromTWRegisteredCandidate)).thenReturn(true);

        assertTrue(registerService.enter(bobFromTWRegisteredCandidate));
    }

    @Test
    public void ShouldNotAllowInvalidCandidate() throws Exception {
        assertFalse(registerService.enter(null));
    }
}
