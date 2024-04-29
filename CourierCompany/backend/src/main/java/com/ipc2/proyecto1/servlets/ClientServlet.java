/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ipc2.proyecto1.servlets;

import com.ipc2.proyecto1.exceptions.HttpException;
import com.ipc2.proyecto1.model.Client;
import com.ipc2.proyecto1.service.ClientService;
import com.ipc2.proyecto1.utils.ConverterJsonToObjectUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author carlos
 */
@WebServlet(name = "ClientServlet", urlPatterns = {"/clients/*"})
public class ClientServlet extends HttpServlet {
    
    private final ClientService clientService;

    public ClientServlet() {
        this.clientService = new ClientService();
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(String text, int httpStatus, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(httpStatus);
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println(text);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            if (request.getPathInfo() != null) {
                String pathParam = request.getPathInfo().replace("/", "");
                List<Client> clients = clientService.getClientById(Integer.parseInt(pathParam));
                if (clients.isEmpty()) {
                    processRequest("No se encontró el cliente", 404, response);
                    return;
                }
                String result = ConverterJsonToObjectUtil.jsonFromClients(clients);
                processRequest(result, 200, response);
            } else {
                List<Client> clients = clientService.getClients();
                String result = ConverterJsonToObjectUtil.jsonFromClients(clients);
                processRequest(result, 200, response);
            }

        } catch (HttpException e) {
            processRequest(e.getMessage(), e.getHttpStatus(), response);
        } catch (Exception e) {
            processRequest(e.getMessage(), 500, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String txtResponse = "OK Clients";
        String clientBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("client body:" + clientBody);
        
        //Convert body into an object
        Client client = ConverterJsonToObjectUtil.getClient(clientBody);
        
        //Send it to service
        try {
            clientService.addClient(client);
            processRequest(txtResponse, 200, response);
        } catch (HttpException e) {
            processRequest(e.getMessage(), e.getHttpStatus(), response);
        } catch (Exception e) {
            processRequest(e.getMessage(), 500, response);
        }
        
        
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String txtResponse = "Client updated";
        String clientBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("Client body:" + clientBody);

        //Convert body into an object
        Client client = ConverterJsonToObjectUtil.getClient(clientBody);
        System.out.println(client.getId());

        try {

            if (request.getPathInfo() != null) {
                String pathParam = request.getPathInfo().replace("/", "");
                client.setId(Integer.parseInt(pathParam));
                //List<ControlPoint> controlPoints = controlPointService.getControlPointById(Integer.parseInt(pathParam));
                clientService.updateClient(client);
                processRequest(txtResponse, 200, response);
            } else {

                String result = "Es necesario el id del cliente para actualizarlo";
                processRequest(result, 400, response);
            }

        } catch (HttpException e) {
            processRequest(e.getMessage(), e.getHttpStatus(), response);
        } catch (Exception e) {
            processRequest(e.getMessage(), 500, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
