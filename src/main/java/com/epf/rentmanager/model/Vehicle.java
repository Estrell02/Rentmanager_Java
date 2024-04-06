package com.epf.rentmanager.model;

public record Vehicle (long id, String constructeur, String modele, int nb_places) {

    public Vehicle () {
        this(0, null, null, 0);
    }

    public Vehicle(long id, String constructeur, String modele, int nb_places) {
        this.id = id;
        this.constructeur = constructeur;
        this.modele = modele;
        this.nb_places = nb_places;
    }
    public Vehicle(long id) {
        this(id, "", "", 0);
    }
    @Override
    public String toString(){
        return "ID du véhicule : " + id + ", Constructeur du véhicule : "
                + constructeur + ", Modèle du véhicule : "
                + modele + " un nombre de place de : " + nb_places;
    }

}