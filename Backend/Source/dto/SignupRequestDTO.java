package com.example.ServiceBookingSystem.dto;

import lombok.Data;

@Data
public class SignupRequestDTO {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String lastname;
    private String phone;
    private String address;

    // Constructors, getters, and setters

    public SignupRequestDTO() {
        // Default constructor
    }

    public SignupRequestDTO(String email, String password, String name, String lastname, String phone, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.address = address;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}