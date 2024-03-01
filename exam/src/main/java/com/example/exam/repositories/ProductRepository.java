package com.example.exam.repositories;

import com.example.exam.models.Image;
import com.example.exam.models.Person;
import com.example.exam.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByTitleStartingWithIgnoreCase(String title);

    Optional<Product> findById(long id);

    void deleteImageById(long id);

}
