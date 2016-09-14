package com.thoughtworks.service;

import com.thoughtworks.model.Candidate;

import java.time.LocalDateTime;

public class RegisterService {

    private final static String THOUGHT_WORKS = "ThoughtWorks";
    BootCampService service;

    public boolean enter(Candidate employee) {
        if (canEnter(employee)) {
            service.register(employee, LocalDateTime.now());
            return true;
        }
        return false;
    }

    private boolean canEnter(Candidate employee) {
        return employee != null
                && employee.getCompany().equals(THOUGHT_WORKS)
                && service.isRegisteredForBootCamp(employee);
    }
}
