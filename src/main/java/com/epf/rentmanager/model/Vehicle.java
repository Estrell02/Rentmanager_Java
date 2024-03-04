package com.epf.rentmanager.model;

import java.sql.Date;

public record Vehicle (int id, String constructeur, String modele, int nb_places){


    public Vehicle(){
        this(0, null, null, 0);
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

    public Vehicle(int id, String constructeur,String modele, int nb_places){
        this.id=id;
        this.constructeur=constructeur;
        this.nb_places=nb_places;
        this.modele=modele;
    }

}
