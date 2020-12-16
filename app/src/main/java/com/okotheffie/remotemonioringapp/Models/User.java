package com.okotheffie.remotemonioringapp.Models;

public class User {
    private String firstname;
    private String lastname;
    private String email;
    private String contactnumber;
    private String username;
    private String user_id;

    public User() {
    }

    public User(String firstname, String lastname, String email, String contactnumber, String username, String user_id) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.contactnumber = contactnumber;
        this.username = username;
        this.user_id = user_id;
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

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", contactnumber='" + contactnumber + '\'' +
                ", username='" + username + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}