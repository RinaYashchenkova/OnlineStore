package com.example.exam.repositories;

import com.example.exam.models.Cart;
import com.example.exam.models.Person;
import com.example.exam.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    List<Cart> findByPerson(Optional<Person> person);

    Cart findByPersonAndProduct(Person person, Product product);
}
