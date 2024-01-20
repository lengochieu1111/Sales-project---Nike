package com.example.nike.Tab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nike.Product.ProductDetails_Activity;
import com.example.nike.R;
import com.example.nike.Shop.ProductAdapter_Old;
import com.example.nike.Shop.ProductModel;
import com.example.nike.Shop.SearchProduct_Activity;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Shop_MainActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_main);

        this.tvw_test = findViewById(R.id.tvw_test);
        this.ibn_searchProduct = findViewById(R.id.ibn_searchProduct);
        this.tlo_shopTab = findViewById(R.id.tlo_shopTab);
        this.grv_shop = findViewById(R.id.grv_shop);

        this.LoadsShopData();
        this.ShowShopView(_productAdapter_Old_men);
        this.HandleClickOnSearchProduct();
        this.HandleClickOnTabLayout();
        this.HandleClickOnProduct();

        //LoadsProductID(0, 1);
        // this.ShowShopView(_productAdapter_men);

    }

    private void LoadsShopData()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() == false) return;

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

                    if (productType.equals("Men"))
                        _productModels_men.add(productModel);
                    else if(productType.equals("Women"))
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

        this._productAdapter_Old_men = new ProductAdapter_Old(this._productModels_men, this);
        this._productAdapter_Old_women = new ProductAdapter_Old(this._productModels_women, this);
        this._productAdapter_Old_kid = new ProductAdapter_Old(this._productModels_kid, this);
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
                // Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                int selectedTabIndex = tlo_shopTab.getSelectedTabPosition();

                // tvw_test.setText(String.valueOf(position));
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
                    if (productType.equals("Men"))
                        _productModels_men.add(productModel);
                    else if(productType.equals("Women"))
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
        Intent shopIntent = new Intent(this, ProductDetails_Activity.class);
        shopIntent.putExtra("productID", productID);
        shopIntent.putExtra("activityIndex", 0);
        startActivity(shopIntent);
    }

    private void HandleClickOnSearchProduct()
    {
        this.ibn_searchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopIntent = new Intent(Shop_MainActivity.this, SearchProduct_Activity.class);
                startActivity(shopIntent);
            }
        });
    }


}