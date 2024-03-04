package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;


@WebServlet("/users")
public class ClientListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ClientService clientService;

    public ClientListServlet() {
        this.clientService = ClientService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Client> clients = clientService.findAll();
            request.setAttribute("client", clients);
            int clientCount = clientService.count();
            request.setAttribute("vehicleCount", clientCount);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération des clients", e);
        }
    }
}
