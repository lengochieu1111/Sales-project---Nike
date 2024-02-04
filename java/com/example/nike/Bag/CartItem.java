package com.example.nike.Bag;

import java.util.List;

public class CartItem {
    /* PROPERTY */
    private String _productID;
    private String _productImageLink;
    private String _productName;
    private Integer _productPrice;
    private String _productColor;
    private Integer _productSize;
    private String _productType;
    private Integer _productNumber = 1;
    private boolean _isSelected = false;

    /* CONSTRUCTOR */
    public CartItem() { }

    public CartItem(CartItem cartItem)
    {
        this._productID = cartItem._productID;
        this._productImageLink = cartItem._productImageLink;
        this._productName = cartItem._productName;
        this._productPrice = cartItem._productPrice;
        this._productColor = cartItem._productColor;
        this._productSize = cartItem._productSize;
        this._productType = cartItem._productType;
        this._productNumber = cartItem._productNumber;
        this._isSelected = cartItem._isSelected;
    }

    public CartItem(String _productID, String _productImageLink, String _productName, Integer _productPrice,
                    String _productColor, Integer _productSize, String _productType, Integer _productNumber, boolean _isSelected) {
        this._productID = _productID;
        this._productImageLink = _productImageLink;
        this._productName = _productName;
        this._productPrice = _productPrice;
        this._productColor = _productColor;
        this._productSize = _productSize;
        this._productType = _productType;
        this._productNumber = _productNumber;
        this._isSelected = _isSelected;
    }

    /* SETTER - GETTER */
    public String get_productID() {
        return _productID;
    }

    public void set_productID(String _productID) {
        this._productID = _productID;
    }

    public String get_productImageLink() {
        return _productImageLink;
    }

    public void set_productImageLink(String _productImageLink) {
        this._productImageLink = _productImageLink;
    }

    public String get_productName() {
        return _productName;
    }

    public void set_productName(String _productName) {
        this._productName = _productName;
    }

    public Integer get_productPrice() {
        return _productPrice;
    }

    public void set_productPrice(Integer _productPrice) {
        this._productPrice = _productPrice;
    }

    public String get_productColor() {
        return _productColor;
    }

    public void set_productColor(String _productColor) {
        this._productColor = _productColor;
    }

    public Integer get_productSize() {
        return _productSize;
    }

    public void set_productSize(Integer _productSize) {
        this._productSize = _productSize;
    }

    public String get_productType() {
        return _productType;
    }

    public void set_productType(String _productType) {
        this._productType = _productType;
    }

    public Integer get_productNumber() {
        return _productNumber;
    }

    public void set_productNumber(Integer _productNumber) {
        this._productNumber = _productNumber;
    }

    public boolean get_isSelected() { return _isSelected; }

    public void set_isSelected(boolean _isSelected) { this._isSelected = _isSelected; }
}
