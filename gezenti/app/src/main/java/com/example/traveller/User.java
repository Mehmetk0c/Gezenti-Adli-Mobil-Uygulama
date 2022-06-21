package com.example.traveller;

public class User {
    public String id;
    public String userName;
    public String email;
    public String password;
    public String last;

    public User(String id, String userName, String email, String password,String last) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.last=last;
    }

}
