package com.example.multipurposeapp.model;


public class Contact {

    private int id,favoris;
    private String nom,prenom,numero,sex;

    public int getFavoris() {
        return favoris;
    }

    public void setFavoris(int favoris) {
        this.favoris = favoris;
    }

    public Contact(String nom, String prenom, String numero, String sex, int favoris) {
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.favoris = favoris;
        this.sex = sex;
    }

    public Contact(int id, String nom, String prenom, String numero, String sex) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.sex = sex;
    }

    public Contact() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Contact( String nom, String prenom, String numero, String sex) {
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.sex = sex;
    }

    public Contact(int id, String nom, String prenom, String numero, String sex, int favoris) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.sex = sex;
        this.favoris=favoris;
    }
}
