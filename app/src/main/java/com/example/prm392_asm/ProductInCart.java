package com.example.prm392_asm;

public class ProductInCart extends Product {
    int quantity;

    public ProductInCart(){
        super();
    }
    public ProductInCart(String name, String description, Double price, int image, int quantity){
        super(name,description,price,image);
        this.quantity = quantity;
    }

    // Getters and setters

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
