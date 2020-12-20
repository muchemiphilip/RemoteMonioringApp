package com.okotheffie.remotemonioringapp.Models;

import java.util.List;

public class User {
//    private String firstname;
//    private String lastname;
//    private String email;
//    private String contactnumber;
//    private String username;
//    private String user_id;
//
//    public User() {
//    }
//
//    public User(String firstname, String lastname, String email, String contactnumber, String username, String user_id) {
//        this.firstname = firstname;
//        this.lastname = lastname;
//        this.email = email;
//        this.contactnumber = contactnumber;
//        this.username = username;
//        this.user_id = user_id;
//    }
//    public String getFirstname() {
//        return firstname;
//    }
//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }
//    public String getLastname() {
//        return lastname;
//    }
//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }
//    public String getEmail() {
//        return email;
//    }
//    public void setEmail(String email) {
//        this.email = email;
//    }
//    public String getContactnumber() {
//        return contactnumber;
//    }
//    public void setContactnumber(String contactnumber) {
//        this.contactnumber = contactnumber;
//    }
//    public String getUsername() {
//        return username;
//    }
//    public void setUsername(String username) {
//        this.username = username;
//    }
//    public String getUser_id() {
//        return user_id;
//    }
//    public void setUser_id(String user_id) {
//        this.user_id = user_id;
//    }
//    @Override
//    public String toString() {
//        return "User{" +
//                "firstname='" + firstname + '\'' +
//                ", lastname='" + lastname + '\'' +
//                ", email='" + email + '\'' +
//                ", contactnumber='" + contactnumber + '\'' +
//                ", username='" + username + '\'' +
//                ", user_id='" + user_id + '\'' +
//                '}';
//    }


    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String userName;
    private int level;
    private boolean loggedIn;
    private String password;
    private List tasks;
    private String uid;

    public User(String firstName, String lastName, String email, String contactNumber, String userName, int level, boolean loggedIn, String password, List tasks, String uid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.userName = userName;
        this.level = level;
        this.loggedIn = loggedIn;
        this.password = password;
        this.tasks = tasks;
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List getTasks() {
        return tasks;
    }

    public void setTasks(List tasks) {
        this.tasks = tasks;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}