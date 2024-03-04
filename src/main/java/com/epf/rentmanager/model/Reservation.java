package com.epf.rentmanager.model;

import java.sql.Date;
import java.time.LocalDate;

public record Reservation(int id, Long client_id,Long vehicle_id, LocalDate debut, LocalDate fin) {

    public Reservation() {
        this(0, null,null,null,null);
    }


    public Reservation(int id, Long client_id,Long vehicle_id, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
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
