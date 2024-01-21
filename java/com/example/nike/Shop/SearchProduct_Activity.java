package com.example.nike.Shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nike.FirebaseDataHelper;
import com.example.nike.Product.Product;
import com.example.nike.Product.ProductDetails_Activity;
import com.example.nike.Product.ProductItemDecoration;
import com.example.nike.Product.ENUM_ProductType;
import com.example.nike.Product.Product_RecyclerView_Config;
import com.example.nike.Product.ENUM_SortType;
import com.example.nike.Product.STR_ProductType;
import com.example.nike.R;
import com.example.nike.Tab.Shop_MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchProduct_Activity extends AppCompatActivity {
    /* PROPERTY */
    TextView tvw_productName_SP;
    RecyclerView rvw_products_SP;
    ImageButton ibn_search_SP;
    ImageButton ibn_filter_SP;
    ImageButton ibn_undo_SP;

    private String _productNameSearch = "";
    private ArrayList<ENUM_ProductType> _productTypeSearch = new ArrayList<>(Arrays.asList(ENUM_ProductType.TOTAL));
    private ENUM_SortType _sortType = ENUM_SortType.FEATURED;

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

    //
    Button btn_cancleSearch_SP;
    EditText edt_searchProductName_SP;
    ImageButton ibn_searchProduct_SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        tvw_productName_SP = findViewById(R.id.tvw_productName_SP);
        ibn_search_SP = findViewById(R.id.ibn_search_SP);
        ibn_filter_SP = findViewById(R.id.ibn_filter_SP);
        ibn_undo_SP = findViewById(R.id.ibn_undo_SP);
        rvw_products_SP = findViewById(R.id.rvw_products_SP);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing); // Chuyển dp sang pixel
        rvw_products_SP.addItemDecoration(new ProductItemDecoration(this, spacingInPixels));
        int numberOfColumns = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        rvw_products_SP.setLayoutManager(layoutManager);

        Intent resultIntent = getIntent();
        this._productNameSearch = resultIntent.getExtras().getString("productNameSearch");
        tvw_productName_SP.setText(this._productNameSearch);

        this.UpdateProductList();
        this.HandlesSearchProductNameFilter();
        this.HandleSelectionInFilter();
        this.HandleClickTheUndoButton();
        this.HandleClickTheSearchButton();

    }

    private void HandlesSearchProductNameFilter()
    {
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
                new Product_RecyclerView_Config().setConfig(rvw_products_SP, SearchProduct_Activity.this, products, keys, 1);
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

    private void HandleClickTheUndoButton()
    {
        ibn_undo_SP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowSearchProductDialog();
            }
        });
    }

    private void HandleClickTheSearchButton()
    {
        this.ibn_search_SP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowSearchProductDialog();
            }
        });
    }

    /*private void HideVirtualKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt_productName_SP.getWindowToken(), 0);
    }*/

    private void ShowSearchProductDialog()
    {
        final Dialog searchDialog = new Dialog(this);
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
    }

    private void HandleCancleSearch_SearchDialog(Dialog searchDialog)
    {
        btn_cancleSearch_SP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_SHORT).show();

                Intent shopIntent = new Intent(SearchProduct_Activity.this, Shop_MainActivity.class);
                shopIntent.putExtra("productNameSearch", _productNameSearch);
                startActivity(shopIntent);
            }
        });

    }

}