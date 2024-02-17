package com.example.nike.Profile;

public class User
{
    private String _name;
    private String _phoneNumber;
    private String _address;

    public User() { }

    public User(String _name, String _phoneNumber, String _address) {
        this._name = _name;
        this._phoneNumber = _phoneNumber;
        this._address = _address;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_phoneNumber() {
        return _phoneNumber;
    }

    public void set_phoneNumber(String _phoneNumber) {
        this._phoneNumber = _phoneNumber;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }
}
