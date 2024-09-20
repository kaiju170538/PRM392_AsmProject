package com.example.prm392_asm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.content.Intent;
import android.widget.Toast;
import android.Manifest;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView productImageView;
    private TextView productNameTextView, productDescriptionTextView, productPriceTextView;
    private Button addToCartButton;
    private ListOfProductInCart cart; // Khởi tạo giỏ hàng

    private static final String CHANNEL_ID = "ProductNotifications";

    /*private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Product Notifications";
            String description = "Channel for product notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        /*createNotificationChannel(); */// Gọi hàm này ở đây

        // Kiểm tra quyền POST_NOTIFICATIONS
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }*/

        // Khởi tạo giỏ hàng
        cart = new ListOfProductInCart();

        // Khởi tạo các View
        productImageView = findViewById(R.id.product_image);
        productNameTextView = findViewById(R.id.product_name);
        productDescriptionTextView = findViewById(R.id.product_description);
        productPriceTextView = findViewById(R.id.product_price);
        addToCartButton = findViewById(R.id.addToCartButton); // Khởi tạo ở đây

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String productName = intent.getStringExtra("productName");
        String productDescription = intent.getStringExtra("productDescription");
        double productPrice = intent.getDoubleExtra("productPrice", 0);
        int productImage = intent.getIntExtra("productImage", 0);

        // Hiển thị dữ liệu sản phẩm
        productNameTextView.setText(productName);
        productDescriptionTextView.setText(productDescription);
        productPriceTextView.setText(String.format("$%.2f", productPrice));
        productImageView.setImageResource(productImage);

        // Thêm sự kiện cho nút "Add to Cart"
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductInCart productInCart = new ProductInCart(productName, productDescription, productPrice, productImage, 1);

                try {
                    ProductInCart existingProduct = cart.GetProductByName(productName);
                    if (existingProduct != null) {
                        // Nếu sản phẩm đã có trong giỏ hàng, tăng số lượng
                        existingProduct.setQuantity(existingProduct.getQuantity() + 1);
                        cart.Update(existingProduct);
                        Toast.makeText(ProductDetailActivity.this, productName + " has been added to cart", Toast.LENGTH_SHORT).show();
                    } else {
                        // Nếu sản phẩm chưa có trong giỏ hàng, thêm mới
                        cart.add(productInCart);
                        Toast.makeText(ProductDetailActivity.this, productName + " has been added to cart", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ProductDetailActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

       /* // Thêm sự kiện cho nút "Add to Cart"
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductInCart productInCart = new ProductInCart(productName, productDescription, productPrice, productImage, 1);

                try {
                    ProductInCart existingProduct = cart.GetProductByName(productName);
                    if (existingProduct != null) {
                        existingProduct.setQuantity(existingProduct.getQuantity() + 1);
                        cart.Update(existingProduct);
                    } else {
                        cart.add(productInCart);
                    }

                    // Kiểm tra quyền trước khi hiển thị notification
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                            showNotification(productName + " has been added to cart");
                        } else {
                            Toast.makeText(ProductDetailActivity.this, "Notification permission not granted", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        showNotification(productName + " has been added to cart");
                    }
                } catch (Exception e) {
                    Log.e("ProductDetailActivity", "Error: " + e.getMessage(), e);
                    Toast.makeText(ProductDetailActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    /*private void showNotification(String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Product Added")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        Intent intent = new Intent(this, MainActivity.class); // Thay thế bằng activity bạn muốn mở
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }
    }*/

   /* @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
                // Gọi lại hàm để hiển thị thông báo nếu cần
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}