package com.example.exam.controllers;

import com.example.exam.models.Cart;
import com.example.exam.models.Person;
import com.example.exam.models.Product;
import com.example.exam.repositories.PeopleRepository;
import com.example.exam.services.CartService;
import com.example.exam.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final PeopleRepository peopleRepository;

    @Autowired
    public CartController(CartService cartService, ProductService productService, PeopleRepository peopleRepository) {
        this.cartService = cartService;
        this.productService = productService;
        this.peopleRepository = peopleRepository;
    }

    @GetMapping("/showCart")
    public String showPersonCart(Model model, Principal principal){
        String username = principal.getName();
        Optional<Person> person = peopleRepository.findByUsername(username);
        if (person.isPresent()) {
            Person person1 = person.get();
            double totalPrice = cartService.getTotalPrice(person1.getId());
            List<Product> products = cartService.getProductsForCart(person1.getId());
            model.addAttribute("products", products);
            model.addAttribute("totalPrice", totalPrice);
        }
        return "carts/show_cart";
    }

    @PostMapping("/{id}/addProduct")
    public String addProductToCart(@PathVariable("id")long id, Principal principal){
        long idPerson = productService.getUserByPrincipal(principal).getId();
        long idProduct = productService.findOneProduct(id).getId();
        cartService.addToCart(idProduct, idPerson);
        return "redirect:/carts/showCart";
    }

    @DeleteMapping("/{id}/deleteProduct")
    public String removeProductFromCart(@PathVariable("id") long id, Principal principal){
        long idPerson = productService.getUserByPrincipal(principal).getId();
        long idProduct = productService.findOneProduct(id).getId();
        cartService.removeFromCart(idProduct, idPerson);
        return "redirect:/carts/showCart";
    }

}
