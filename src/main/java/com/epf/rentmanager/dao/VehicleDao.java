package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {

	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur,modele, nb_places) VALUES(?,?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur,modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur,modele, nb_places FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1,vehicle.constructeur() );
			ps.setString(2,vehicle.modele() );
			ps.setInt(3, vehicle.nb_places());
			ps.execute();

			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				} else {
					throw new DaoException("Creating vehicle failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(DELETE_VEHICLE_QUERY)) {
			ps.setLong(1, vehicle.id());
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public Vehicle findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_QUERY)) {
			ps.setLong(1, id);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				return new Vehicle(
						resultSet.getLong("id"),
						resultSet.getString("constructeur"),
						resultSet.getString("modele"),
						resultSet.getInt("nb_places"));
			} else {
				throw new DaoException();
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		List <Vehicle> vehicles=new ArrayList<>();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps=connection.prepareStatement(FIND_VEHICLES_QUERY)){

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				vehicles.add(new Vehicle(
						rs.getInt("id"),
						rs.getString("constructeur"),
						rs.getString("modele"),
						rs.getInt("nb_places")
				));
			}
		}catch (SQLException e){
			throw new DaoException(e.getMessage());
		}
		return  vehicles;
		
	}
	public int count() throws DaoException {
		String sql = "SELECT COUNT(*) FROM Vehicle";
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return 0;
	}

	public void update(Vehicle vehicle) throws DaoException {
		String sql= "UPDATE Vehicle SET constructeur=?, modele=?, nb_places=? WHERE id=?;";
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, vehicle.constructeur());
			ps.setString(2, vehicle.modele());
			ps.setInt(3, vehicle.nb_places());
			ps.setLong(4, vehicle.id());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException();
		}
	}
	

}
