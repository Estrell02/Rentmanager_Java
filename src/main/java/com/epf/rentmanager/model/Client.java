package com.epf.rentmanager.model;

import java.sql.Date;
import java.time.LocalDate;

public record Client (long id, String nom, String prenom, String email, LocalDate naissance){

    public Client(String nom, String prenom, String email, LocalDate naissance) {
        this(0, nom, prenom, email, naissance);
    }

    public Client(long id) {
        this(id, "", "", "", LocalDate.now());
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
