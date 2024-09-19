package com.example.prm392_asm;

import android.content.Context;
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


