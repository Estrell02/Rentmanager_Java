package com.epf.rentmanager.model;

import java.sql.Date;
import java.time.LocalDate;

public class Reservation {
    private int id;
    private Long client_id;
    private Long vehicle_id;
    private LocalDate debut;
    private LocalDate fin;

    public Reservation(int id, long client_id, Long vehicle_id, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
        this.fin = fin;
    }


    public Reservation(int id, Long clientId, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client_id = client_id;
        this.debut = debut;
        this.fin = fin;
    }

    public int getId() {
        return id;
    }

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public Long getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(Long vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", client_id='" + client_id + '\'' +
                ", vehicle_id='" + vehicle_id + '\'' +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }
}
