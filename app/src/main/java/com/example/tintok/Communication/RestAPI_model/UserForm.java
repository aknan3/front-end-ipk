package com.example.tintok.Communication.RestAPI_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UserForm {

    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("id")
    private String id;
    @SerializedName("profile_path")
    private String url;
    @SerializedName("gender")
    private String gender;
    @SerializedName("location")
    private String location;
    @SerializedName("description")
    private String description;

    public List<PostForm> getPosts() {
        return posts;
    }

    @SerializedName("posts")
    private List<PostForm> posts;


    public UserForm(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public UserForm(String username, String gender, String location, String description) {
        this.username = username;
        this.gender = gender;
        this.location = location;
        this.description = description;
    }

    public String getId(){
        return this.id;
    }
    public String getUsername(){
        return this.username;
    }
    public String getEmail() {
        return email;
    }
    public String getImageUrl(){ return url; }
    public String getGender() {
        return gender;
    }
    public String getLocation() {
        return location;
    }
    public String getDescription() {
        return description;
    }
}
