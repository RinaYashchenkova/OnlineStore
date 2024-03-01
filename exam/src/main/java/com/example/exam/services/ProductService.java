package com.example.exam.services;

import com.example.exam.models.Image;
import com.example.exam.models.Person;
import com.example.exam.models.Product;
import com.example.exam.repositories.PeopleRepository;
import com.example.exam.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, PeopleRepository peopleRepository) {
        this.productRepository = productRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Product> allProducts(){
        return productRepository.findAll();
    }


    public Product findOneProduct(long id){
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    @Transactional
    public void createProduct(Product product, Principal principal, MultipartFile file) throws IOException {
        Image image;
        if(file.getSize() != 0) {
            image = toImageEntity(file);
            image.setPreviewImage(true);
            product.addImageToProduct(image);
        }
        product.setOwner(getUserByPrincipal(principal));

        Product product1 = productRepository.save(product);
        product1.setPreviewImageId(product1.getImages().get(0).getId());
        productRepository.save(product);
    }

    public Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setData(file.getBytes());
        return image;
    }

    @Transactional
    public void updateProduct(long id, Product updatedProduct, MultipartFile file, Principal principal) throws IOException {
            updatedProduct.setId(id);
            productRepository.deleteImageById(updatedProduct.getId());
            Image newImage1;

            if(file.getSize() != 0) {
                newImage1 = toImageEntity(file);
                newImage1.setPreviewImage(true);
                updatedProduct.addImageToProduct(newImage1);
            }

            updatedProduct.setId(id);
            updatedProduct.setOwner(getUserByPrincipal(principal));
        Product product1 = productRepository.save(updatedProduct);
        product1.setPreviewImageId(product1.getImages().get(0).getId());
        productRepository.save(product1);

    }

    @Transactional
    public void deleteProduct(long id){
        productRepository.deleteById(id);
    }

    public Person getUserByPrincipal(Principal principal){
        return peopleRepository.findByUsername(principal.getName()).orElse(null);
    }

    public List<Product> searchProduct(String startWith){
        return productRepository.findByTitleStartingWithIgnoreCase(startWith);
    }

}
