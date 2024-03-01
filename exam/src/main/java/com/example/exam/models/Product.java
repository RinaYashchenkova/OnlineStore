package com.example.exam.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Название товара не может быть пустым.")
    @Column(name = "title")
    private String title;

    @Column(name = "amount")
    private int amount;

    @Column(name = "price")
    private double price;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "product")
    private List<Image> images = new ArrayList<>();

    private int previewImageId;

    public void addImageToProduct(Image image) {
        image.setProduct(this);
        images.add(image);
    }
}
