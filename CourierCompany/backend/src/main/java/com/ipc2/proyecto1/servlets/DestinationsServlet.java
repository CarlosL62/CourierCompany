/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ipc2.proyecto1.servlets;

import com.ipc2.proyecto1.exceptions.HttpException;
import com.ipc2.proyecto1.model.ControlPoint;
import com.ipc2.proyecto1.model.Destination;
import com.ipc2.proyecto1.service.ControlPointService;
import com.ipc2.proyecto1.service.DestinationService;
import com.ipc2.proyecto1.utils.ConverterJsonToObjectUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author carlos
 */
@WebServlet(name = "DestinationsServlet", urlPatterns = {"/destinations/*"})
public class DestinationsServlet extends HttpServlet {

    private DestinationService destinationService;

    public DestinationsServlet() {
        this.destinationService = new DestinationService();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(String text, int httpStatus, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
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
                List<Destination> destinations = destinationService.getDestinationById(Integer.parseInt(pathParam));
                String result = ConverterJsonToObjectUtil.jsonFromDestinations(destinations);
                processRequest(result, 200, response);
            } else {
                List<Destination> destinations = destinationService.getDestinations();
                String result = ConverterJsonToObjectUtil.jsonFromDestinations(destinations);
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

        String txtResponse = "OK Destinations";
        String destinationBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("Destination body:" + destinationBody);

        //Convert body into an object
        Destination destination = ConverterJsonToObjectUtil.getDestination(destinationBody);
        System.out.println(destination.getId());

        //Send it to service
        try {
            destinationService.addDestination(destination);
            processRequest(txtResponse, 200, response);
        } catch (HttpException e) {
            processRequest(e.getMessage(), e.getHttpStatus(), response);
        } catch (Exception e) {
            processRequest(e.getMessage(), 500, response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String txtResponse = "Destination deleted";
        try {
            
            if (request.getPathInfo() != null) {
                String pathParam = request.getPathInfo().replace("/", "");
                destinationService.deleteDestinationById(Integer.parseInt(pathParam));
                processRequest(txtResponse, 200, response);
            } else {
                
                String result = "Es necesario el id del destino para eliminarlo";
                processRequest(result, 400, response);
            }

        } catch (HttpException e) {
            processRequest(e.getMessage(), e.getHttpStatus(), response);
        } catch (Exception e) {
            processRequest(e.getMessage(), 500, response);
        }
        
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String txtResponse = "Destination updated";
        String destinationBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("Destination body:" + destinationBody);

        //Convert body into an object
        Destination destination = ConverterJsonToObjectUtil.getDestination(destinationBody);
        System.out.println(destination.getId());
        
        try {
            
            if (request.getPathInfo() != null) {
                String pathParam = request.getPathInfo().replace("/", "");
                destination.setId(Integer.parseInt(pathParam));
                //List<ControlPoint> controlPoints = controlPointService.getControlPointById(Integer.parseInt(pathParam));
                destinationService.updateDestination(destination);
                processRequest(txtResponse, 200, response);
            } else {
                
                String result = "Es necesario el id del destino para actualizarla";
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
