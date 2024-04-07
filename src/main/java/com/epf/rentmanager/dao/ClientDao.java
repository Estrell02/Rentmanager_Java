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
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
    private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(id) FROM Client;";


	public long create(Client client) throws DaoException, ServiceException {
		if(client.nom() == null || client.prenom() == null) {
			throw new ServiceException("Le nom et le prénom du client sont obligatoires");
		}
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setString(1, client.nom().toUpperCase());
			prepareStatement.setString(2, client.prenom());
			prepareStatement.setString(3, client.email());
			prepareStatement.setDate(4, Date.valueOf(client.naissance()));
			prepareStatement.execute();
			prepareStatement.close();
			return 0;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du client: " + e.getMessage());
		}
	}


	public long delete(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(DELETE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, client.id());
			prepareStatement.execute();
			prepareStatement.close();
		}catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du client: " + e.getMessage());
		}
		return 0;
	}
	public void update(Client client) throws DaoException {
		 String sql = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?;";

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, client.nom());
			ps.setString(2, client.prenom());
			ps.setString(3, client.email());
			ps.setDate(4, Date.valueOf(client.naissance()));
			ps.setLong(5, client.id());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException();
		}
	}


	public Client findById(long id) throws DaoException {

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(FIND_CLIENT_QUERY)) {
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Client(
						rs.getLong("id"),
						rs.getString("nom"),
						rs.getString("prenom"),
						rs.getString("email"),
						rs.getDate("naissance").toLocalDate());
			} else {
				throw new DaoException();
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}

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
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(COUNT_CLIENTS_QUERY)) {
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

}
