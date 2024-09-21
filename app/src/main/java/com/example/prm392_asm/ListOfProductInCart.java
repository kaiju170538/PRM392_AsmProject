    package com.example.prm392_asm;

    import android.content.Context;
    import android.content.SharedPreferences;

    import com.google.gson.Gson;
    import com.google.gson.reflect.TypeToken;

    import java.util.ArrayList;

    public class ListOfProductInCart {
        //property
        ArrayList<ProductInCart> productList;
        private static final String PREFS_NAME = "cart_prefs";
        private static final String CART_KEY = "cart_items";
        private Context context;
        public ListOfProductInCart(){
            this.productList = new ArrayList<>();
        }

        //get method for productList
        public ArrayList<ProductInCart> getProductList() {
            return productList;
        }
        //set method for productList
        public void setProductList(ArrayList<ProductInCart> productList) {
            this.productList = productList;
        }
        public ListOfProductInCart(Context context) {
            this.context = context;
            this.productList = loadCart();
        }

        public void saveCart(Context context) {
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(productList);
            editor.putString(CART_KEY, json);
            editor.apply();
        }

        private ArrayList<ProductInCart> loadCart() {
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = prefs.getString(CART_KEY, null);
            return json != null ? gson.fromJson(json, new TypeToken<ArrayList<ProductInCart>>(){}.getType()) : new ArrayList<>();
        }

        //Add method for add product to the cart
        public void add(ProductInCart product) throws Exception {
            try {
                boolean exists = false;
                for (ProductInCart pro : this.productList) {
                    if (pro.getName().equals(product.getName())) {
                        // Increase the quantity if the product is exist
                        pro.setQuantity(pro.getQuantity() + product.getQuantity());
                        exists = true;
                        break;
                    }
                }
                // add product if it not exist
                if (!exists) {
                    this.productList.add(product);
                }
                saveCart(context);
            } catch (Exception ex){
                throw new Exception("Không thể thêm sản phẩm vào giỏ hàng: " + ex.getMessage());
            }
        }

        //Update method for update the quantity for productInCart
        public void Update(ProductInCart product) throws Exception {
            for (ProductInCart pro : this.productList) {
                if (pro.getName().equals(product.getName())) {
                    pro.setQuantity(product.getQuantity());
                    saveCart(context); // Lưu giỏ hàng sau khi cập nhật
                    return;
                }
            }
            throw new Exception("Sản phẩm không tồn tại trong giỏ hàng");
        }

        //Delete method for delete the product from the cart
        public void delete(ProductInCart product) throws Exception {
            try {
                for (int i = 0; i < this.productList.size(); i++) {
                    ProductInCart pro = this.productList.get(i);
                    if (pro.getName().equals(product.getName())) {
                        this.productList.remove(i); // Xóa sản phẩm
                        saveCart(context);
                        break;
                    }
                }
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }
        }

        //Method for get product by name
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
