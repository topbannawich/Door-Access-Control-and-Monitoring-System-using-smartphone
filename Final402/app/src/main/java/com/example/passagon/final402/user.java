package com.example.passagon.final402;

public class user{
   private String Name;
    private  String Surname;
    private  String Password;
    private  String Passcode;
    private  String Username;
   private  String Type;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPasscode() {
        return Passcode;
    }

    public void setPasscode(String passcode) {
        Passcode = passcode;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public user(){

}

    public user(String name, String surname, String password, String passcode,  String type) {
        Name = name;
       Surname = surname;
       Password = password;
       Passcode = passcode;

        Type = type;
    }
}
