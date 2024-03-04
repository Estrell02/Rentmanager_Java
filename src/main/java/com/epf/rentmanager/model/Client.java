package com.epf.rentmanager.model;

import java.sql.Date;
import java.time.LocalDate;

public record Client (int id, String nom, String prenom, String email, LocalDate naissance){

    public Client(){this(0,null, null,null, null );}

    public Client(int id, String nom, String prenom, String email, LocalDate naissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", Nom='" + nom + '\'' +
                ", Prenom='" + prenom + '\'' +
                ", Email='" + email + '\'' +
                ", Naissance=" + naissance +
                '}';
    }


}
