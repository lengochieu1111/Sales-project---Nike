package com.example.nike.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nike.Product.ProductDetails_Activity;
import com.example.nike.Product.STR_ProductType;
import com.example.nike.Shop.ProductAdapter_Old;
import com.example.nike.Shop.ProductModel;
import com.example.nike.R;
import com.example.nike.Shop.SearchProduct_Activity;
import com.example.nike.Tab.ENUM_ActivityType;
import com.example.nike.Tab.STR_IntentKey;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /* PROPERTY */
    TextView tvw_test;
    ImageButton ibn_searchProduct;
    TabLayout tlo_shopTab;
    GridView grv_shop;
    ProductAdapter_Old _productAdapter_Old_men;
    ProductAdapter_Old _productAdapter_Old_women;
    ProductAdapter_Old _productAdapter_Old_kid;
    ArrayList<ProductModel> _productModels_men =  new ArrayList<ProductModel>();
    ArrayList<ProductModel> _productModels_women =  new ArrayList<ProductModel>();
    ArrayList<ProductModel> _productModels_kid =  new ArrayList<ProductModel>();

    Button btn_cancleSearch_SP;
    EditText edt_searchProductName_SP;
    ImageButton ibn_searchProduct_SP;

    private String _productNameSearch = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //
        this.LoadsShopData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        this.tvw_test = view.findViewById(R.id.tvw_test);
        this.ibn_searchProduct = view.findViewById(R.id.ibn_searchProduct);
        this.tlo_shopTab = view.findViewById(R.id.tlo_shopTab);
        this.grv_shop = view.findViewById(R.id.grv_shop);

        // LoadsAndRendersTheStoreView
        this.ShowShopView(_productAdapter_Old_men);
        this.HandleClickOnSearchProduct();
        this.HandleClickOnTabLayout();
        this.HandleClickOnProduct();

        // Inflate the layout for this fragment
        return view;
    }

    // NEW
    private void LoadsShopData()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) return;

                _productModels_men.clear();
                _productModels_women.clear();
                _productModels_kid.clear();
                for (DataSnapshot snap : snapshot.getChildren())
                {
                    String productID = snap.child("_productID").getValue(String.class);
                    String productType = snap.child("_productType").getValue(String.class);
                    Integer productPrice = snap.child("_productPrice").getValue(Integer.class);
                    String productName = snap.child("_productName").getValue(String.class);
                    String productImageLink = snap.child("_productImageLink").getValue(String.class);

                    ProductModel productModel = new ProductModel(productID, productName, productPrice, productImageLink);

                    if (productType.equals(STR_ProductType.MEN))
                        _productModels_men.add(productModel);
                    else if(productType.equals(STR_ProductType.WOMEN))
                        _productModels_women.add(productModel);
                    else
                        _productModels_kid.add(productModel);

                }
                _productAdapter_Old_men.notifyDataSetChanged();
                _productAdapter_Old_women.notifyDataSetChanged();
                _productAdapter_Old_kid.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        this._productAdapter_Old_men = new ProductAdapter_Old(this._productModels_men, getContext());
        this._productAdapter_Old_women = new ProductAdapter_Old(this._productModels_women, getContext());
        this._productAdapter_Old_kid = new ProductAdapter_Old(this._productModels_kid, getContext());
    }

    private void ShowShopView(ProductAdapter_Old productAdapterOld)
    {
        this.grv_shop.setAdapter(productAdapterOld);
    }

    private void HandleClickOnTabLayout()
    {
        tlo_shopTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0)
                    ShowShopView(_productAdapter_Old_men);
                else if (tab.getPosition() == 1)
                    ShowShopView(_productAdapter_Old_women);
                else
                    ShowShopView(_productAdapter_Old_kid);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void HandleClickOnProduct()
    {
        grv_shop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedTabIndex = tlo_shopTab.getSelectedTabPosition();
                LoadsProductID(selectedTabIndex, position);
            }
        });
    }

    private void LoadsProductID(Integer tabLayoutIndex, Integer productIndex)
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) return;

                for (DataSnapshot snap : snapshot.getChildren())
                {
                    String productID = snap.child("_productID").getValue(String.class);
                    String productType = snap.child("_productType").getValue(String.class);
                    Integer productPrice = snap.child("_productPrice").getValue(Integer.class);
                    String productName = snap.child("_productName").getValue(String.class);
                    String productImageLink = snap.child("_productImageLink").getValue(String.class);

                    ProductModel productModel = new ProductModel(productID, productName, productPrice, productImageLink);
                    if (productType.equals(STR_ProductType.MEN))
                        _productModels_men.add(productModel);
                    else if(productType.equals(STR_ProductType.WOMEN))
                        _productModels_women.add(productModel);
                    else
                        _productModels_kid.add(productModel);

                }

                ArrayList<ProductModel> productModels =  new ArrayList<ProductModel>();
                if (tabLayoutIndex == 0)
                    productModels = _productModels_men;
                else if (tabLayoutIndex == 1)
                    productModels = _productModels_women;
                else
                    productModels = _productModels_kid;

                /* Product Details */
                String productID = productModels.get(productIndex).get_productID();
                GoToProductDetails(productID);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void GoToProductDetails(String productID)
    {
        Intent shopIntent = new Intent(getActivity(), ProductDetails_Activity.class);
        shopIntent.putExtra(STR_IntentKey.ProductID, productID);
        shopIntent.putExtra(STR_IntentKey.ActivityType, ENUM_ActivityType.Shop);
        startActivity(shopIntent);
    }

    private void HandleClickOnSearchProduct()
    {
        this.ibn_searchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowSearchProductDialog();
            }
        });
    }

    private void ShowSearchProductDialog()
    {
        final Dialog searchDialog = new Dialog(getContext());
        searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        searchDialog.setContentView(R.layout.search_product_dialog_layout);

        btn_cancleSearch_SP = searchDialog.findViewById(R.id.btn_cancleSearch_SP);
        edt_searchProductName_SP = searchDialog.findViewById(R.id.edt_searchProductName_SP);
        ibn_searchProduct_SP = searchDialog.findViewById(R.id.ibn_searchProduct_SP);

        /* Handle Event */
        HandleCancleSearch_SearchDialog(searchDialog);
        HandlesSearchProductName_SearchDialog();
        HandleSearchProduct_SearchDialog();

        searchDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        searchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        searchDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        searchDialog.getWindow().setGravity(Gravity.BOTTOM);

        searchDialog.show();

        HideVirtualKeyboard();
    }

    private void HideVirtualKeyboard()
    {
        edt_searchProductName_SP.requestFocus();

        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(edt_searchProductName_SP.getWindowToken(), 0);

        // Hiển thị bàn phím ảo
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void HandleCancleSearch_SearchDialog(Dialog searchDialog)
    {
        btn_cancleSearch_SP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edt_searchProductName_SP.getWindowToken(), 0);
                searchDialog.dismiss();
            }
        });
    }

    private void HandlesSearchProductName_SearchDialog()
    {
        edt_searchProductName_SP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                for (int i = 0; i < s.length(); i++)
                {
                    if (s.charAt(i) == '\n')
                    {
                        s.replace(i, i + 1, "");
                    }
                }

                if (s == null || s.length() == 0)
                    _productNameSearch = "";
                else
                    _productNameSearch = s.toString().toLowerCase().trim();
            }
        });
    }

    private void HandleSearchProduct_SearchDialog()
    {
        ibn_searchProduct_SP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_productNameSearch.equals(""))
                    Toast.makeText(getContext().getApplicationContext(), "Yêu cầu nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
                else
                {
                    InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edt_searchProductName_SP.getWindowToken(), 0);

                    Toast.makeText(getContext().getApplicationContext(), "Search", Toast.LENGTH_SHORT).show();

                    Intent shopIntent = new Intent(getActivity(), SearchProduct_Activity.class);
                    shopIntent.putExtra(STR_IntentKey.ProductNameSearch, _productNameSearch);
                    startActivity(shopIntent);
                }
            }
        });

    }

}