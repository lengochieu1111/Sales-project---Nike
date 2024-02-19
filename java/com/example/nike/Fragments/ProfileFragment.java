package com.example.nike.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nike.Bag.CartItem;
import com.example.nike.Bag.PaymentItem_RecyclerView_Config;
import com.example.nike.FirebaseDataHelper;
import com.example.nike.Login.Login_Activity;
import com.example.nike.Product.Product;
import com.example.nike.Profile.User;
import com.example.nike.R;
// import com.example.nike.zalopay.Api.CreateOrder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.util.ArrayList;

import vn.zalopay.sdk.Utils;
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

    ProgressBar progressBar_loadding_Profile;
    LinearLayout llt_profile;

    TextView textView_name_Profile;
    TextView textView_phoneNumber_Profile;
    TextView textView_address_Profile;
    Button button_editProfile_Profile;
    Button button_logOut_Profile;

    /* Edit Profile */
    EditText editText_name_EditProfile;
    EditText editText_phoneNumber_EditProfile;
    EditText editText_address_EditProfile;
    Button button_cancle_EditProfile;
    Button button_apply_EditProfile;

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

        progressBar_loadding_Profile = view.findViewById(R.id.progressBar_loadding_Profile);
        llt_profile = view.findViewById(R.id.llt_profile);

        textView_name_Profile = view.findViewById(R.id.textView_name_Profile);
        textView_phoneNumber_Profile = view.findViewById(R.id.textView_phoneNumber_Profile);
        textView_address_Profile = view.findViewById(R.id.textView_address_Profile);
        button_editProfile_Profile = view.findViewById(R.id.button_editProfile_Profile);
        button_logOut_Profile = view.findViewById(R.id.button_logOut_Profile);

        progressBar_loadding_Profile.setVisibility(View.VISIBLE);
        llt_profile.setVisibility(View.GONE);

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
                progressBar_loadding_Profile.setVisibility(View.GONE);
                llt_profile.setVisibility(View.VISIBLE);

                if (user.equals(new User())) return;
                UpdateUserInformation(user);
            }
        });

        button_editProfile_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowEditProfileDialog();
            }
        });

        button_logOut_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                GoToLoginActivity();
            }
        });

        return view;
    }

    private void ShowEditProfileDialog()
    {
        final Dialog editProfileDialog = new Dialog(getContext());
        editProfileDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editProfileDialog.setContentView(R.layout.edit_profile_bottom_sheet_layout);

        this.editText_name_EditProfile = editProfileDialog.findViewById(R.id.editText_name_EditProfile);
        this.editText_phoneNumber_EditProfile = editProfileDialog.findViewById(R.id.editText_phoneNumber_EditProfile);
        this.editText_address_EditProfile = editProfileDialog.findViewById(R.id.editText_address_EditProfile);
        this.button_cancle_EditProfile = editProfileDialog.findViewById(R.id.button_cancle_EditProfile);
        this.button_apply_EditProfile = editProfileDialog.findViewById(R.id.button_apply_EditProfile);

        /* Handle Event */

        button_cancle_EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfileDialog.dismiss();
            }
        });

        button_apply_EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(editText_name_EditProfile.getText());
                String phoneNumber = String.valueOf(editText_phoneNumber_EditProfile.getText());
                String address = String.valueOf(editText_address_EditProfile.getText());

                if (TextUtils.isEmpty(name) ||  TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(address))
                {
                    Toast.makeText(getContext(), "Please enter complete information.", Toast.LENGTH_SHORT).show();
                    return;
                }


                User user = new User();
                user.set_name(name);
                user.set_phoneNumber(phoneNumber);
                user.set_address(address);

                new FirebaseDataHelper().UpdateUserInformation(user, new FirebaseDataHelper.DataStatus() {
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
                        UpdateUserInformation(user);
                    }
                });

                editProfileDialog.dismiss();
            }
        });


        editProfileDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        editProfileDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editProfileDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        editProfileDialog.getWindow().setGravity(Gravity.BOTTOM);

        editProfileDialog.show();
    }


    private void UpdateUserInformation(User user)
    {
        textView_name_Profile.setText(user.get_name());
        textView_phoneNumber_Profile.setText(user.get_phoneNumber());
        textView_address_Profile.setText(user.get_address());
    }

    private void GoToLoginActivity()
    {
        Intent loginIntent = new Intent(getContext(), Login_Activity.class);
        startActivity(loginIntent);
    }

}