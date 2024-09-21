package com.example.prm392_asm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<ProductInCart> productList;
    private Context context;
    private ListOfProductInCart listOfProductInCart;

    public CartAdapter(Context context, List<ProductInCart> productList) {
        this.context = context;
        this.productList = productList;
        this.listOfProductInCart = new ListOfProductInCart(context);
    }

    // Tạo ViewHolder mới cho từng item trong RecyclerView
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout của item_product_in_cart.xml
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(view);
    }

    // Gắn dữ liệu vào view (TextView, ImageView...) cho từng item
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        ProductInCart product = productList.get(position);

        // Set dữ liệu cho các view
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.format("$%.2f", product.getPrice()));
        holder.quantity.setText(String.valueOf(product.getQuantity()));
        holder.productImage.setImageResource(product.getImage());

        // Xử lý tăng số lượng sản phẩm
        holder.increaseQuantity.setOnClickListener(v -> {
            product.setQuantity(product.getQuantity() + 1);
            holder.quantity.setText(String.valueOf(product.getQuantity()));
            try {
                listOfProductInCart.Update(product); // Cập nhật
            } catch (Exception e) {
                e.printStackTrace();
            }
            notifyItemChanged(position);
        });

        // Xử lý giảm số lượng sản phẩm
        holder.decreaseQuantity.setOnClickListener(v -> {
            if (product.getQuantity() > 1) {
                product.setQuantity(product.getQuantity() - 1);
                holder.quantity.setText(String.valueOf(product.getQuantity()));
                try {
                    listOfProductInCart.Update(product); // Cập nhật
                } catch (Exception e) {
                    e.printStackTrace();
                }
                notifyItemChanged(position);
            }
        });

        // Xử lý nút xóa sản phẩm
        holder.removeProduct.setOnClickListener(v -> {
            try {
                listOfProductInCart.delete(product); // Xóa sản phẩm
            } catch (Exception e) {
                e.printStackTrace();
            }
            productList.remove(position);
            notifyItemRemoved(position);
        });
    }

    // Trả về số lượng item trong danh sách
    @Override
    public int getItemCount() {
        return productList.size();
    }

    // Lớp ViewHolder quản lý view của từng item
    public static class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName, productPrice, quantity;
        Button increaseQuantity, decreaseQuantity;
        ImageButton removeProduct;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.productPrice);
            quantity = itemView.findViewById(R.id.quantity);
            increaseQuantity = itemView.findViewById(R.id.increaseQuantity);
            decreaseQuantity = itemView.findViewById(R.id.decreaseQuantity);
            removeProduct = itemView.findViewById(R.id.removeProduct);
        }
    }
}