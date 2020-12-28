package com.okotheffie.remotemonioringapp.models;

public class User {
    private String firstname;
    private String lastname;
    private String email;
    private String contactnumber;
    private String username;
    private String user_id;
    private String profile_image;
    private String security_level;

    public User() {
    }

    public User(String firstname, String lastname, String email, String contactnumber, String username, String user_id, String profile_image, String security_level) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.contactnumber = contactnumber;
        this.username = username;
        this.user_id = user_id;
        this.profile_image = profile_image;
        this.security_level = security_level;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getSecurity_level() {
        return security_level;
    }

    public void setSecurity_level(String security_level) {
        this.security_level = security_level;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", contactnumber='" + contactnumber + '\'' +
                ", username='" + username + '\'' +
                ", user_id='" + user_id + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", security_level='" + security_level + '\'' +
                '}';
    }
}