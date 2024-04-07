package com.epf.rentmanager.servlet;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users/edit")
public class ClientEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    ClientService clientService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            request.setAttribute("client", clientService.findById(Long.parseLong(request.getParameter("id"))));
        } catch (ServiceException | DaoException e) {
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/edit.jsp").forward(request, response);
    }
//        List<Reservation> reservations = new ArrayList<>();
//        List<Vehicle> vehicles = new ArrayList<>();
//        Client client = null;
//
//        try {
//            reservations = reservationService.findResaByClientId(Long.parseLong(request.getParameter("id")));
//            client = clientService.findById(Long.parseLong(request.getParameter("id")));
//            for (Reservation resa : reservations) {
//                Vehicle vehicle = vehicleService.findById(resa.vehicle_id());
//                if (vehicle != null && !vehicles.contains(vehicle)) {
//                    vehicles.add(vehicle);
//                }
//            }
//        } catch (ServiceException | DaoException e) {
//            throw new RuntimeException(e);
//        }
//
//        request.setAttribute("client", client);
//        request.setAttribute("vehicles", vehicles);
//        request.setAttribute("reservations", reservations);
//
//        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
//    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            String prenom = request.getParameter("first_name");
            String nom = request.getParameter("last_name");
            String email = request.getParameter("email");
            LocalDate birth = java.time.LocalDate.parse(request.getParameter("birth"));

            if (prenom.isEmpty() || nom.isEmpty())
                throw new ServletException("Nom et prénom ne doivent pas être vide");
            if (prenom.length() < 3 || nom.length() < 3)
                throw new ServletException("Nom et prénom doivent faire au moins 3 caractères");
            if (birth == null || birth.isAfter(java.time.LocalDate.now().minusYears(18)))
                throw new ServletException("Date de naissance invalide");
            String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
            if (!email.matches(regex))
                throw new ServletException("Email invalide");

            clientService.update(new Client(id,prenom, nom, email, birth));
        } catch (ServiceException e) {
            throw new ServletException();
        }
        response.sendRedirect(request.getContextPath() + "/users");
    }

}
