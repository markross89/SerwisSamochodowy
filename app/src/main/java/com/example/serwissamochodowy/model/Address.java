package com.example.serwissamochodowy.model;

public class Address {
    private String street;
    private String houseNumber;
    private String flatNumber; // Opcjonalnie (np. dla mieszkań)
    private String city;
    private String postalCode;
    private String country;

    public Address(String street, String houseNumber, String flatNumber, String city, String postalCode, String country) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.flatNumber = flatNumber;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    // --- GETTERY ---
    public String getStreet() { return street; }
    public String getHouseNumber() { return houseNumber; }
    public String getFlatNumber() { return flatNumber; }
    public String getCity() { return city; }
    public String getPostalCode() { return postalCode; }
    public String getCountry() { return country; }

    // --- SETTERY ---
    public void setStreet(String street) { this.street = street; }
    public void setHouseNumber(String houseNumber) { this.houseNumber = houseNumber; }
    public void setFlatNumber(String flatNumber) { this.flatNumber = flatNumber; }
    public void setCity(String city) { this.city = city; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public void setCountry(String country) { this.country = country; }
}