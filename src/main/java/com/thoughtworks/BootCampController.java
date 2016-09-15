package com.thoughtworks;

import com.thoughtworks.Repository.Repository;
import com.thoughtworks.factory.ConfigurationFactory;
import com.thoughtworks.model.Candidate;
import com.thoughtworks.service.BootCampService;
import com.thoughtworks.service.LoginResponse;
import com.thoughtworks.service.LoginService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BootCampController {

    private final BootCampService bootCampService;
    private final LoginService loginService;
    private final Repository repository;

    public BootCampController() {
        repository = new Repository(ConfigurationFactory.createSessionFactory());
        bootCampService = new BootCampService(repository);
        loginService = new LoginService(bootCampService, repository);
    }

    @RequestMapping("/ping")
    public String ping() {
        return "pong";
    }

    @RequestMapping("/candidates")
    public List<Candidate> getCandidates() {
        return repository.getRegisteredCandidates();
    }

    @RequestMapping("/candidates/{candidateId}/login")
    public LoginResponse login(@PathVariable("candidateId") int candidateId) {
        Candidate candidate = repository.find(candidateId);
        boolean logginSuccess = loginService.login(candidate);
        return logginSuccess
                ? new LoginResponse(logginSuccess, candidate.getName())
                : new LoginResponse(false, null);
    }
}
