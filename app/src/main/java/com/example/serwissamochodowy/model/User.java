package com.example.serwissamochodowy.model;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Address address; // Obiekt adresu z ulicą, numerem domu/mieszkania, miastem itd.

    public User(int id, String firstName, String lastName, String email, String phone, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // --- GETTERY ---
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public Address getAddress() { return address; }

    // --- SETTERY ---
    public void setId(int id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(Address address) { this.address = address; }
}