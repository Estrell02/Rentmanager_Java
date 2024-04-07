package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private ClientDao clientDao;

    @Autowired
    private ClientService(ClientDao clientDao){
        this.clientDao = clientDao;
    }

    public long create(Client client) throws ServiceException, DaoException {
        if (client.nom().isEmpty() || client.prenom().isEmpty()) {
            throw new ServiceException("Le nom et le prénom du client ne peuvent pas être vides.");
        }

        return clientDao.create(client);
    }

    public Client findById(long id) throws ServiceException, DaoException {

        return clientDao.findById(id);
    }

    public List<Client> findAll() throws ServiceException, DaoException {
        return clientDao.findAll();
    }


    public long delete(Client client) throws ServiceException {
        try {
            return clientDao.delete(client);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }
    public int count() throws ServiceException, DaoException {
        return clientDao.count();
    }
    public void update(Client client) throws ServiceException {

        try {
            clientDao.update(client);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }
}