package com.example.exam.services;

import com.example.exam.models.Person;
import com.example.exam.models.Role;
import com.example.exam.repositories.PeopleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    public PeopleService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Person findPersonByUsername(String username){
        return peopleRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Person not found!"));
    }

    @Transactional
    public void updatePerson(long id, Person updatedPerson){
        updatedPerson.setId(id);
        updatedPerson.setRoles(Collections.singleton(Role.ROLE_USER));
        updatedPerson.setPassword(passwordEncoder.encode(updatedPerson.getPassword()));
        peopleRepository.save(updatedPerson);
    }
}
