package com.example.prm392_asm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    Context _context;
    ArrayList<Product> _productList;

    public MyRecyclerViewAdapter(Context context, ArrayList<Product> productList){
        this._productList = productList;
        this._context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(_context);
        View view = layoutInflater.inflate(R.layout.m_product_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageResource(_productList.get(position).getImage());
        holder.textView.setText(_productList.get(position).getName());

        // Xử lý sự kiện khi người dùng bấm vào sản phẩm
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để mở ProductDetailActivity
                Intent intent = new Intent(_context, ProductDetailActivity.class);

                // Truyền dữ liệu sản phẩm sang Activity
                intent.putExtra("productName", _productList.get(position).getName());
                intent.putExtra("productDescription", _productList.get(position).getDescription());
                intent.putExtra("productPrice", _productList.get(position).getPrice());
                intent.putExtra("productImage", _productList.get(position).getImage());

                // Bắt đầu Activity
                _context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _productList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mImageView);
            textView = itemView.findViewById(R.id.mTextView);
        }
    }
}


