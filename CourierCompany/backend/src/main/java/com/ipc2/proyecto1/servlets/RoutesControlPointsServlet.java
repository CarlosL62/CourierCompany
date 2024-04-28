/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ipc2.proyecto1.servlets;

import com.ipc2.proyecto1.exceptions.HttpException;
import com.ipc2.proyecto1.model.RoutesControlPoint;
import com.ipc2.proyecto1.service.RoutesControlPointService;
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
@WebServlet(name = "RoutesControlPointsServlet", urlPatterns = {"/routes_control_points"})
public class RoutesControlPointsServlet extends HttpServlet {

    private final RoutesControlPointService routesControlPointsServive;

    public RoutesControlPointsServlet() {
        this.routesControlPointsServive = new RoutesControlPointService();
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

            String routeIdP = request.getParameter("route_id");
            String controlPointIdP = request.getParameter("control_point_id");

            if ((routeIdP != null) && (controlPointIdP != null)) {
                int routeId = Integer.parseInt(routeIdP);
                int controlPointId = Integer.parseInt(controlPointIdP);

                RoutesControlPoint routeControlPoint = new RoutesControlPoint();
                routeControlPoint.setRouteId(routeId);
                routeControlPoint.setControlPointId(controlPointId);
                List<RoutesControlPoint> routesControlPoints = routesControlPointsServive.getRoutesControlPointsById(routeControlPoint);
                String result = ConverterJsonToObjectUtil.jsonFromRoutesControlPoint(routesControlPoints);
                processRequest(result, 200, response);
                return;
            }

            List<RoutesControlPoint> routesControlPoints = routesControlPointsServive.getRoutesControlPoints();
            String result = ConverterJsonToObjectUtil.jsonFromRoutesControlPoint(routesControlPoints);
            processRequest(result, 200, response);

//            if (request.getPathInfo() != null) {
//                String pathParam = request.getPathInfo().replace("/", "");
//                List<RoutesControlPoint> routesControlPoints = routesControlPointsServive.getRoutesControlPointsById(Integer.parseInt(pathParam));
//                if (routesControlPoints.isEmpty()) {
//                    processRequest("No se encontró los puntos de control de la ruta", 404, response);
//                    return;
//                }
//                String result = ConverterJsonToObjectUtil.jsonFromRoutesControlPoint(routesControlPoints);
//                processRequest(result, 200, response);
//            } else {
//                List<RoutesControlPoint> routesControlPoints = routesControlPointsServive.getRoutesControlPoints();
//                String result = ConverterJsonToObjectUtil.jsonFromRoutesControlPoint(routesControlPoints);
//                processRequest(result, 200, response);
//            }

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
        
        String txtResponse = "OK RoutesControlPoints";
        String routesControlPointBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        
        //Convert body into an object
        RoutesControlPoint routesControlPoint = ConverterJsonToObjectUtil.getRoutesControlPoint(routesControlPointBody);
        System.out.println(routesControlPoint.getControlPointId());
        
        //Send it to service
        try {
            routesControlPointsServive.addRoutesControlPoint(routesControlPoint);
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

        String txtResponse = "RoutesControlPoint deleted";

        try {

            String routeIdP = request.getParameter("route_id");
            String controlPointIdP = request.getParameter("control_point_id");

            if ((routeIdP != null) && (controlPointIdP != null)) {
                int routeId = Integer.parseInt(routeIdP);
                int controlPointId = Integer.parseInt(controlPointIdP);

                RoutesControlPoint routeControlPoint = new RoutesControlPoint();
                routeControlPoint.setRouteId(routeId);
                routeControlPoint.setControlPointId(controlPointId);
                routesControlPointsServive.deleteRoutesControlPointById(routeControlPoint);
                processRequest(txtResponse, 200, response);
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
