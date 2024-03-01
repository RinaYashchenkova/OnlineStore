package com.example.exam.services;

import com.example.exam.models.Cart;
import com.example.exam.models.Person;
import com.example.exam.models.Product;
import com.example.exam.repositories.CartRepository;
import com.example.exam.repositories.PeopleRepository;
import com.example.exam.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CartService {
    private final CartRepository cartRepository;

    private final PeopleRepository peopleRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, PeopleRepository peopleRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.peopleRepository = peopleRepository;
        this.productRepository = productRepository;
    }


    @Transactional
    public void addToCart(long productId, long personId) {
        Person person = peopleRepository.findById(personId).orElseThrow(() -> new RuntimeException("Person not found!"));

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found!"));

        Cart cart = cartRepository.findByPersonAndProduct(person, product);

        if (cart == null) {
            cart = new Cart();
            cart.setPerson(person);
            cart.setProduct(product);
            cart.setQuantity(product.getAmount());
        } else {
            cart.setQuantity(cart.getQuantity() + 1);
        }

        cartRepository.save(cart);
    }

    @Transactional
    public void removeFromCart(long productId, long personId) {
        Person person = peopleRepository.findById(personId).orElseThrow(() -> new RuntimeException("Person not found!"));

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found!"));

        Cart cart = cartRepository.findByPersonAndProduct(person, product);
        if (cart != null) {
            cart.setQuantity(cart.getQuantity() - 1);
            if (cart.getQuantity() == 0) {
                cartRepository.delete(cart);
            } else {
                cartRepository.save(cart);
            }
        }
    }

    public double getTotalPrice(Long personId) {
        List<Product> products = getProductsForCart(personId);

        return products.stream()
                .map(product -> product.getPrice() * product.getAmount())
                .collect(Collectors.summingDouble(Double::doubleValue));
    }

    public List<Product> getProductsForCart(long idPerson) {
        Optional<Person> person = peopleRepository.findById(idPerson);
        List<Cart> carts = cartRepository.findByPerson(person);
        List<Product> products = new ArrayList<>();
        for (Cart cart : carts) {
            products.add(cart.getProduct());
        }
        return products;
    }

}
