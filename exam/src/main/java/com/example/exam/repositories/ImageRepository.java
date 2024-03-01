package com.example.exam.repositories;

import com.example.exam.models.Image;
import com.example.exam.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findImagesByProduct(Product product);
}
