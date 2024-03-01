package com.example.exam.controllers;

import com.example.exam.models.Image;
import com.example.exam.models.Product;
import com.example.exam.repositories.ImageRepository;
import com.example.exam.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ImageRepository imageRepository;

    @Autowired
    public ProductController(ProductService productService, ImageRepository imageRepository) {
        this.productService = productService;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/listProducts")
    public String listProducts(Model model){
        model.addAttribute("listProducts", productService.allProducts());
        return "products/list_products";
    }

    @GetMapping("/searchProduct")
    public String searchProduct(@RequestParam(required = false) String query, Model model){
        List<Product> products;
        if (query != null) {
            products = productService.searchProduct(query);
        } else {
            products = productService.allProducts();
        }
        model.addAttribute("listProducts", products);
        return "products/list_products";
    }

    @GetMapping("/{id}")
    public String showProduct(Model model, @PathVariable("id") long id){
        model.addAttribute("showProduct", productService.findOneProduct(id));
        return "products/product_info";
    }

    @GetMapping("/createProduct")
    public String createProduct(Model model){
        model.addAttribute("product", new Product());
        return "products/create_product";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult,
                         Principal principal, @RequestParam(value = "file")MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()){
            return "products/create_product";
        } else {
            productService.createProduct(product,principal,file);
        }
        return "redirect:/products/listProducts";
    }

    @GetMapping("/{id}/updateProduct")
    public String updateProduct(Model model, @PathVariable("id") long id){
        model.addAttribute("product", productService.findOneProduct(id));
        return "products/update_product";
    }


    @PatchMapping("/{id}/update")
    public String update(@PathVariable("id") long id, @ModelAttribute("product") @Valid Product product,
                         BindingResult bindingResult, Principal principal,
                         @RequestParam(value = "file") MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()){
            return "products/update_product";
        } else {
            productService.updateProduct(id,product,file,principal);
            return "redirect:/products/listProducts";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") long id){
        productService.deleteProduct(id);
        return "redirect:/products/listProducts";
    }



    @GetMapping("/images/{id}")
    @ResponseBody
    public ResponseEntity<?> getImageId(@PathVariable("id") String id) {
        if(!id.matches("\\d+")) {
            return ResponseEntity.badRequest().body("Invalid id format");
        }
        int imageId = Integer.parseInt(id);
        Image image = imageRepository.findById(imageId).orElse(null);
        return ResponseEntity.ok()
                .header("fileName", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getData())));
    }
}
