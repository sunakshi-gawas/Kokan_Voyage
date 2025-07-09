package com.konkanvoyage.konkanvoyage.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String phone;
    private String destination;
    private String arrivalDate;
    private String leavingDate;
    private String status;
    private String price;
    

    @Column(nullable = false)
    private String username;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    // Getters
    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDestination() {
        return destination;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getLeavingDate() {
        return leavingDate;
    }

    public String getStatus() {
        return status;
    }
    
    public String getUsername() {
        return username;
    }

    public String getPrice() {
        return price;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setLeavingDate(String leavingDate) {
        this.leavingDate = leavingDate;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
