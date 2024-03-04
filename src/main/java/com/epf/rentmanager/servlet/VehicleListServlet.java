package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.model.Vehicle;

@WebServlet("/cars")
public class VehicleListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private VehicleService vehicleService;

    public VehicleListServlet() {
        this.vehicleService = VehicleService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Vehicle> vehicles = vehicleService.findAll();
            request.setAttribute("vehicles", vehicles);
            int vehicleCount = vehicleService.count();
            request.setAttribute("vehicleCount", vehicleCount);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération des véhicules", e);
        }
    }
}
