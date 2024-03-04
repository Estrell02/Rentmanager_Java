package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;

public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}
	
	
	public long create(Vehicle vehicle) throws ServiceException, DaoException {



		return vehicleDao.create(vehicle);
		
	}

	public Vehicle findById(long id) throws ServiceException, DaoException {
		return vehicleDao.findById(id);
		
	}

	public List<Vehicle> findAll() throws ServiceException, DaoException {
		return vehicleDao.findAll();
		
	}
	public void delete(int id) throws ServiceException, DaoException {

		try {

			vehicleDao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public int count() throws ServiceException, DaoException {
		return vehicleDao.count();
	}
	
}
