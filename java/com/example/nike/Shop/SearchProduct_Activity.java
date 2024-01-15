package com.example.nike.Shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.nike.Product.ProductDetails_Activity;
import com.example.nike.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchProduct_Activity extends AppCompatActivity {

    /* PROPERTY */
    EditText edt_productName_SP;
    GridView grv_searchResults_SP;
    ImageButton ibn_search_SP;
    ImageButton ibn_filter_SP;
    ImageButton ibn_undo_SP;

    private String _searchKeywords;

    ProductAdapter _productAdapter_total;
    ProductAdapter _productAdapter_men;
    ProductAdapter _productAdapter_women;
    ProductAdapter _productAdapter_kid;
    ArrayList<ProductModel> _productModels_total =  new ArrayList<ProductModel>();
    ArrayList<ProductModel> _productModels_men =  new ArrayList<ProductModel>();
    ArrayList<ProductModel> _productModels_women =  new ArrayList<ProductModel>();
    ArrayList<ProductModel> _productModels_kid =  new ArrayList<ProductModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        edt_productName_SP = findViewById(R.id.edt_productName_SP);
        ibn_search_SP = findViewById(R.id.ibn_search_SP);
        ibn_filter_SP = findViewById(R.id.ibn_filter_SP);
        ibn_undo_SP = findViewById(R.id.ibn_undo_SP);
        // svw_filter_SP = findViewById(R.id.svw_filter_SP);

        grv_searchResults_SP = findViewById(R.id.grv_searchResults_SP);

        // Intent intent =

        this._searchKeywords = "nike";
        this.LoadsShopData(this._searchKeywords);
        this.ShowShopView(_productAdapter_total);
        this.HandleSelectionInFilter();
        // this.HandleClickOnSearchProduct();
        // this.HandleClickOnProduct();

    }

    private void LoadsShopData(String searchKeywords)
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() == false) return;

                _productModels_men.clear();
                _productModels_women.clear();
                _productModels_kid.clear();
                _productModels_total.clear();
                for (DataSnapshot snap : snapshot.getChildren())
                {
                    String productID = snap.child("_productID").getValue(String.class);
                    String productType = snap.child("_productType").getValue(String.class);
                    Integer productPrice = snap.child("_productPrice").getValue(Integer.class);
                    String productName = snap.child("_productName").getValue(String.class);
                    String productImageLink = snap.child("_productImageLink").getValue(String.class);

                    ProductModel productModel = new ProductModel(productID, productName, productPrice, productImageLink);

                    if (productName.toLowerCase().contains(searchKeywords.toLowerCase()))
                    {
                        _productModels_total.add(productModel);

                        if (productType.equals("Men"))
                            _productModels_men.add(productModel);
                        else if(productType.equals("Women"))
                            _productModels_women.add(productModel);
                        else
                            _productModels_kid.add(productModel);
                    }

                }
                _productAdapter_men.notifyDataSetChanged();
                _productAdapter_women.notifyDataSetChanged();
                _productAdapter_kid.notifyDataSetChanged();
                _productAdapter_total.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        this._productAdapter_men = new ProductAdapter(this._productModels_men, this);
        this._productAdapter_women = new ProductAdapter(this._productModels_women, this);
        this._productAdapter_kid = new ProductAdapter(this._productModels_kid, this);
        this._productAdapter_total = new ProductAdapter(this._productModels_total, this);
    }

    private void ShowShopView(ProductAdapter productAdapter)
    {
        this.grv_searchResults_SP.setAdapter(productAdapter);
    }

    private void HandleSelectionInFilter()
    {
        this.ibn_filter_SP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFilter();
            }
        });
    }

    private void ShowFilter()
    {
        final Dialog filterDialog = new Dialog(this);
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filterDialog.setContentView(R.layout.filter_bottom_sheet_layout);

        //
        // filterDialog.dismiss();

        RadioGroup rgp_sort_SP = filterDialog.findViewById(R.id.rgp_sort_SP);
        CheckBox cbx_men_SP = filterDialog.findViewById(R.id.cbx_men_SP);
        CheckBox cbx_women_SP = filterDialog.findViewById(R.id.cbx_women_SP);
        CheckBox cbx_kid_SP = filterDialog.findViewById(R.id.cbx_kid_SP);
        Button btn_reset_Filter = filterDialog.findViewById(R.id.btn_reset_Filter);
        Button btn_apply_Filter = filterDialog.findViewById(R.id.btn_apply_Filter);

        btn_apply_Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.dismiss();
            }
        });


        filterDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        filterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        filterDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        filterDialog.getWindow().setGravity(Gravity.BOTTOM);

        filterDialog.show();
    }

    private void HandleClickOnProduct()
    {
        grv_searchResults_SP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                // tvw_test.setText(String.valueOf(position));
                LoadsProductID(_searchKeywords, position);
            }
        });
    }

    private void LoadsProductID(String searchKeywords, Integer productIndex)
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) return;

                _productModels_men.clear();
                _productModels_women.clear();
                _productModels_kid.clear();
                _productModels_total.clear();
                for (DataSnapshot snap : snapshot.getChildren())
                {
                    String productID = snap.child("_productID").getValue(String.class);
                    String productType = snap.child("_productType").getValue(String.class);
                    Integer productPrice = snap.child("_productPrice").getValue(Integer.class);
                    String productName = snap.child("_productName").getValue(String.class);
                    String productImageLink = snap.child("_productImageLink").getValue(String.class);

                    ProductModel productModel = new ProductModel(productID, productName, productPrice, productImageLink);

                    if (productName.toLowerCase().contains(searchKeywords.toLowerCase()))
                    {
                        _productModels_total.add(productModel);

                        if (productType.equals("Men"))
                            _productModels_men.add(productModel);
                        else if(productType.equals("Women"))
                            _productModels_women.add(productModel);
                        else
                            _productModels_kid.add(productModel);
                    }

                }

                /* GoToProductDetails */
                String productID = _productModels_total.get(productIndex).get_productID();
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
        startActivity(shopIntent);

    }

    private void HandleClickOnSearchProduct()
    {
        this.ibn_search_SP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent Search Product
            }
        });
    }


}