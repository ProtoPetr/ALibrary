package com.epam.entity;

import java.io.Serializable;

public class User implements Serializable {
    private long id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String mail;
    private String role = "user";
    private String status = "active";

    public User() {
    }

    public User(String name, String surname, String login, String password, String mail) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surName) {
        this.surname = surName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name +
                ", surName='" + surname +
                ", login='" + login +
                ", password='" + password +
                ", mail='" + mail +
                ", role=" + role +
                ", status='" + status +
                '}';
    }

    public enum ROLE {
        USER, ADMIN, LIBRARIAN, UNKNOWN
    }
}
