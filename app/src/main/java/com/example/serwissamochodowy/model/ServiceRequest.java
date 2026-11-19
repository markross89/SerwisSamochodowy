package com.example.serwissamochodowy.model;

public class ServiceRequest {

    private long id;
    private long vehicleId;

    private String category;
    private String subcategory;
    private String serviceName;
    private String problemDescription;

    private String visitDate;
    private String visitTime;
    private String status;
    private String createdAt;

    public ServiceRequest(
            long vehicleId,
            String category,
            String subcategory,
            String serviceName,
            String problemDescription,
            String visitDate,
            String visitTime,
            String status,
            String createdAt
    ) {
        this.vehicleId = vehicleId;
        this.category = category;
        this.subcategory = subcategory;
        this.serviceName = serviceName;
        this.problemDescription = problemDescription;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.status = status;
        this.createdAt = createdAt;
    }

    public ServiceRequest(
            long id,
            long vehicleId,
            String category,
            String subcategory,
            String serviceName,
            String problemDescription,
            String visitDate,
            String visitTime,
            String status,
            String createdAt
    ) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.category = category;
        this.subcategory = subcategory;
        this.serviceName = serviceName;
        this.problemDescription = problemDescription;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.status = status;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}