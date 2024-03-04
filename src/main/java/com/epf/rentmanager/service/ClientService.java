package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.utils.IOUtils;

public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;

	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}

	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		return instance;
	}

	public long create(Client client) throws ServiceException, DaoException {
		if (client.nom().isEmpty() || client.prenom().isEmpty()) {
			throw new ServiceException("Le nom et le prénom du client ne peuvent pas être vides.");
		}
//		client.nom(client.nom().toUpperCase());
		return clientDao.create(client);
	}

	public Client findById(long id) throws ServiceException, DaoException {

		return clientDao.findById(id);
	}

	public List<Client> findAll() throws ServiceException, DaoException {
		return clientDao.findAll();
	}


	public void delete(long id) throws ServiceException, DaoException {
		try {
			clientDao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException( e.getMessage());
		}
	}
	public int count() throws ServiceException, DaoException {
		return clientDao.count();
	}
}