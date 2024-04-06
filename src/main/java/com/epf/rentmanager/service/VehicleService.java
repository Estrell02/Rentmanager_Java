package com.epf.rentmanager.service;

import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;

	@Autowired
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}


	public long create(Vehicle vehicle) throws ServiceException, DaoException {

		return vehicleDao.create(vehicle);

	}

	public Vehicle findById(long id) throws ServiceException {
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la récupération du véhicule");
		}
	}

	public List<Vehicle> findAll() throws ServiceException, DaoException {
		return vehicleDao.findAll();

	}
	public long delete(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public int count() throws ServiceException, DaoException {
		return vehicleDao.count();
	}
	private void checkVehicle(Vehicle vehicle) throws ServiceException {
		if (vehicle.constructeur().isEmpty())
			throw new ServiceException("Constructeur ne doit pas être vide");
		if (vehicle.modele().isEmpty())
			throw new ServiceException("Modéle ne doit pas être vide");
		if (vehicle.nb_places() < 2 || vehicle.nb_places() > 9)
			throw new ServiceException("Nombre de places doit être compris entre 2 et 9");
	}
	public void update(Vehicle vehicle) throws ServiceException {
		checkVehicle(vehicle);
		try {
			vehicleDao.update(vehicle);
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

}
