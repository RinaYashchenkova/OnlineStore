package com.example.exam.services;

import com.example.exam.models.Person;
import com.example.exam.models.Role;
import com.example.exam.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registration(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRoles(Collections.singleton(Role.ROLE_USER));
        person.setAccountActive(true);
        person.setDateOfRegistration(LocalDateTime.now());
        peopleRepository.save(person);
    }
}
