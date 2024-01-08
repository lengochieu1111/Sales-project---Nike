package com.example.nike.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nike.R;

import java.util.List;

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ImageViewHolder> {
    private Context _context;
    private List<String> _images; // Mảng chứa ID của các ảnh

    public ProductImageAdapter(List<String> _images, Context _context) {
        this._images = _images;
        this._context = _context;
    }

    @NonNull
    @Override
    public ProductImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_image_pager, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductImageAdapter.ImageViewHolder holder, int position) {
        //  holder._imv_productImagePager.setImageResource(_images.get(position));
        Glide.with(holder._imv_productImagePager.getContext()).load(this._images.get(position)).into(holder._imv_productImagePager);
    }

    @Override
    public int getItemCount() {
        return _images.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView _imv_productImagePager;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            _imv_productImagePager = itemView.findViewById(R.id.imv_productImagePager); // Thay thế "imageView" bằng id của ImageView trong layout item_image
        }
    }

}
