package com.example.nike.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.nike.Bag.CartItem;
import com.example.nike.Bag.PaymentItem_RecyclerView_Config;
import com.example.nike.FirebaseDataHelper;
import com.example.nike.Product.Product;
import com.example.nike.Profile.User;
import com.example.nike.R;
import com.example.nike.zalopay.Api.CreateOrder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.util.ArrayList;

import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    FirebaseUser _user;
    String _profileKey;

    TextView textView_name_Profile;
    TextView textView_phoneNumber_Profile;
    TextView textView_address_Profile;
    Button button_editProfile_Profile;
    Button button_logOut_Profile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        this._user = FirebaseAuth.getInstance().getCurrentUser();
        this._profileKey = this._user.getUid();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        textView_name_Profile = view.findViewById(R.id.textView_name_Profile);
        textView_phoneNumber_Profile = view.findViewById(R.id.textView_phoneNumber_Profile);
        textView_address_Profile = view.findViewById(R.id.textView_address_Profile);
        button_editProfile_Profile = view.findViewById(R.id.button_editProfile_Profile);
        button_logOut_Profile = view.findViewById(R.id.button_logOut_Profile);

        new FirebaseDataHelper().ReadUser(new FirebaseDataHelper.DataStatus() {
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
            public void HasTheSelectedProduct_CartItem(boolean isEmpty) {}
            @Override
            public void DataIsLoaded_User(User user) {
                if (user.equals(new User())) return;

                textView_name_Profile.setText(user.get_name());
                textView_phoneNumber_Profile.setText(user.get_phoneNumber());
                textView_address_Profile.setText(user.get_address());
            }
        });

        button_editProfile_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowEditProfileDialog();
            }
        });

        return view;
    }

    private void ShowEditProfileDialog()
    {
        final Dialog editProfileDialog = new Dialog(getContext());
        editProfileDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editProfileDialog.setContentView(R.layout.payment_bottom_sheet_layout);

        /* Handle Event */


        editProfileDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        editProfileDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editProfileDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        editProfileDialog.getWindow().setGravity(Gravity.BOTTOM);

        editProfileDialog.show();
    }


}