package com.epf.rentmanager.ui.cli;


import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class RequestCLI {

    private static final ClientService clientService = ClientService.getInstance();
    private static final VehicleService vehicleService = VehicleService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            IOUtils.print("Menu:");
            IOUtils.print("1. Créer un client");
            IOUtils.print("2. Lister tous les clients");
            IOUtils.print("3. Créer un véhicule");
            IOUtils.print("4. Lister tous les véhicules");
            IOUtils.print("5. Supprimer un client");
            IOUtils.print("6. Supprimer un véhicule");
            IOUtils.print("7. Créer une réservation");
            IOUtils.print("8. Lister toutes les réservations");
            IOUtils.print("9. Lister toutes les réservations d'un client");
            IOUtils.print("10. Lister toutes les réservations d'un véhicule");
            IOUtils.print("11. Supprimer une réservation");
            IOUtils.print("7. Quitter");
            int choice = IOUtils.readInt("Veuillez choisir une option: ");

            switch (choice) {
                case 1:
                    createClient();
                    break;
                case 2:
                    listAllClients();
                    break;
                case 3:
                    createVehicle();
                    break;
                case 4:
                    listAllVehicles();
                    break;
                case 5:
                    deleteClient();
                    break;
                case 6:
                    deleteVehicle();
                    break;
                case 7:
                    createReservation();
                    break;
                case 8:
                    listAllReservations();
                    break;
                case 9:
                    listReservationsByClient();
                    break;
                case 10:
                    listReservationsByVehicle();
                    break;
                case 11:
                    deleteReservation();
                    break;
                case 12:
                    exit = true;
                    break;
                default:
                    IOUtils.print("Option invalide. Veuillez réessayer.");
                    break;
            }
        }


    }

    private static void createClient() {
        String nom = IOUtils.readString("Nom: ", true);
        String prenom = IOUtils.readString("Prénom: ", true);
        String email = IOUtils.readString("Email: ", true);
        LocalDate dateNaissance = IOUtils.readDate("Date de naissance (dd/MM/yyyy): ", true);

        try {
            clientService.create(new Client(0, nom, prenom, email, dateNaissance));
            IOUtils.print("Client créé avec succès.");
        } catch (ServiceException | DaoException e) {
            IOUtils.print("Erreur lors de la création du client: " + e);
        }
    }

    private static void listAllClients() {
        try {
            List<Client> clients = clientService.findAll();
            IOUtils.print("Liste des clients:");
            for (Client client : clients) {
                IOUtils.print(client.toString());
            }
        } catch (ServiceException | DaoException e) {
            IOUtils.print("Erreur lors de la récupération des clients: " + e.getMessage());
        }
    }

    private static void createVehicle() {
        String constructeur = IOUtils.readString("Constructeur: ", true);
        int nb_places = IOUtils.readInt("Nombre de places: ");
        String modele=IOUtils.readString("Modele:", true);

        try {
            vehicleService.create(new Vehicle(0, constructeur,modele, nb_places));
            IOUtils.print("Véhicule créé avec succès.");
        } catch (ServiceException | DaoException e) {
            IOUtils.print("Erreur lors de la création du véhicule: " + e);
        }
    }

    private static void listAllVehicles() {
        try {
            List<Vehicle> vehicles = vehicleService.findAll();
            IOUtils.print("Liste des véhicules:");
            for (Vehicle vehicle : vehicles) {
                IOUtils.print(vehicle.toString());
            }
        } catch (ServiceException | DaoException e) {
            IOUtils.print("Erreur lors de la récupération des véhicules: " + e);
        }
    }

    private static void deleteClient() {
        long id = IOUtils.readInt("ID du client à supprimer: ");
        try {
            clientService.delete(id);
            IOUtils.print("Client supprimé avec succès.");
        } catch (ServiceException | DaoException e) {
            IOUtils.print("Erreur lors de la suppression du client: " + e);
        }
    }

    private static void deleteVehicle() {
        int id = IOUtils.readInt("ID du vehicule à supprimer: ");

        try {
            vehicleService.delete(id);
            IOUtils.print("Vehicule supprimé avec succès.");
        } catch (ServiceException | DaoException e) {
            IOUtils.print("Erreur lors de la suppression du véhicule: " + e.getMessage());
        }


    }
    private static void createReservation() {
        long clientId = IOUtils.readInt("ID du client: ");
        long vehicleId = IOUtils.readInt("ID du véhicule: ");
        LocalDate debut = IOUtils.readDate("Date de début (dd/MM/yyyy): ", true);
        LocalDate fin = IOUtils.readDate("Date de fin (dd/MM/yyyy): ", true);

        try {
            reservationService.create(new Reservation(0, clientId, vehicleId, debut, fin));
            IOUtils.print("Réservation créée avec succès.");
        } catch (Exception e) {
            IOUtils.print("Erreur lors de la création de la réservation: " + e.getMessage());
        }
    }

    private static void listAllReservations() {
        try {
            List<Reservation> reservations = reservationService.findAll();
            IOUtils.print("Liste des réservations:");
            for (Reservation reservation : reservations) {
                IOUtils.print(reservation.toString());
            }
        } catch (Exception e) {
            IOUtils.print("Erreur lors de la récupération des réservations: " + e.getMessage());
        }
    }

    private static void listReservationsByClient() {
        long clientId = IOUtils.readInt("ID du client: ");
        try {
            List<Reservation> reservations = reservationService.findResaByClientId(clientId);
            IOUtils.print("Réservations pour le client " + clientId + ":");
            for (Reservation reservation : reservations) {
                IOUtils.print(reservation.toString());
            }
        } catch (Exception e) {
            IOUtils.print("Erreur lors de la récupération des réservations: " + e.getMessage());
        }
    }

    private static void listReservationsByVehicle() {
        int vehicleId = IOUtils.readInt("ID du véhicule: ");
        try {
            List<Reservation> reservations = reservationService.findResaByVehicleId(vehicleId);
            IOUtils.print("Réservations pour le véhicule " + vehicleId + ":");
            for (Reservation reservation : reservations) {
                IOUtils.print(reservation.toString());
            }
        } catch (Exception e) {
            IOUtils.print("Erreur lors de la récupération des réservations: " + e.getMessage());
        }
    }

    private static void deleteReservation() {
        int reservationId = IOUtils.readInt("ID de la réservation à supprimer: ");
        try {
            reservationService.delete(reservationId);
            IOUtils.print("Réservation supprimée avec succès.");
        } catch (Exception e) {
            IOUtils.print("Erreur lors de la suppression de la réservation: " + e.getMessage());
        }
    }
}