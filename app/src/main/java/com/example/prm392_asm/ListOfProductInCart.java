package com.example.prm392_asm;

import java.util.ArrayList;

public class ListOfProductInCart {
    ArrayList<ProductInCart> productList;

    public ListOfProductInCart(){
        this.productList = new ArrayList<>();
    }

    public ArrayList<ProductInCart> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductInCart> productList) {
        this.productList = productList;
    }

    public void add(ProductInCart product){
        this.productList.add(product);
    }

    public void Update(ProductInCart product) throws Exception {
        try{
            for (ProductInCart pro: this.productList) {
                if(pro.Name.equals(product.Name)){
                    pro.quantity = product.quantity;
                }
            }
        }
        catch (Exception ex){
            throw new Exception(ex.getMessage());
        }

    }

    public void Delete(ProductInCart product)throws Exception{
        try{
            for (ProductInCart pro: this.productList) {
                if(pro.Name.equals(product.Name)){
                    productList.remove(pro);
                }
            }
        }
        catch (Exception ex){
            throw new Exception(ex.getMessage());
        }

    }

    public ProductInCart GetProductByName(String name) throws Exception {
        try{
            for (ProductInCart pro: this.productList) {
                if(pro.Name.equals(name)){
                    return pro;
                }
            }
        }
        catch (Exception ex){
            throw new Exception(ex.getMessage());
        }

        return null;
    }


}
