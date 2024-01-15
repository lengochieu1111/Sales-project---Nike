package com.example.nike.Shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nike.R;

import java.util.ArrayList;
import java.util.List;

/*public class ProductAdapter extends BaseAdapter {
    *//* PROPERTY *//*
    private ArrayList<ProductModel> _productsList;
    private Context _context;

    *//* CONSTRUCTOR *//*
    public ProductAdapter()
    {

    }
    public ProductAdapter(ArrayList<ProductModel> _productsList, Context _context) {
        this._productsList = _productsList;
        this._context = _context;
    }

    *//* SETTER - GETTER *//*
    public ArrayList<ProductModel> get_productsList() {
        return _productsList;
    }

    public void set_productsList(ArrayList<ProductModel> _productsList) {
        this._productsList = _productsList;
    }

    public Context get_context() {
        return _context;
    }

    public void set_context(Context _context) {
        this._context = _context;
    }

    *//* FUNCTION *//*
    @Override
    public int getCount() {
        return this._productsList.size();
    }

    @Override
    public Object getItem(int position) {
        return this._productsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductView productView;

        if (convertView == null)
        {
            productView = new ProductView();

            convertView = LayoutInflater.from(_context).inflate(R.layout.product_model, null);

            productView._productImage = convertView.findViewById(R.id.imv_image);
            productView._productName = convertView.findViewById(R.id.tev_name);
            productView._productPrice = convertView.findViewById(R.id.tev_price);
            convertView.setTag(productView);
        }
        else
        {
            productView = (ProductView) convertView.getTag();
        }

        Glide.with(productView._productImage.getContext()).load(this._productsList.get(position).get_productImageLink()).into(productView._productImage);
        productView._productName.setText(this._productsList.get(position).get_productName());

        *//* Product Price *//*
        int i_productPrice = this._productsList.get(position).get_productPrice();
        String str_productPrice =  this.ConvertNumberToString_productPrice(i_productPrice);
        productView._productPrice.setText(str_productPrice);

        return convertView;
    }


    private String ConvertNumberToString_productPrice(int i_productPrice)
    {
        String str_productPrice = String.valueOf(i_productPrice);
        int numberLenght = str_productPrice.length();
        int surplus = numberLenght % 3;
        int dotNumber = numberLenght / 3;
        if (numberLenght % 3 == 0)
            dotNumber -= 1;

        String str_result = "đ";
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


    *//* View *//*
    class ProductView
    {
        private ImageView _productImage;
        private TextView _productName, _productPrice;
    }*/

public class ProductAdapter extends BaseAdapter {
    /* PROPERTY */
    private ArrayList<ProductModel> _productsList;
    private Context _context;

    /* CONSTRUCTOR */
    public ProductAdapter()
    {

    }
    public ProductAdapter(ArrayList<ProductModel> _productsList, Context _context) {
        this._productsList = _productsList;
        this._context = _context;
    }

    public ProductAdapter(ProductAdapter productAdapter) {
        this._productsList = productAdapter._productsList;
        this._context = productAdapter._context;
    }

    /* SETTER - GETTER */
    public ArrayList<ProductModel> get_productsList() {
        return _productsList;
    }

    public void set_productsList(ArrayList<ProductModel> _productsList) {
        this._productsList = _productsList;
    }

    public Context get_context() {
        return _context;
    }

    public void set_context(Context _context) {
        this._context = _context;
    }

    /* FUNCTION */
    @Override
    public int getCount() {
        return this._productsList.size();
    }

    @Override
    public Object getItem(int position) {
        return this._productsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductView productView;

        if (convertView == null)
        {
            productView = new ProductView();

            convertView = LayoutInflater.from(_context).inflate(R.layout.product_model, null);

            productView._productImage = convertView.findViewById(R.id.imv_image);
            productView._productName = convertView.findViewById(R.id.tev_name);
            productView._productPrice = convertView.findViewById(R.id.tev_price);
            convertView.setTag(productView);
        }
        else
        {
            productView = (ProductView) convertView.getTag();
        }

        Glide.with(productView._productImage.getContext()).load(this._productsList.get(position).get_productImageLink()).into(productView._productImage);
        productView._productName.setText(this._productsList.get(position).get_productName());

        /* Product Price */
        int i_productPrice = this._productsList.get(position).get_productPrice();
        String str_productPrice =  this.ConvertNumberToString_productPrice(i_productPrice);
        productView._productPrice.setText(str_productPrice);

        return convertView;
    }


    private String ConvertNumberToString_productPrice(int i_productPrice)
    {
        String str_productPrice = String.valueOf(i_productPrice);
        int numberLenght = str_productPrice.length();
        int surplus = numberLenght % 3;
        int dotNumber = numberLenght / 3;
        if (numberLenght % 3 == 0)
            dotNumber -= 1;

        String str_result = "đ";
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


    /* View */
    class ProductView
    {
        private ImageView _productImage;
        private TextView _productName, _productPrice;
    }



}
