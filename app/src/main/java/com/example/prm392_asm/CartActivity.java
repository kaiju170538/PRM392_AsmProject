package com.example.prm392_asm;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    RecyclerView cartRecyclerView;
    ListOfProductInCart listOfProductInCart;
    ImageView toolbarIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Khởi tạo RecyclerView
        cartRecyclerView = findViewById(R.id.cartRecyclerView);

        // Khởi tạo danh sách sản phẩm trong giỏ hàng
        listOfProductInCart = new ListOfProductInCart(this);
        // Tạo adapter cho RecyclerView
        CartAdapter adapter = new CartAdapter(this, listOfProductInCart.getProductList());
        cartRecyclerView.setAdapter(adapter);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbarIcon = findViewById(R.id.toolbar_icon);
        toolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Quay lại activity trước đó
            }
        });
    }


    public void addProductToCart(ProductInCart product) {
        try {
            listOfProductInCart.add(product);
            cartRecyclerView.getAdapter().notifyDataSetChanged(); // Cập nhật RecyclerView
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu cần
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        listOfProductInCart.saveCart(this); // Lưu giỏ hàng khi thoát
    }
}