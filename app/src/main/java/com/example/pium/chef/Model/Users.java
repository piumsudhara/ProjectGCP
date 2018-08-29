package com.example.pium.chef.Model;

import com.google.gson.annotations.SerializedName;

public class Users
{
    @SerializedName("username")
    public String username;

    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;

    @SerializedName("role")
    public String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
