package com.example.serwissamochodowy.model;


    public class Vehicle {
        private long id;
        private String brand;
        private String model;
        private String registration;
        private String vin;
        private String color;
        private String registrationDate;

        // Konstruktor do tworzenia nowego auta (bez ID, bo baza sama nadaje auto-inkrementację)
        public Vehicle(String brand, String model, String registration, String vin, String color, String registrationDate) {
            this.brand = brand;
            this.model = model;
            this.registration = registration;
            this.vin = vin;
            this.color = color;
            this.registrationDate = registrationDate;
        }

        // Konstruktor pełny (np. gdy pobieramy auto z bazy danych wraz z jego ID)
        public Vehicle(long id, String brand, String model, String registration, String vin, String color, String registrationDate) {
            this.id = id;
            this.brand = brand;
            this.model = model;
            this.registration = registration;
            this.vin = vin;
            this.color = color;
            this.registrationDate = registrationDate;
        }

        // --- GETTERY (pobieranie danych) ---
        public long getId() {
            return id;
        }

        public String getBrand() {
            return brand;
        }

        public String getModel() {
            return model;
        }

        public String getRegistration() {
            return registration;
        }

        public String getVin() {
            return vin;
        }

        public String getColor() {
            return color;
        }

        public String getRegistrationDate() {
            return registrationDate;
        }

        // --- SETTER (ustawianie / modyfikacja danych) ---
        public void setId(long id) {
            this.id = id;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public void setRegistration(String registration) {
            this.registration = registration;
        }

        public void setVin(String vin) {
            this.vin = vin;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setRegistrationDate(String registrationDate) {
            this.registrationDate = registrationDate;
        }
    }
