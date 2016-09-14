package com.thoughtworks.service;

import com.thoughtworks.Repository.Repository;
import com.thoughtworks.model.Candidate;
import lombok.AllArgsConstructor;

import java.util.List;


@AllArgsConstructor
public class BootCampService {

    private final Repository repository;

    public boolean isRegisteredForBootCamp(Candidate candidate) {
        List<Candidate> registeredCandidates = repository.getRegisteredCandidates();
        return registeredCandidates.stream()
                .filter(c -> c.equals(candidate))
                .findFirst().isPresent();
    }

}
