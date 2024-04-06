package com.epf.rentmanager.model;

import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import java.time.LocalDate;

public record Reservation (long id, long client_id, long vehicle_id, LocalDate debut, LocalDate fin) {
    public Reservation(long client_id, long vehicle_id, LocalDate debut, LocalDate fin) {
        this(0, client_id, vehicle_id, debut, fin);
    }

    public Reservation(long id) {
        this(id, 0, 0, LocalDate.now(), LocalDate.now());
    }

    @Override
    public String toString() {
        return "Informations sur la réservation :" +
                ", ID : " + id + "\n" +
                "ID du client : " + client_id +
                ", ID du véhicule : " + vehicle_id + "\n" +
                "Date de début : " + debut +
                ", Date de fin : " + fin;
    }

}