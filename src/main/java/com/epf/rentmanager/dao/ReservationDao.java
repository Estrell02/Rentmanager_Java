package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;

import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(id) FROM Reservation;";
	private static final String COUNT_RESERVATIONS_BY_CLIENT_QUERY = "SELECT COUNT(id) FROM Reservation WHERE client_id=?;";
	private static final String COUNT_VEHICLES_BY_CLIENT_QUERY = "SELECT COUNT(DISTINCT(vehicle_id)) FROM Reservation WHERE client_id=?;";
	private static final String FIND_BY_RESERVATION_ID_QUERY =
			"""
            SELECT Reservation.id, Vehicle.id AS vehicle_id, Vehicle.constructeur, Vehicle.modele, Vehicle.nb_places, Client.id as client_id, Client.nom, Client.prenom, Client.email, Client.naissance, debut, fin
            FROM Reservation
            INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id
            INNER JOIN Client ON Reservation.client_id = Client.id
            WHERE Reservation.id=?;
            """;

	private static final String UPDATE_RESERVATION_QUERY =
			"""
            UPDATE Reservation 
            SET client_id=?, vehicle_id=?, debut=?, fin=? 
            WHERE id=?;
            """;

	public long create(Reservation reservation) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1,reservation.client_id() );
            ps.setLong(2, reservation.vehicle_id());
            ps.setDate(3, Date.valueOf(reservation.debut()));
            ps.setDate(4, Date.valueOf(reservation.fin()));
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new DaoException("Creating reservation failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException();
        }
	}
	
//	public long delete(int id) throws DaoException {
//		try (Connection connection = ConnectionManager.getConnection();
//			 PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY)) {
//			ps.setLong(1, id);
//			int affectedRows = ps.executeUpdate();
//			return affectedRows;
//		} catch (SQLException e) {
//			throw new DaoException();
//		}
//	}


	public long delete(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, reservation.id());
			prepareStatement.execute();
			prepareStatement.close();
		} catch (SQLException e) {
			throw new DaoException( e.getMessage());
		}
		return 0;
	}


	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, clientId);
			ResultSet resultSet = prepareStatement.executeQuery();
			List<Reservation> reservations = new ArrayList<Reservation>();
			while(resultSet.next()) {
				Reservation reservation = new Reservation(resultSet.getLong("id"),
						clientId, resultSet.getLong("vehicle_id"),
						resultSet.getDate("debut").toLocalDate(), resultSet.getDate("fin").toLocalDate());
				reservations.add(reservation);
			}
			prepareStatement.close();
			return reservations;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération des réservations: " + e.getMessage());
		}
	}

	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, vehicleId);
			ResultSet resultSet = prepareStatement.executeQuery();
			List<Reservation> reservations = new ArrayList<Reservation>();
			while(resultSet.next()) {
				Reservation reservation = new Reservation(resultSet.getLong("id"),
						resultSet.getLong("client_id"), vehicleId,
						resultSet.getDate("debut").toLocalDate(), resultSet.getDate("fin").toLocalDate());
				reservations.add(reservation);
			}
			prepareStatement.close();
			return reservations;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération des réservations: " + e.getMessage());
		}
	}

	public List<Reservation> findAll() throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(FIND_RESERVATIONS_QUERY, Statement.RETURN_GENERATED_KEYS);
			ResultSet resultSet = prepareStatement.executeQuery();
			List<Reservation> reservations = new ArrayList<Reservation>();
			while(resultSet.next()) {
				Reservation reservation = new Reservation(resultSet.getLong("id"),
						resultSet.getLong("client_id"), resultSet.getLong("vehicle_id"),
						resultSet.getDate("debut").toLocalDate(), resultSet.getDate("fin").toLocalDate());
				reservations.add(reservation);
			}
			prepareStatement.close();
			return reservations;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération des réservations: " + e.getMessage());
		}
	}
	public int count() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(COUNT_RESERVATIONS_QUERY)) {
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DaoException();
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public int countByClientId(long clientId) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(COUNT_RESERVATIONS_BY_CLIENT_QUERY)) {
			ps.setLong(1, clientId);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DaoException();
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public int countVehiclesByClientId(long clientId) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(COUNT_VEHICLES_BY_CLIENT_QUERY)) {
			ps.setLong(1, clientId);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DaoException();
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public Reservation findById(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM Reservation WHERE id=?;");
			prepareStatement.setLong(1, id);
			ResultSet resultSet = prepareStatement.executeQuery();
			long clientId = 0;
			long vehicleId = 0;
			LocalDate debut = null;
			LocalDate fin = null;
			while(resultSet.next()) {
				clientId = resultSet.getLong("client_id");
				vehicleId = resultSet.getLong("vehicle_id");
				debut = resultSet.getDate("debut").toLocalDate();
				fin = resultSet.getDate("fin").toLocalDate();
			}
			prepareStatement.close();
			return new Reservation(id, clientId, vehicleId, debut, fin);
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération de la réservation: " + e.getMessage());
		}
	}

	public void update(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(UPDATE_RESERVATION_QUERY)) {
			ps.setLong(1, reservation.client_id());
			ps.setLong(2, reservation.vehicle_id());
			ps.setDate(3, Date.valueOf(reservation.debut()));
			ps.setDate(4, Date.valueOf(reservation.fin()));
			ps.setLong(5, reservation.id());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException();
		}
	}
}

