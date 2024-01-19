package com.example.nike.Product;

import java.util.List;

public class Product {
    /* PROPERTY */
    private String _productID;
    private String _productName;
    private Integer _productPrice;
    private String _productImageLink;
    private String _productDescription;
    private List<Integer> _productSize;
    private List<List<String>> _productImageColorLink;
    private String _productType;

    /* CONSTRUCTOR */
    public Product()
    {
    }

    public Product(Product product)
    {
        this._productID = product._productID;
        this._productName = product._productName;
        this._productPrice = product._productPrice;
        this._productImageLink = product._productImageLink;
        this._productDescription = product._productDescription;
        this._productSize = product._productSize;
        this._productImageColorLink = product._productImageColorLink;
        this._productType = product._productType;
    }

    public void ChangeDataProduct(Product product)
    {
        this._productID = product._productID;
        this._productName = product._productName;
        this._productPrice = product._productPrice;
        this._productImageLink = product._productImageLink;
        this._productDescription = product._productDescription;
        this._productSize = product._productSize;
        this._productImageColorLink = product._productImageColorLink;
        this._productType = product._productType;
    }

    public Product(String _productID, String _productName, Integer _productPrice, String _productImageLink, String _productDescription,
                   List<Integer> _productSize, List<List<String>> _productImageColorLink, String _productType) {
        this._productID = _productID;
        this._productName = _productName;
        this._productPrice = _productPrice;
        this._productImageLink = _productImageLink;
        this._productDescription = _productDescription;
        this._productSize = _productSize;
        this._productImageColorLink = _productImageColorLink;
        this._productType = _productType;
    }

    /* SETTER - GETTER */

    public String get_productID() {
        return _productID;
    }

    public void set_productID(String _productID) {
        this._productID = _productID;
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

    public String get_productImageLink() {
        return _productImageLink;
    }

    public void set_productImageLink(String _productImageLink) {
        this._productImageLink = _productImageLink;
    }

    public String get_productDescription() {
        return _productDescription;
    }

    public void set_productDescription(String _productDescription) {
        this._productDescription = _productDescription;
    }

    public List<Integer> get_productSize() {
        return _productSize;
    }

    public void set_productSize(List<Integer> _productSize) {
        this._productSize = _productSize;
    }

    public List<List<String>> get_productImageColorLink() {
        return _productImageColorLink;
    }

    public void set_productImageColorLink(List<List<String>> _productImageColorLink) {
        this._productImageColorLink = _productImageColorLink;
    }

    public String get_productType() {
        return _productType;
    }

    public void set_productType(String _productType) {
        this._productType = _productType;
    }
}
