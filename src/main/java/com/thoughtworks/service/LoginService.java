package com.thoughtworks.service;

import com.thoughtworks.Repository.Repository;
import com.thoughtworks.model.Candidate;
import com.thoughtworks.model.Log;

import javax.inject.Inject;
import java.time.LocalDateTime;

public class LoginService {

    private final static String THOUGHT_WORKS = "ThoughtWorks";

    private final BootCampService bootCampService;
    private final Repository repository;

    @Inject
    public LoginService(BootCampService bootCampService, Repository repository) {
        this.bootCampService = bootCampService;
        this.repository = repository;
    }

    public boolean login(Candidate employee) {
        if (isAuthorized(employee)) {
            repository.save(new Log(employee.getId(), LocalDateTime.now()));
            return true;
        }
        return false;
    }

    private boolean isAuthorized(Candidate employee) {
        return employee != null
                && employee.getCompany().equals(THOUGHT_WORKS)
                && bootCampService.isRegisteredForBootCamp(employee);
    }
}
