package com.example.exam.repositories;

import com.example.exam.models.Person;
import com.example.exam.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PeopleRepository extends JpaRepository<Person,Long> {

    Optional<Person> findByUsername(String username);

    Optional<Person> findById(long id);

}
