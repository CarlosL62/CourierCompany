/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ipc2.proyecto1.servlets;

import com.ipc2.proyecto1.exceptions.HttpException;
import com.ipc2.proyecto1.model.ControlPoint;
import com.ipc2.proyecto1.service.ControlPointService;
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
@WebServlet(name = "ControlPointServlet", urlPatterns = {"/control_point/*"})
public class ControlPointServlet extends HttpServlet {

    private ControlPointService controlPointService;

    public ControlPointServlet() {
        this.controlPointService = new ControlPointService();
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
                List<ControlPoint> controlPoints = controlPointService.getControlPointById(Integer.parseInt(pathParam));
                String result = ConverterJsonToObjectUtil.jsonFromControlPoints(controlPoints);
                processRequest(result, 200, response);
            } else {
                List<ControlPoint> controlPoints = controlPointService.getControlPoints();
                String result = ConverterJsonToObjectUtil.jsonFromControlPoints(controlPoints);
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

        String txtResponse = "OK ControlPoint";
        String controlPointBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("Package body:" + controlPointBody);

        //Convert body into an object
        ControlPoint controlPoint = ConverterJsonToObjectUtil.getControlPoint(controlPointBody);
        System.out.println(controlPoint.getId());

        //Send it to service
        try {
            controlPointService.addControlPoint(controlPoint);
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
        
        String txtResponse = "ControlPoint deleted";
        try {
            
            if (request.getPathInfo() != null) {
                String pathParam = request.getPathInfo().replace("/", "");
                controlPointService.deleteControlPointById(Integer.parseInt(pathParam));
                processRequest(txtResponse, 200, response);
            } else {
                
                String result = "Es necesario el id del controlPoint para eliminarlo";
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
        String txtResponse = "ControlPoint updated";
        String controlPointBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("ControlPoint body:" + controlPointBody);

        //Convert body into an object
        ControlPoint controlPoint = ConverterJsonToObjectUtil.getControlPoint(controlPointBody);
        System.out.println(controlPoint.getId());
        
        try {
            
            if (request.getPathInfo() != null) {
                String pathParam = request.getPathInfo().replace("/", "");
                controlPoint.setId(Integer.parseInt(pathParam));
                //List<ControlPoint> controlPoints = controlPointService.getControlPointById(Integer.parseInt(pathParam));
                controlPointService.updateControlPoint(controlPoint);
                processRequest(txtResponse, 200, response);
            } else {
                
                String result = "Es necesario el id del controlPoint para actualizarla";
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
