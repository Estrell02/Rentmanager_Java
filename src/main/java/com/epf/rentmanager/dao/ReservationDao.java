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

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
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
	
	public long delete(int id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY)) {
			ps.setLong(1, id);
			int affectedRows = ps.executeUpdate();
			return affectedRows;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY)) {

			ps.setLong(1, clientId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				reservations.add(new Reservation(

						rs.getInt("id"),
						rs.getLong("vehicle_id"),
						clientId,
						rs.getDate("debut").toLocalDate(),
						rs.getDate("fin").toLocalDate()
				));
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage() );
		}
		return reservations;
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY)) {

			ps.setLong(1, vehicleId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				reservations.add(new Reservation(
						rs.getInt("id"),
						vehicleId,
						rs.getLong("client_id"),
						rs.getDate("debut").toLocalDate(),
						rs.getDate("fin").toLocalDate()
				));
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage() );
		}
		return reservations;
	}

	public List<Reservation> findAll() throws DaoException {
		List <Reservation> reservations=new ArrayList<>();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps=connection.prepareStatement(FIND_RESERVATIONS_QUERY)){

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				reservations.add(new Reservation(
						rs.getInt("id"),
						rs.getLong("client_id"),
						rs.getLong("vehicle_id"),
						rs.getDate("debut").toLocalDate(),
						rs.getDate("fin").toLocalDate()
				));
			}
		}catch (SQLException e){
			throw new DaoException();
		}
		return  reservations;
	}
}
