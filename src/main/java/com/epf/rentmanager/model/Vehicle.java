package com.epf.rentmanager.model;

import java.sql.Date;

public class Vehicle {
    private int id;
    private String constructeur;
    private String modele;
    private int nb_places;

    public Vehicle(){
        this.id=id;
        this.constructeur=constructeur;
        this.nb_places=nb_places;
        this.modele=modele;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", contructeur='" + constructeur + '\'' +
                ", modele='" + modele + '\'' +
                ", nb_places='" + nb_places + '\'' +
                '}';
    }

//    public Vehicle(int id, String constructeur, int nb_places){
//        this.id=id;
//        this.constructeur=constructeur;
//        this.nb_places=nb_places;
//        this.modele=modele;
//    }
    public Vehicle(int id, String constructeur,String modele, int nb_places){
        this.id=id;
        this.constructeur=constructeur;
        this.nb_places=nb_places;
        this.modele=modele;
    }


    public int getId() {
        return id;
    }


    public String getConstructeur() {
        return constructeur;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public int getNb_places() {
        return nb_places;
    }

    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }
}
