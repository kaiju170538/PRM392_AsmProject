package com.example.prm392_asm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
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
// Khởi tạo danh sách sản phẩm trong giỏ hàng
    private ListOfProductInCart listOfProductInCart;
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

        //notification_activity
        // Check for POST_NOTIFICATIONS permission for Android Tiramisu (API level 33)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        //Create new productCart
        listOfProductInCart = new ListOfProductInCart(this);
        //call notification if there are item in cart
        if(!listOfProductInCart.productList.isEmpty()){
            sendNotification();
        }


    }

    private void sendNotification() {
        //get the Icon for notification
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        // create notification channel id
        String channelID = "CHANNEL_ID_NOTIFICATION";
        // create notification builder for notification
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), channelID);
        //create notification
        builder.setSmallIcon(R.drawable.smallicon)
                .setContentTitle("Shopping cart!")
                .setContentText("You have an unpaid order.")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //Create intent for redirect to the cartActivity
        Intent intent = new Intent(getApplicationContext(), CartActivity.class);
        ///Create PendingIntent to handle action touching on the notification
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, intent, PendingIntent.FLAG_MUTABLE);
        //Set PendingIntent for notification builder
        builder.setContentIntent(pendingIntent);

        //Create notification manager
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    notificationManager.getNotificationChannel(channelID);

            if (notificationChannel == null) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(channelID,
                        "Some description", importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        //Call notification
        notificationManager.notify(0, builder.build());

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