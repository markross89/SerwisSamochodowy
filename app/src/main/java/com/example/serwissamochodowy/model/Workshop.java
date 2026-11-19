
package com.example.serwissamochodowy.model;

public class Workshop {

    private int id;
    private String name;
    private Address address;
    private String phone;
    private String openingHours;

    public Workshop(int id, String name, Address address, String phone, String openingHours) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.openingHours = openingHours;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }
}
