package com.example.nike.Product;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductItemDecoration extends RecyclerView.ItemDecoration{
    private int space;

    public ProductItemDecoration(Context context, int space) {
        this.space = (int) (space * context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space * 2;

        // Add top margin for the first row to prevent double spacing
        if (parent.getChildLayoutPosition(view) < 3) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }
}
