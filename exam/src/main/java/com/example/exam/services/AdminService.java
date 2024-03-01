package com.example.exam.services;

import com.example.exam.models.Person;
import com.example.exam.models.Product;
import com.example.exam.models.Role;
import com.example.exam.repositories.PeopleRepository;
import com.example.exam.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class AdminService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public AdminService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> allUsers(){
        return peopleRepository.findAll();
    }

    @Transactional
    public void banAccount(long id){
        Person person = peopleRepository.findById(id).orElse(null);
        if (person != null){
            person.setAccountActive(false);
            peopleRepository.save(person);
        }
    }

    @Transactional
    public void unbanAccount(long id){
        Person person = peopleRepository.findById(id).orElse(null);
        if(person != null) {
            person.setAccountActive(true);
            peopleRepository.save(person);
        }
    }
}
