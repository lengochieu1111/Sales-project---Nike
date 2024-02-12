package com.example.nike.Bag;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nike.FirebaseDataHelper;
import com.example.nike.Product.I_OnItemClickListener;
import com.example.nike.Product.Product;
import com.example.nike.Product.ProductDetails_Activity;
import com.example.nike.R;
import com.example.nike.Tab.ENUM_ActivityType;
import com.example.nike.Tab.STR_IntentKey;

import java.util.ArrayList;

public class PaymentItem_RecyclerView_Config
{
    private Context _context;
    private PaymentItemAdapter _paymentItemAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, ArrayList<CartItem> _cartItemList, ArrayList<String> _keys)
    {
        this._context = context;
        this._paymentItemAdapter = new PaymentItemAdapter(_cartItemList, _keys);

        recyclerView.setAdapter(this._paymentItemAdapter);
    }

    public class PaymentItemAdapter extends RecyclerView.Adapter<PaymentItemView>
    {
        private I_OnItemClickListener _listener;

        public void set_OnItemClickListener(I_OnItemClickListener _listener)
        {
            this._listener = _listener;
        }

        private ArrayList<CartItem> _cartItemList;
        private ArrayList<String> _keys;

        public PaymentItemAdapter(ArrayList<CartItem> _cartItemList, ArrayList<String> keys) {
            this._cartItemList = _cartItemList;
            this._keys = keys;
        }

        @NonNull
        @Override
        public PaymentItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PaymentItemView(parent, this._listener);
        }

        @Override
        public void onBindViewHolder(@NonNull PaymentItemView holder, int position) {
            holder.Bind(_cartItemList.get(position), _keys.get(position));
        }

        @Override
        public int getItemCount() {
            return this._cartItemList.size();
        }
    }

    public class PaymentItemView extends RecyclerView.ViewHolder
    {
        private ImageView ivw_productImage_PaymentItem;
        private TextView tvw_productName_PaymentItem;
        private TextView tvw_productColor_PaymentItem;
        private TextView tvw_productSize_PaymentItem;
        private TextView tvw_productPrice_PaymentItem;
        private TextView tvw_productNumber_PaymentItem;
        private String _key;

        public PaymentItemView(ViewGroup parent, final I_OnItemClickListener listener) {
            super(LayoutInflater.from(_context)
                    .inflate(R.layout.payment_item, parent, false));

            this.ivw_productImage_PaymentItem = itemView.findViewById(R.id.ivw_productImage_PaymentItem);
            this.tvw_productName_PaymentItem = itemView.findViewById(R.id.tvw_productName_PaymentItem);
            this.tvw_productColor_PaymentItem = itemView.findViewById(R.id.tvw_productColor_PaymentItem);
            this.tvw_productSize_PaymentItem = itemView.findViewById(R.id.tvw_productSize_PaymentItem);
            this.tvw_productPrice_PaymentItem = itemView.findViewById(R.id.tvw_productPrice_PaymentItem);
            this.tvw_productNumber_PaymentItem = itemView.findViewById(R.id.tvw_productNumber_PaymentItem);

/*            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });*/
        }

        public void Bind(CartItem cartItem, String key)
        {
            Glide.with(this.ivw_productImage_PaymentItem.getContext()).load(cartItem.get_productImageLink()).into(this.ivw_productImage_PaymentItem);
            this.tvw_productName_PaymentItem.setText(cartItem.get_productName());
            this.tvw_productColor_PaymentItem.setText(cartItem.get_productColor());
            this.tvw_productSize_PaymentItem.setText(String.valueOf(cartItem.get_productSize()));
            this.tvw_productNumber_PaymentItem.setText(String.valueOf(cartItem.get_productNumber()));

            /* Product Price */
            int i_productPrice = cartItem.get_productPrice();
            String str_productPrice =  this.ConvertNumberToString_productPrice(i_productPrice);
            this.tvw_productPrice_PaymentItem.setText(str_productPrice);

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
