package com.epf.rentmanager.servlet;


import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rents/delete/*")
public class ReservationDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String reservationIDstr = request.getPathInfo().substring(1);
        String id =request.getParameter("id");
        int reservationID = Integer.parseInt(id);

        System.out.println(reservationID);
        try {
            reservationService.delete(reservationService.findById(reservationID));
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        response.sendRedirect(request.getContextPath() + "/rents");
    }


}
