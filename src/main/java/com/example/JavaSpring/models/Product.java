package com.example.JavaSpring.models;

import javax.persistence.*;
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private int price;
    private String image;

    public Product(){}

    public Product(String name, String description, int price, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String  image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "{"+
                "id:" + this.id+
                "name:" + this.name+
                "descriptionL:" + this.description+
                "image:" + this.image+
                "}";
    }
}
