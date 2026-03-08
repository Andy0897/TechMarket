package com.example.TechMarket.Product;

import com.example.TechMarket.Category.Category;
import com.example.TechMarket.Condition.Condition;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Column(name = "product_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Полето не може да бъде празно")
    private String title;

    @NotEmpty(message = "Полето не може да бъде празно")
    @Size(max = 500, message = "Описанието не трябва да е по-дълго от 500 символа")
    private String description;

    @Min(value = 0, message = "Невалидна стойност")
    private double price;

    @Lob
    @ElementCollection
    List<byte[]> images = new ArrayList<>();

    @Column(name = "product_condition")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Condition condition;

    @NotNull
    @ManyToOne
    private Category category;

    boolean isAvailable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}