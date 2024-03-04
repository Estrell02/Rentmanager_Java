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
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;


public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	
	public long create(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1,client.nom() );
			ps.setString(2, client.prenom());
			ps.setString(3, client.email());
			ps.setDate(4, Date.valueOf(client.naissance()));
			ps.executeUpdate();

			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				} else {
					throw new DaoException("Creating client failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
	}
	
	public long delete(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(DELETE_CLIENT_QUERY)) {
			ps.setLong(1, id);
			int affectedRows = ps.executeUpdate();
			return affectedRows;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	public Client findById(long id) throws DaoException {
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps=connection.prepareStatement(FIND_CLIENT_QUERY)){

			ps.setInt(1, (int) id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return new Client(
						rs.getInt("id"),
						rs.getString("nom"),
						rs.getString("prenom"),
						rs.getString("email"),
						rs.getDate("naissance").toLocalDate()
				);}
		}catch (SQLException e){
			throw new DaoException();
		}
		return null;
	}

	public List<Client> findAll() throws DaoException {
		List <Client> clients=new ArrayList<>();
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement ps=connection.prepareStatement(FIND_CLIENTS_QUERY)){

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				clients.add(new Client(
						rs.getInt("id"),
						rs.getString("nom"),
						rs.getString("prenom"),
						rs.getString("email"),
						rs.getDate("naissance").toLocalDate()
				));
			}
		}catch (SQLException e){
			throw new DaoException();
		}
		return  clients;

	}
	public int count() throws DaoException {
		String sql = "SELECT COUNT(*) FROM Client";
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

}
