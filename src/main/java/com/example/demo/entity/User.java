package com.example.demo.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer id;

    private String name;

    private String surName;

    private String email;

    private String password;

     @ManyToMany(fetch = FetchType.LAZY)
     @JoinTable(name = "user_role",joinColumns = @JoinColumn(columnDefinition = "clientId"),inverseJoinColumns = @JoinColumn(columnDefinition = "roleId"))
    private Set<Role> roles = new HashSet<>();


    public User() {
    }

    public User(String name, String surName, String email, String password) {
        this.name = name;
        this.surName = surName;
        this.email = email;
        this.password = password;
    }

    public User(String name, String surName, String email) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
