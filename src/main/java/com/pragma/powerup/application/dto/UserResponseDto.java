package com.pragma.powerup.application.dto;

import java.time.LocalDate;

public class UserResponseDto {
    private Long id;

    private String name;

    private String lastname;

    private Number document;

    private String phone;

    private LocalDate birthdate;

    private String email;

    private String password;

    private String role;

    public UserResponseDto(Long id, String name, String lastname, Number document, String phone, LocalDate birthdate, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.document = document;
        this.phone = phone;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserResponseDto() {
    }
    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Number getDocument() {
        return document;
    }

    public void setDocument(Number document) {
        this.document = document;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
