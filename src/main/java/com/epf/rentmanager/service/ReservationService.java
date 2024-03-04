package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;

import java.util.List;

public class ReservationService {

    public static ReservationService instance;
    private ReservationDao reservationDao;

    private ReservationService() {
        this.reservationDao = reservationDao.getInstance();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public long create(Reservation reservation) throws ServiceException, DaoException {
        if (reservation.getClient_id()==null || reservation.getVehicle_id()==null) {
            throw new ServiceException(" client_id et vehicle_id ne peuvent pas être vides.");
        }

        return reservationDao.create(reservation);
    }

    public List<Reservation> findResaByClientId(long id) throws ServiceException, DaoException {

        return reservationDao.findResaByClientId(id);
    }
    public List<Reservation> findResaByVehicleId(long id) throws ServiceException, DaoException {

        return reservationDao.findResaByVehicleId(id);
    }

    public List<Reservation> findAll() throws ServiceException, DaoException {
        return reservationDao.findAll();
    }


    public void delete(int id) throws ServiceException, DaoException {
        try {
            reservationDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException( e.getMessage());
        }
    }
}
