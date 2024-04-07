package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("clients", clientService.findAll());
            request.setAttribute("vehicles", vehicleService.findAll());
        } catch (ServiceException | DaoException e) {
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            reservationService.create(new Reservation(
                    Long.parseLong(request.getParameter("client")),
                    Long.parseLong(request.getParameter("car")),
                    LocalDate.parse(request.getParameter("begin")),
                    LocalDate.parse(request.getParameter("end"))
            ));
            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (NumberFormatException e) {
            System.out.println("Erreur de formatage des paramètres : " + e.getMessage());
            throw new ServletException("Erreur de formatage des paramètres", e);
        } catch (DateTimeParseException e) {
            System.out.println("Erreur de formatage de date : " + e.getMessage());
            throw new ServletException("Erreur de formatage de date", e);
        } catch (Exception e) {
            System.out.println("Erreur inattendue : " + e.getMessage());
            throw new ServletException("Erreur inattendue", e);
        }
    }


}
