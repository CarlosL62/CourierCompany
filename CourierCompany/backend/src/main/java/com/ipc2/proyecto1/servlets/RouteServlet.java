/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ipc2.proyecto1.servlets;

import com.ipc2.proyecto1.exceptions.HttpException;
import com.ipc2.proyecto1.model.Route;
import com.ipc2.proyecto1.service.RouteService;
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
@WebServlet(name = "RouteServlet", urlPatterns = {"/route/*"})
public class RouteServlet extends HttpServlet {

    private RouteService routeService;

    public RouteServlet() {
        this.routeService = new RouteService();
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
                List<Route> routes = routeService.getRouteById(Integer.parseInt(pathParam));
                String result = ConverterJsonToObjectUtil.jsonFromRoutes(routes);
                processRequest(result, 200, response);
            } else {
                List<Route> routes = routeService.getRoutes();
                String result = ConverterJsonToObjectUtil.jsonFromRoutes(routes);
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

        String txtResponse = "OK Route";
        String routeBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("Route body:" + routeBody);
        
        //Convert body into an object
        Route route = ConverterJsonToObjectUtil.getRoute(routeBody);
        System.out.println(route.getDestinationId());
        
        //Send it to service
        try {
            routeService.addRoute(route);
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
        
        String txtResponse = "Route updated";
        String routeBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("Route body:" + routeBody);

        //Convert body into an object
        Route route = ConverterJsonToObjectUtil.getRoute(routeBody);
        System.out.println(route.getId());
        
        try {
            
            if (request.getPathInfo() != null) {
                String pathParam = request.getPathInfo().replace("/", "");
                route.setId(Integer.parseInt(pathParam));
                //List<ControlPoint> controlPoints = controlPointService.getControlPointById(Integer.parseInt(pathParam));
                routeService.updateRoute(route);
                processRequest(txtResponse, 200, response);
            } else {
                
                String result = "Es necesario el id de la ruta para actualizarla";
                processRequest(result, 400, response);
            }

        } catch (HttpException e) {
            processRequest(e.getMessage(), e.getHttpStatus(), response);
        } catch (Exception e) {
            processRequest(e.getMessage(), 500, response);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String txtResponse = "Route deleted";
        try {
            
            if (request.getPathInfo() != null) {
                String pathParam = request.getPathInfo().replace("/", "");
                routeService.deleteRouteById(Integer.parseInt(pathParam));
                processRequest(txtResponse, 200, response);
            } else {
                
                String result = "Es necesario el id de la ruta para eliminarla";
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
