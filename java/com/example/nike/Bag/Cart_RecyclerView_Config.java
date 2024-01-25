package com.example.nike.Bag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nike.Product.I_OnItemClickListener;
import com.example.nike.Product.Product;
import com.example.nike.R;

import java.util.ArrayList;

public class Cart_RecyclerView_Config {
    private Context _context;
    private CartItemAdapter _cartItemAdapter;

    public void setConfig(RecyclerView recyclerView, Context _context, ArrayList<CartItem> _cartItemList, ArrayList<String> _keys)
    {
        this._context = _context;
        this._cartItemAdapter = new CartItemAdapter(_cartItemList, _keys);

/*        this._cartItemAdapter.set_OnItemClickListener(new I_OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
*/

        recyclerView.setAdapter(this._cartItemAdapter);
    }

    public class CartItemAdapter extends RecyclerView.Adapter<CartItemView>
    {
        private I_OnItemClickListener _listener;

        public void set_OnItemClickListener(I_OnItemClickListener _listener)
        {
            this._listener = _listener;
        }

        private ArrayList<CartItem> _cartItemList;
        private ArrayList<String> _keys;

        public CartItemAdapter(ArrayList<CartItem> _cartItemList, ArrayList<String> keys) {
            this._cartItemList = _cartItemList;
            this._keys = keys;
        }

        @NonNull
        @Override
        public CartItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CartItemView(parent, this._listener);
        }

        @Override
        public void onBindViewHolder(@NonNull CartItemView holder, int position) {
            holder.Bind(_cartItemList.get(position), _keys.get(position));
        }

        @Override
        public int getItemCount() {
            return this._cartItemList.size();
        }
    }

    public class CartItemView extends RecyclerView.ViewHolder
    {
        private ImageView _ivw_productImage_Bag;
        private TextView _tvw_productName_Bag;
        private TextView _tvw_productType_Bag;
        private TextView _tvw_productColor_Bag;
        private TextView _tvw_productSize_Bag;
        private Button _btn_minusOne_Bag;
        private TextView _tvw_productNumber_Bag;
        private Button _btn_plusOne_Bag;
        private TextView _tvw_productPrice_Bag;
        private String _key;

        public CartItemView(ViewGroup parent, final I_OnItemClickListener listener) {
            super(LayoutInflater.from(_context).inflate(R.layout.cart_item, parent, false));

            this._ivw_productImage_Bag = itemView.findViewById(R.id._ivw_productImage_Bag);
            this._tvw_productName_Bag = itemView.findViewById(R.id._tvw_productName_Bag);
            this._tvw_productType_Bag = itemView.findViewById(R.id._tvw_productType_Bag);
            this._tvw_productColor_Bag = itemView.findViewById(R.id._tvw_productColor_Bag);
            this._tvw_productSize_Bag = itemView.findViewById(R.id._tvw_productSize_Bag);
            this._btn_minusOne_Bag = itemView.findViewById(R.id._btn_minusOne_Bag);
            this._tvw_productNumber_Bag = itemView.findViewById(R.id._tvw_productNumber_Bag);
            this._btn_plusOne_Bag = itemView.findViewById(R.id._btn_plusOne_Bag);
            this._tvw_productPrice_Bag = itemView.findViewById(R.id._tvw_productPrice_Bag);

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

        public void Bind(CartItem cartItem, String key)
        {
            Glide.with(this._ivw_productImage_Bag.getContext()).load(cartItem.get_productImageLink()).into(this._ivw_productImage_Bag);
            this._tvw_productName_Bag.setText(cartItem.get_productName());
            this._tvw_productType_Bag.setText(cartItem.get_productType());
            this._tvw_productColor_Bag.setText(cartItem.get_productColor());
            this._tvw_productSize_Bag.setText(cartItem.get_productSize());
            this._tvw_productNumber_Bag.setText(cartItem.get_productNumber());

            this._btn_minusOne_Bag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { _tvw_productNumber_Bag.setText(cartItem.get_productNumber()); }
            });

            this._btn_plusOne_Bag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { _tvw_productNumber_Bag.setText(cartItem.get_productNumber()); }
            });

            /* Product Price */
            int i_productPrice = cartItem.get_productPrice();
            String str_productPrice =  this.ConvertNumberToString_productPrice(i_productPrice);
            this._tvw_productPrice_Bag.setText(str_productPrice);

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

}
