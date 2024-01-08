package com.example.nike.Shop;

public class ProductModel {
    /* PROPERTY */
    private String _productID;
    private String _productName;
    private Integer _productPrice;
    private String _productImageLink;

    /* CONSTRUCTOR */
    public ProductModel()
    {
    }

    public ProductModel(String _productID, String _productName, Integer _productPrice, String _productImageLink) {
        this._productID = _productID;
        this._productName = _productName;
        this._productPrice = _productPrice;
        this._productImageLink = _productImageLink;
    }

    /* SETTER - GETTER */

    public Integer get_productPrice() {
        return _productPrice;
    }

    public void set_productPrice(Integer _productPrice) {
        this._productPrice = _productPrice;
    }

    public String get_productName() {
        return _productName;
    }

    public void set_productName(String _productName) {
        this._productName = _productName;
    }

    public String get_productImageLink() {
        return _productImageLink;
    }

    public void set_productImageLink(String _productImageLink) {
        this._productImageLink = _productImageLink;
    }

    public String get_productID() {
        return _productID;
    }

    public void set_productID(String _productID) {
        this._productID = _productID;
    }
}
