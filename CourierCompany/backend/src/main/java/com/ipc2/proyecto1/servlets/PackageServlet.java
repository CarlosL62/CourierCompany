/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ipc2.proyecto1.servlets;

import com.ipc2.proyecto1.exceptions.HttpException;
import com.ipc2.proyecto1.model.PackageN;
import com.ipc2.proyecto1.service.PackageService;
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
@WebServlet(name = "PackageServlet", urlPatterns = {"/packages/*"})
public class PackageServlet extends HttpServlet {

    private PackageService packageService;

    public PackageServlet() {
        this.packageService = new PackageService();
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

            String status = request.getParameter("status");
            if (status != null && !status.isEmpty()) {
                List<PackageN> packages = packageService.getPackageByStatus(status);
                String result = ConverterJsonToObjectUtil.jsonFromPackages(packages);
                processRequest(result, 200, response);
                return;
            }
            
            if (request.getPathInfo() != null) {
                String pathParam = request.getPathInfo().replace("/", "");
                List<PackageN> packages = packageService.getPackageById(Integer.parseInt(pathParam));
                String result = ConverterJsonToObjectUtil.jsonFromPackages(packages);
                processRequest(result, 200, response);
            } else {
                List<PackageN> packages = packageService.getPackages();
                String result = ConverterJsonToObjectUtil.jsonFromPackages(packages);
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

        String txtResponse = "OK Package";
        String packageBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("Package body:" + packageBody);

        //Convert body into an object
        PackageN packagen = ConverterJsonToObjectUtil.getPackage(packageBody);
        System.out.println(packagen.getClientId());

        //Send it to service
        try {
            packageService.addPackage(packagen);
            processRequest(txtResponse, 200, response);
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
