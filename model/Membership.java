package com.example.ProjectClub.model;

import java.time.LocalDate;

public class Membership {
    private Long id;             // Primary key for the membership record
    private Long userId;           // Foreign key to link to the User
    private String membershipType; // e.g., "GOLD", "FAMILY", "INDIVIDUAL"
    private int price;
    private LocalDate startDate;    // The date the membership becomes active
    private LocalDate expiryDate;   // The date the membership expires

    public Membership() {
    }

    public Membership(Long id, Long userId, String membershipType, int price, LocalDate startDate, LocalDate expiryDate) {
        this.id = id;
        this.userId = userId;
        this.membershipType = membershipType;
        this.price = price;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
