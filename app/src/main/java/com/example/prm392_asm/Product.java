package com.example.prm392_asm;

public class Product {
    String Name;
    String Description;
    Double Price;
    int Image; //referencing the resource id (R.drawable.id) of the images

    public Product(){}
    public Product(String name, String description, Double price, int image) {
        Name = name;
        Description = description;
        Price = price;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
