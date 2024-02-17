package com.example.nike.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nike.Bag.CartItem;
import com.example.nike.Bag.CartItem_RecyclerView_Config;
import com.example.nike.Bag.PaymentItem_RecyclerView_Config;
import com.example.nike.FirebaseDataHelper;
import com.example.nike.Login.Login_Activity;
import com.example.nike.Product.Product;
import com.example.nike.Profile.User;
import com.example.nike.R;
import com.example.nike.Tab.Test_MainActivity;
import com.example.nike.zalopay.Api.CreateOrder;


import org.json.JSONObject;

import java.util.ArrayList;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BagFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BagFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BagFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BagFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BagFragment newInstance(String param1, String param2) {
        BagFragment fragment = new BagFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /* PROPERTY */
    RecyclerView rvw_bag;
    TextView textView;
    LinearLayout llt_invoice;
    LinearLayout llt_bag;
    ProgressBar pbr_loadding_bag;

    TextView tvw_subtotal_bag;
    TextView tvw_total_bag;
    Button btn_buy;

    //
    private ImageButton ibn_undo_Payment;
    private TextView tvw_productName_Payment;
    private TextView tvw_phoneNumber_Payment;
    private TextView tvw_address_Payment;
    private RecyclerView rvw_payment;
    private TextView tvw_subtotal_Payment;
    private TextView tvw_total_Payment;
    private Button btn_payment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Zalo
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(2554, Environment.SANDBOX);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bag, container, false);

        rvw_bag = view.findViewById(R.id.rvw_bag);
        textView = view.findViewById(R.id.textView);
        llt_invoice = view.findViewById(R.id.llt_invoice);
        llt_bag = view.findViewById(R.id.llt_bag);
        pbr_loadding_bag = view.findViewById(R.id.pbr_loadding_bag);
        tvw_subtotal_bag = view.findViewById(R.id.tvw_subtotal_bag);
        tvw_total_bag = view.findViewById(R.id.tvw_total_bag);
        btn_buy = view.findViewById(R.id.btn_buy);
        rvw_bag.setLayoutManager(new LinearLayoutManager(getContext()));

        ProgressBarStatus(true);
        BagStatus(false);
        InvoiceStatus(false);

        new FirebaseDataHelper().ReadTheCartItemList(new FirebaseDataHelper.DataStatus() {
            @Override
            public void DataIsLoaded_Product(ArrayList<Product> products, ArrayList<String> keys) { }

            @Override
            public void DataIsLoaded_CartItem(ArrayList<CartItem> cartItems, ArrayList<CartItem> _cartItemSelected, ArrayList<String> keys) {
                ProgressBarStatus(false);
                BagStatus(true);

                new CartItem_RecyclerView_Config().setConfig(rvw_bag, getContext(), cartItems, keys);
                UpdateTotalAmount_Bag(_cartItemSelected);

                if (cartItems.isEmpty())
                    InvoiceStatus(false);
                else
                    InvoiceStatus(true);

            }

            @Override
            public void DataIsInserted_Product() { }
            @Override
            public void DataIsInserted_CartItem() { }
            @Override
            public void DataIsUpdated_CartItem(ArrayList<CartItem> cartItemSelected) {
                UpdateTotalAmount_Bag(cartItemSelected);
            }
            @Override
            public void DataIsDeleted_CartItem(ArrayList<CartItem> cartItemSelected) {
                UpdateTotalAmount_Bag(cartItemSelected);
            }
            @Override
            public void HasTheSelectedProduct_CartItem(boolean isEmpty) {}

            @Override
            public void DataIsLoaded_User(User user) {

            }

            @Override
            public void DataIsUpdated_Product() { }
            @Override
            public void DataIsDeleted_Product() { }
        });

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new FirebaseDataHelper().HasTheSelectedProductInTheBag(new FirebaseDataHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded_Product(ArrayList<Product> products, ArrayList<String> keys) {}
                    @Override
                    public void DataIsInserted_Product() {}
                    @Override
                    public void DataIsUpdated_Product() {}
                    @Override
                    public void DataIsDeleted_Product() {}
                    @Override
                    public void DataIsLoaded_CartItem(ArrayList<CartItem> cartItems, ArrayList<CartItem> _cartItemSelected, ArrayList<String> keys) {}
                    @Override
                    public void DataIsInserted_CartItem() {}
                    @Override
                    public void DataIsUpdated_CartItem(ArrayList<CartItem> cartItemSelected) {}
                    @Override
                    public void DataIsDeleted_CartItem(ArrayList<CartItem> cartItem) {}
                    @Override
                    public void HasTheSelectedProduct_CartItem(boolean isEmpty) {
                        if (isEmpty)
                        {
                            Toast.makeText(getContext(), "Please select product to pay.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            ShowPaymentDialog();
                        }
                    }

                    @Override
                    public void DataIsLoaded_User(User user) {

                    }
                });
            }
        });

        return view;
    }

    private void InvoiceStatus(boolean status)
    {
        if (status)
            llt_invoice.setVisibility(View.VISIBLE);
        else
            llt_invoice.setVisibility(View.GONE);
    }

    private void BagStatus(boolean status)
    {
        if (status)
            llt_bag.setVisibility(View.VISIBLE);
        else
            llt_bag.setVisibility(View.GONE);
    }

    private void ProgressBarStatus(boolean status)
    {
        if (status)
            pbr_loadding_bag.setVisibility(View.VISIBLE);
        else
            pbr_loadding_bag.setVisibility(View.GONE);
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

    private void UpdateTotalAmount_Bag(ArrayList<CartItem> _cartItemSelected)
    {
        String str_total = "";
        if (_cartItemSelected.isEmpty())
        {
            str_total = "0";
        }
        else
        {
            float subtotal_bag = 0;
            for (CartItem cartItem : _cartItemSelected)
            {
                subtotal_bag += cartItem.get_productPrice() * cartItem.get_productNumber();
            }
            str_total = ConvertNumberToString_productPrice((int) subtotal_bag);
        }
        tvw_subtotal_bag.setText(str_total);
        tvw_total_bag.setText(str_total);
    }

    private void ShowPaymentDialog()
    {
        final Dialog paymentDialog = new Dialog(getContext());
        paymentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        paymentDialog.setContentView(R.layout.payment_bottom_sheet_layout);

        this.ibn_undo_Payment = paymentDialog.findViewById(R.id.ibn_undo_Payment);
        this.tvw_productName_Payment = paymentDialog.findViewById(R.id.tvw_productName_Payment);
        this.tvw_phoneNumber_Payment = paymentDialog.findViewById(R.id.tvw_phoneNumber_Payment);
        this.tvw_address_Payment = paymentDialog.findViewById(R.id.tvw_address_Payment);
        this.rvw_payment = paymentDialog.findViewById(R.id.rvw_payment);
        this.tvw_subtotal_Payment = paymentDialog.findViewById(R.id.tvw_subtotal_Payment);
        this.tvw_total_Payment = paymentDialog.findViewById(R.id.tvw_total_Payment);
        this.btn_payment = paymentDialog.findViewById(R.id.btn_payment);
        this.rvw_payment.setLayoutManager(new LinearLayoutManager(getContext()));

        /* Handle Event */
        new FirebaseDataHelper().ReadTheCartItemList(new FirebaseDataHelper.DataStatus() {
            @Override
            public void DataIsLoaded_Product(ArrayList<Product> products, ArrayList<String> keys) {}
            @Override
            public void DataIsInserted_Product() {}
            @Override
            public void DataIsUpdated_Product() {}
            @Override
            public void DataIsDeleted_Product() {}

            @Override
            public void DataIsLoaded_CartItem(ArrayList<CartItem> cartItems, ArrayList<CartItem> _cartItemSelected, ArrayList<String> keys) {
                new PaymentItem_RecyclerView_Config().setConfig(rvw_payment, getContext(), _cartItemSelected, keys);
                UpdateTotalAmount_Payment(_cartItemSelected);
            }

            @Override
            public void DataIsInserted_CartItem() {}
            @Override
            public void DataIsUpdated_CartItem(ArrayList<CartItem> cartItemSelected) {}
            @Override
            public void DataIsDeleted_CartItem(ArrayList<CartItem> cartItem) {}

            @Override
            public void HasTheSelectedProduct_CartItem(boolean isEmpty) {

            }

            @Override
            public void DataIsLoaded_User(User user) {

            }
        });

        //
        this.ibn_undo_Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentDialog.dismiss();
            }
        });

        this.btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrder orderApi = new CreateOrder();

                try {
                    JSONObject data = orderApi.createOrder("10000");
                    String code = data.getString("return_code");

                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder(getActivity(), token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {

                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {

                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {

                            }
                        });
                    }

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        paymentDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        paymentDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        paymentDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        paymentDialog.getWindow().setGravity(Gravity.BOTTOM);

        paymentDialog.show();

    }

    private void UpdateTotalAmount_Payment(ArrayList<CartItem> _cartItemSelected)
    {
        String str_total = "";
        if (_cartItemSelected.isEmpty())
        {
            str_total = "0";
        }
        else
        {
            float subtotal_bag = 0;
            for (CartItem cartItem : _cartItemSelected)
            {
                subtotal_bag += cartItem.get_productPrice() * cartItem.get_productNumber();
            }
            str_total = ConvertNumberToString_productPrice((int) subtotal_bag);
        }
        tvw_subtotal_Payment.setText(str_total);
        tvw_total_Payment.setText(str_total);
    }

/*    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        handleNewIntent(getActivity().getIntent());
    }

    private void handleNewIntent(Intent intent) {
        ZaloPaySDK.getInstance().onResult(intent);
    }

}