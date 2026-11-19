package com.example.serwissamochodowy.model;

public class Repair {
    private long id;
    private long vehicleId;
    private String repairDate;
    private String description;
    private double cost;

    // Konstruktor do tworzenia nowej naprawy (bez ID, bo baza sama nadaje auto-inkrementację)
    public Repair(long vehicleId, String repairDate, String description, double cost) {
        this.vehicleId = vehicleId;
        this.repairDate = repairDate;
        this.description = description;
        this.cost = cost;
    }

    // Konstruktor pełny (gdy pobieramy naprawę z bazy danych wraz z jej ID)
    public Repair(long id, long vehicleId, String repairDate, String description, double cost) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.repairDate = repairDate;
        this.description = description;
        this.cost = cost;
    }

    // --- GETTERY ---
    public long getId() { return id; }
    public long getVehicleId() { return vehicleId; }
    public String getRepairDate() { return repairDate; }
    public String getDescription() { return description; }
    public double getCost() { return cost; }

    // --- SETTERY ---
    public void setId(long id) { this.id = id; }
    public void setVehicleId(long vehicleId) { this.vehicleId = vehicleId; }
    public void setRepairDate(String repairDate) { this.repairDate = repairDate; }
    public void setDescription(String description) { this.description = description; }
    public void setCost(double cost) { this.cost = cost; }
}