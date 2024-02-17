package com.example.nike.Tab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nike.Bag.CartItem;
import com.example.nike.Bag.CartItem_RecyclerView_Config;
import com.example.nike.Bag.PaymentItem_RecyclerView_Config;
import com.example.nike.FirebaseDataHelper;
import com.example.nike.Product.Product;
import com.example.nike.Profile.User;
import com.example.nike.R;

import java.util.ArrayList;

public class Test_MainActivity extends AppCompatActivity {
    /* PROPERTY */
    /*RecyclerView rvw_bag;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);

        *//*rvw_bag = findViewById(R.id.rvw_bag);
        textView = findViewById(R.id.textView);
        llt_invoice = findViewById(R.id.llt_invoice);
        llt_bag = findViewById(R.id.llt_bag);
        pbr_loadding_bag = findViewById(R.id.pbr_loadding_bag);
        tvw_subtotal_bag = findViewById(R.id.tvw_subtotal_bag);
        tvw_total_bag = findViewById(R.id.tvw_total_bag);
        btn_buy = findViewById(R.id.btn_buy);
        rvw_bag.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ProgressBarStatus(true);
        BagStatus(false);
        InvoiceStatus(false);*//*

        *//*
        img_test1 = findViewById(R.id.img_test1);

        String url = "https://firebasestorage.googleapis.com/v0/b/nike-97470.appspot.com/o/Products%2FNike%20Air%20Force%201%20Mid%20Evo%2FMainProductImage%2Fair-force-1-mid-evo-shoes-1HPsJQ.png?alt=media&token=c2e47ea7-9b2e-479b-91ac-49c159cd6299";
        Glide.with(this.img_test1.getContext()).load(url).into(this.img_test1);*//*

        *//*this.tvw_test = findViewById(R.id.tvw_test);
        this.ibn_searchProduct = findViewById(R.id.ibn_searchProduct);
        this.tlo_shopTab = findViewById(R.id.tlo_shopTab);
        this.grv_shop = findViewById(R.id.grv_shop);

        this.LoadsShopData();
        this.ShowShopView(_productAdapter_Old_men);
        this.HandleClickOnSearchProduct();
        this.HandleClickOnTabLayout();
        this.HandleClickOnProduct();
        *//*

        *//*new FirebaseDataHelper().ReadTheCartItemList(new FirebaseDataHelper.DataStatus() {
            @Override
            public void DataIsLoaded_Product(ArrayList<Product> products, ArrayList<String> keys) { }

            @Override
            public void DataIsLoaded_CartItem(ArrayList<CartItem> cartItems, ArrayList<CartItem> _cartItemSelected, ArrayList<String> keys) {
                ProgressBarStatus(false);
                BagStatus(true);

                new CartItem_RecyclerView_Config().setConfig(rvw_bag, Test_MainActivity.this, cartItems, keys);
                UpdateTotalAmount_Bag(_cartItemSelected);

                if (!cartItems.isEmpty())
                    InvoiceStatus(true);
                else
                    InvoiceStatus(false);

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
            public void HasTheSelectedProduct_CartItem(boolean hasTheSelectedProduct) {

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
                ShowPaymentDialog();
            }
        });*//*

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
        final Dialog paymentDialog = new Dialog(Test_MainActivity.this);
        paymentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        paymentDialog.setContentView(R.layout.payment_bottom_sheet_layout);

        this.ibn_undo_Payment = paymentDialog.findViewById(R.id.ibn_undo_Payment);
        this.tvw_productName_Payment = paymentDialog.findViewById(R.id.tvw_name_Payment);
        this.tvw_phoneNumber_Payment = paymentDialog.findViewById(R.id.tvw_phoneNumber_Payment);
        this.tvw_address_Payment = paymentDialog.findViewById(R.id.tvw_address_Payment);
        this.rvw_payment = paymentDialog.findViewById(R.id.rvw_payment);
        this.tvw_subtotal_Payment = paymentDialog.findViewById(R.id.tvw_subtotal_Payment);
        this.tvw_total_Payment = paymentDialog.findViewById(R.id.tvw_total_Payment);
        this.rvw_payment.setLayoutManager(new LinearLayoutManager(Test_MainActivity.this));

        *//* Handle Event *//*
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
                new PaymentItem_RecyclerView_Config().setConfig(rvw_payment, Test_MainActivity.this, _cartItemSelected, keys);
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
    }*/


    /*private void LoadsShopData()
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

                *//* Product Details *//*
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
                ShowSearchProductDialog();
            }
        });
    }

    private void ShowSearchProductDialog()
    {
        final Dialog searchDialog = new Dialog(this);
        searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        searchDialog.setContentView(R.layout.search_product_dialog_layout);

        btn_cancleSearch_SP = searchDialog.findViewById(R.id.btn_cancleSearch_SP);
        edt_searchProductName_SP = searchDialog.findViewById(R.id.edt_searchProductName_SP);
        ibn_searchProduct_SP = searchDialog.findViewById(R.id.ibn_searchProduct_SP);

        *//* Handle Event *//*
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

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(edt_searchProductName_SP.getWindowToken(), 0);

        // Hiển thị bàn phím ảo
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void HandleCancleSearch_SearchDialog(Dialog searchDialog)
    {
        btn_cancleSearch_SP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
                    Toast.makeText(getApplicationContext(), "Yêu cầu nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
                else
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edt_searchProductName_SP.getWindowToken(), 0);

                    Toast.makeText(getApplicationContext(), "Search", Toast.LENGTH_SHORT).show();
                    Intent shopIntent = new Intent(Test_MainActivity.this, SearchProduct_Activity.class);
                    shopIntent.putExtra("productNameSearch", _productNameSearch);
                    startActivity(shopIntent);
                }
            }
        });

    }*/


}