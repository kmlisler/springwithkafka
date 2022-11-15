package com.kamilisler.springwithkafka.model;

public class MappedPackage {
    private Long id;
    private String createdAt;
    private String lastUpdatedAt;
    private Integer collectionDuration;
    private Integer deliveryDuration;
    private Integer eta;
    private Integer leadTime;
    private Boolean orderInTime;

    public MappedPackage(Long id, String createdAt, String lastUpdatedAt, Integer collectionDuration, Integer deliveryDuration, Integer eta, Integer leadTime, Boolean orderInTime) {
        this.id = id;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.collectionDuration = collectionDuration;
        this.deliveryDuration = deliveryDuration;
        this.eta = eta;
        this.leadTime = leadTime;
        this.orderInTime = orderInTime;
    }
    public MappedPackage(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(String lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Integer getCollectionDuration() {
        return collectionDuration;
    }

    public void setCollectionDuration(Integer collectionDuration) {
        this.collectionDuration = collectionDuration;
    }

    public Integer getDeliveryDuration() {
        return deliveryDuration;
    }

    public void setDeliveryDuration(Integer deliveryDuration) {
        this.deliveryDuration = deliveryDuration;
    }

    public Integer getEta() {
        return eta;
    }

    public void setEta(Integer eta) {
        this.eta = eta;
    }

    public Integer getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

    public Boolean getOrderInTime() {
        return orderInTime;
    }

    public void setOrderInTime(Boolean orderInTime) {
        this.orderInTime = orderInTime;
    }
}
