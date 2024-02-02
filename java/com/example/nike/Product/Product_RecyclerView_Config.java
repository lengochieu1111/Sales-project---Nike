package com.example.nike.Product;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nike.FirebaseDataHelper;
import com.example.nike.R;
import com.example.nike.Tab.ENUM_ActivityType;
import com.example.nike.Tab.STR_IntentKey;

import java.util.ArrayList;

public class Product_RecyclerView_Config {
    private Context _context;
    private ProductAdapter _productAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, ArrayList<Product> products, ArrayList<String> keys, ENUM_ActivityType activityType)
    {
        this._context = context;
        this._productAdapter = new ProductAdapter(products, keys);

        this._productAdapter.set_OnItemClickListener((view, position) ->
        {
            Toast.makeText(recyclerView.getContext(), "Item clicked at position " + products.get(position).get_productID(), Toast.LENGTH_SHORT).show();

            Intent shopIntent = new Intent(_context, ProductDetails_Activity.class);
            shopIntent.putExtra(STR_IntentKey.ProductID, products.get(position).get_productID());
            shopIntent.putExtra(STR_IntentKey.ActivityType, activityType);
            context.startActivity(shopIntent);

        });

        recyclerView.setAdapter(this._productAdapter);
    }

    class ProductAdapter extends RecyclerView.Adapter<ProductItemView>
    {
        private I_OnItemClickListener _listener;

        public void set_OnItemClickListener(I_OnItemClickListener listener) {
            this._listener = listener;
        }

        //
        private ArrayList<Product> _productsList;
        private ArrayList<String> _keys;

        public ProductAdapter(ArrayList<Product> productsList, ArrayList<String> keys) {
            this._productsList = productsList;
            this._keys = keys;
        }

        @NonNull
        @Override
        public ProductItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProductItemView(parent, this._listener);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductItemView holder, int position) {
            holder.Bind(this._productsList.get(position), this._keys.get(position));
        }

        @Override
        public int getItemCount() {
            return this._productsList.size();
        }
    }

    class ProductItemView extends RecyclerView.ViewHolder
    {
        private ImageView _productImage;
        private TextView _productName, _productPrice;
        private String _key;

        public ProductItemView(ViewGroup parent, final I_OnItemClickListener listener) {
            super(LayoutInflater.from(_context)
                    .inflate(R.layout.product_item, parent, false));

            this._productImage = itemView.findViewById(R.id.imv_image);
            this._productName = itemView.findViewById(R.id.tev_name);
            this._productPrice = itemView.findViewById(R.id.tev_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }

        public void Bind(Product product, String key)
        {
            Glide.with(this._productImage.getContext()).load(product.get_productImageLink()).into(this._productImage);
            this._productName.setText(product.get_productName());

            /* Product Price */
            int i_productPrice = product.get_productPrice();
            String str_productPrice =  this.ConvertNumberToString_productPrice(i_productPrice);
            this._productPrice.setText(str_productPrice);

            this._key = key;
        }

        private String ConvertNumberToString_productPrice(int i_productPrice)
        {
            String str_productPrice = String.valueOf(i_productPrice);
            int numberLenght = str_productPrice.length();
            int surplus = numberLenght % 3;
            int dotNumber = numberLenght / 3;
            if (numberLenght % 3 == 0)
                dotNumber -= 1;

            String str_result = "₫";
            for (int i = 0; i < dotNumber; i++)
            {
                if (i == 0)
                {
                    if (surplus != 0)
                        str_result += str_productPrice.substring(0, surplus) + ",";
                    else
                        str_result += str_productPrice.substring(surplus, surplus + 3) + ",";
                }
                else if (i != 0)
                    str_result += str_productPrice.substring(surplus, surplus + 3) + ",";
            }
            str_result += str_productPrice.substring(surplus, surplus + 3);

            return str_result;
        }

    }

    // SETTER - GETTER
    public ProductAdapter get_productAdapter() {
        return _productAdapter;
    }
}
