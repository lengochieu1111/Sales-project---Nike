package com.example.nike;

import androidx.annotation.NonNull;

import com.example.nike.Product.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ApplicationData {
    /* PROPERTY */
    private static ArrayList<Product> _productList =  new ArrayList<Product>();

    private ApplicationData() { }

    public static ArrayList<Product> ProductList() {
        return _productList;
    }

    private void LoadProductList() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) return;

                _productList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String productID = snap.child("_productID").getValue(String.class);
                    Integer productPrice = snap.child("_productPrice").getValue(Integer.class);
                    String productName = snap.child("_productName").getValue(String.class);
                    String productImageLink = snap.child("_productImageLink").getValue(String.class);
                    String productDescription = snap.child("_productDescription").getValue(String.class);
                    String productType = snap.child("_productType").getValue(String.class);

                    /*  Product Size */
                    List<Integer> productSize = new ArrayList<>();
                    Iterable<DataSnapshot> dss_productSize = snap.child("_productSize").getChildren();
                    for (DataSnapshot child : dss_productSize) {
                        productSize.add(child.getValue(Integer.class));
                    }

                    /*  Product Image Color Link */
                    List<List<String>> productImageColorLink = new ArrayList<>();
                    Iterable<DataSnapshot> dss_productImageColorLink = snap.child("_productImageColorLink").getChildren();
                    for (DataSnapshot colorNumber : dss_productImageColorLink) {
                        Iterable<DataSnapshot> dss_imageColorLink = colorNumber.getChildren();
                        ArrayList<String> imageColorLink = new ArrayList<>();
                        for (DataSnapshot icl : dss_imageColorLink) {
                            imageColorLink.add(icl.getValue(String.class));
                        }
                        productImageColorLink.add(imageColorLink);
                    }

                    Product product = new Product(productID, productName, productPrice, productImageLink,
                            productDescription, productSize, productImageColorLink, productType);

                    _productList.add(product);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }



}
