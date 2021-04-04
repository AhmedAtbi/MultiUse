package com.example.multipurposeapp.model;

public class Favoris {
    private int id;
    private int id_contact;

    public Favoris() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_contact() {
        return id_contact;
    }

    public void setId_contact(int id_contact) {
        this.id_contact = id_contact;
    }

    public Favoris(int id, int id_contact) {
        this.id = id;
        this.id_contact = id_contact;
    }
}
