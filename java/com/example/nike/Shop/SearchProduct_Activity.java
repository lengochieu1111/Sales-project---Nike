package com.example.nike.Shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.nike.FirebaseDataHelper;
import com.example.nike.Product.Product;
import com.example.nike.Product.ProductDetails_Activity;
import com.example.nike.Product.ProductItemDecoration;
import com.example.nike.Product.ENUM_ProductType;
import com.example.nike.Product.Product_RecyclerView_Config;
import com.example.nike.Product.ENUM_SortType;
import com.example.nike.Product.STR_ProductType;
import com.example.nike.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchProduct_Activity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    /* PROPERTY */
    EditText edt_productName_SP;
    RecyclerView rvw_products_SP;
    ImageButton ibn_search_SP;
    ImageButton ibn_filter_SP;
    ImageButton ibn_undo_SP;

    private String _productNameSearch = "";
    private ArrayList<ENUM_ProductType> _productTypeSearch = new ArrayList<>(Arrays.asList(ENUM_ProductType.TOTAL));
    private ENUM_SortType _sortType = ENUM_SortType.FEATURED;

    ProductAdapter_Old _productAdapter_Old_total;
    ProductAdapter_Old _productAdapter_Old_men;
    ProductAdapter_Old _productAdapter_Old_women;
    ProductAdapter_Old _productAdapter_Old_kid;
    ProductAdapter_Old _currentProductAdapterOld;
    ArrayList<ProductModel> _productModels_total =  new ArrayList<ProductModel>();
    ArrayList<ProductModel> _productModels_men =  new ArrayList<ProductModel>();
    ArrayList<ProductModel> _productModels_women =  new ArrayList<ProductModel>();
    ArrayList<ProductModel> _productModels_kid =  new ArrayList<ProductModel>();

    /* Filter Dialog */
    RadioGroup rgp_sort_SP;
    RadioButton rbn_featured_SP;
    RadioButton rbn_lowHigh_SP;
    RadioButton rbn_highLow_SP;
    CheckBox cbx_men_SP;
    CheckBox cbx_women_SP;
    CheckBox cbx_kid_SP ;
    Button btn_reset_Filter;
    Button btn_apply_Filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        edt_productName_SP = findViewById(R.id.edt_productName_SP);
        ibn_search_SP = findViewById(R.id.ibn_search_SP);
        ibn_filter_SP = findViewById(R.id.ibn_filter_SP);
        ibn_undo_SP = findViewById(R.id.ibn_undo_SP);
        // svw_filter_SP = findViewById(R.id.svw_filter_SP);

        // grv_searchResults_SP = findViewById(R.id.grv_searchResults_SP);

        rvw_products_SP = findViewById(R.id.rvw_products_SP);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing); // Chuyển dp sang pixel
        rvw_products_SP.addItemDecoration(new ProductItemDecoration(this, spacingInPixels));
        int numberOfColumns = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        rvw_products_SP.setLayoutManager(layoutManager);

        this.UpdateProductList();

        this.HandlesSearchProductNameFilter();
        this.HandleSelectionInFilter();

        /*
         this.HandleClickOnSearchProduct();
         this.HandleClickOnProduct();*/

    }

    private void HandlesSearchProductNameFilter()
    {
        edt_productName_SP.setImeOptions(EditorInfo.IME_ACTION_NONE);

        edt_productName_SP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0)
                    _productNameSearch = "";
                else
                    _productNameSearch = s.toString().toLowerCase().trim();

                UpdateProductList();
            }
        });
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

        rgp_sort_SP = filterDialog.findViewById(R.id.rgp_sort_SP);
        rbn_featured_SP = filterDialog.findViewById(R.id.rbn_featured_SP);
        rbn_lowHigh_SP = filterDialog.findViewById(R.id.rbn_lowHigh_SP);
        rbn_highLow_SP = filterDialog.findViewById(R.id.rbn_highLow_SP);
        cbx_men_SP = filterDialog.findViewById(R.id.cbx_men_SP);
        cbx_women_SP = filterDialog.findViewById(R.id.cbx_women_SP);
        cbx_kid_SP = filterDialog.findViewById(R.id.cbx_kid_SP);
        btn_reset_Filter = filterDialog.findViewById(R.id.btn_reset_Filter);
        btn_apply_Filter = filterDialog.findViewById(R.id.btn_apply_Filter);

        /* Handle Event */
        HandleSortFilter();
        HandleGenderFilter();
        HandleResetButton(filterDialog);
        HandleApplyButton(filterDialog);

        filterDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        filterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        filterDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        filterDialog.getWindow().setGravity(Gravity.BOTTOM);

        filterDialog.show();
    }

    private void HandleSortFilter()
    {
        if (_sortType == ENUM_SortType.FEATURED)
            rbn_featured_SP.setChecked(true);
        else if (_sortType == ENUM_SortType.LOW_HIGH)
            rbn_lowHigh_SP.setChecked(true);
        else if (_sortType == ENUM_SortType.HIGH_LOW)
            rbn_highLow_SP.setChecked(true);

        rgp_sort_SP.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbn_featured_SP)
                {
                    _sortType = ENUM_SortType.FEATURED;
                }
                else if (checkedId == R.id.rbn_lowHigh_SP)
                {
                    _sortType = ENUM_SortType.LOW_HIGH;
                }
                if (checkedId == R.id.rbn_highLow_SP)
                {
                    _sortType = ENUM_SortType.HIGH_LOW;
                }
            }
        });
    }

    private void HandleGenderFilter()
    {
        if (_productTypeSearch.contains(ENUM_ProductType.TOTAL))
        {
            cbx_men_SP.setChecked(false);
            cbx_women_SP.setChecked(false);
            cbx_kid_SP.setChecked(false);
        }
        else
        {
            if (_productTypeSearch.contains(ENUM_ProductType.MEN))
                cbx_men_SP.setChecked(true);
            if (_productTypeSearch.contains(ENUM_ProductType.WOMEN))
                cbx_women_SP.setChecked(true);
            if (_productTypeSearch.contains(ENUM_ProductType.KID))
                cbx_kid_SP.setChecked(true);
        }

        cbx_men_SP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _productTypeSearch.add(ENUM_ProductType.MEN);
            }
        });

        cbx_women_SP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _productTypeSearch.add(ENUM_ProductType.WOMEN);
            }
        });

        cbx_kid_SP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _productTypeSearch.add(ENUM_ProductType.KID);
            }
        });
    }

    private void HandleResetButton(Dialog filterDialog)
    {
        btn_reset_Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _productTypeSearch.clear();
                _productTypeSearch.add(ENUM_ProductType.TOTAL);
                _sortType = ENUM_SortType.FEATURED;
                UpdateProductList();
                filterDialog.dismiss();
            }
        });
    }
    private void HandleApplyButton(Dialog filterDialog)
    {
        // Apply
        btn_apply_Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked_men = cbx_men_SP.isChecked();
                boolean isChecked_women = cbx_women_SP.isChecked();
                boolean isChecked_kid = cbx_kid_SP.isChecked();
                if (((isChecked_men && isChecked_women && isChecked_kid) || (!isChecked_men && !isChecked_women && !isChecked_kid))
                        && !_productTypeSearch.contains(ENUM_ProductType.TOTAL))
                {
                    _productTypeSearch.clear();
                    _productTypeSearch.add(ENUM_ProductType.TOTAL);
                }
                else
                {
                    if (_productTypeSearch.contains(ENUM_ProductType.TOTAL))
                        _productTypeSearch.clear();

                    if (isChecked_men && !_productTypeSearch.contains(ENUM_ProductType.MEN))
                        _productTypeSearch.add(ENUM_ProductType.MEN);
                    if (isChecked_women && !_productTypeSearch.contains(ENUM_ProductType.WOMEN))
                        _productTypeSearch.add(ENUM_ProductType.WOMEN);
                    if (isChecked_kid && !_productTypeSearch.contains(ENUM_ProductType.KID))
                        _productTypeSearch.add(ENUM_ProductType.KID);
                }

                UpdateProductList();
                filterDialog.dismiss();
            }
        });
    }

    private void UpdateProductList()
    {
        new FirebaseDataHelper().ReadTheProductList(new FirebaseDataHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Product> products, ArrayList<String> keys) {
                new Product_RecyclerView_Config().setConfig(rvw_products_SP, SearchProduct_Activity.this, products, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        }, _productNameSearch, _productTypeSearch, _sortType);
    }

    private void HandleClickOnProduct()
    {
        rvw_products_SP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                        if (productType.equals(STR_ProductType.MEN))
                            _productModels_men.add(productModel);
                        else if(productType.equals(STR_ProductType.WOMEN))
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