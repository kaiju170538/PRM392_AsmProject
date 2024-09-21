package com.example.prm392_asm;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Product> productModel = new ArrayList<>();
    int[] productImage = {R.drawable.iphone16pm, R.drawable.iphone15plus, R.drawable.iphone11, R.drawable.samsungs24,
            R.drawable.samsungflip, R.drawable.opporeno12, R.drawable.honorx7b, R.drawable.hmd105, R.drawable.xiaomi14, R.drawable.nokia3210};

    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        setupProductModel();
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MyRecyclerViewAdapter(this, productModel));

        //toolbar options start------------------
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Shop");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        //toolbar options end--------------------
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater menuInflater = new MenuInflater(this);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    //setup dataset for product list
    private void setupProductModel(){
        String[] productName = getResources().getStringArray(R.array.prod_name);
        String[] productDescription = getResources().getStringArray(R.array.prod_des);
        String[] productPrice = getResources().getStringArray(R.array.prod_price);

        for (int i = 0; i < productName.length; i++) {
            double price = Double.parseDouble(productPrice[i]);
            productModel.add(new Product(productName[i], productDescription[i], price, productImage[i]));
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cart_item) {
            // Mở Activity giỏ hàng
            startActivity(new Intent(this, CartActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}