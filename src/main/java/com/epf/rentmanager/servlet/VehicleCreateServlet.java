package com.epf.rentmanager.servlet;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.model.Vehicle;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private VehicleService vehicleService;

    public VehicleCreateServlet() {
        this.vehicleService = VehicleService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String constructeur = request.getParameter("manufacturer");
        int nombrePlaces = Integer.parseInt(request.getParameter("seats"));
        String modele = request.getParameter("modele");

        Vehicle vehicle = new Vehicle(0,constructeur, modele, nombrePlaces);
        try {
            vehicleService.create(vehicle);
            response.sendRedirect("/rentmanager/cars");
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la création du véhicule", e);
        }
    }
}

