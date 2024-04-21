/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ipc2.proyecto1.servlets;

import com.ipc2.proyecto1.exceptions.HttpException;
import com.ipc2.proyecto1.model.User;
import com.ipc2.proyecto1.service.UserService;
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
@WebServlet(name = "UserServlet", urlPatterns = {"/users/*"})
public class UserServlet extends HttpServlet {
    
    private final UserService userService; 

    public UserServlet() {
        this.userService = new UserService();
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
                List<User> users = userService.getUserById(Integer.parseInt(pathParam));
                if (users.isEmpty()) {
                    processRequest("No se encontró el usuario", 404, response);
                    return;
                }
                String result = ConverterJsonToObjectUtil.jsonFromUsers(users);
                processRequest(result, 200, response);
            } else {
                List<User> users = userService.getUsers();
                String result = ConverterJsonToObjectUtil.jsonFromUsers(users);
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
        
        String txtResponse = "OK Users";
        //String nombre = request.getParameter("hola");
        String userBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("user body: " + userBody);

        //Convert body into an object
        User user = ConverterJsonToObjectUtil.getUser(userBody);
        System.out.println(user.getName());
       
        //Send it to service
        
        try {
            userService.addUser(user);
            processRequest(txtResponse,200, response);
        } catch(HttpException e){
            processRequest(e.getMessage(),e.getHttpStatus(), response);
        }catch (Exception e) {
            processRequest(e.getMessage(),500, response);
        }
       
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String txtResponse = "User updated";
        String userBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("User body:" + userBody);

        //Convert body into an object
        User user = ConverterJsonToObjectUtil.getUser(userBody);
        System.out.println(user.getId());
        
        try {
            
            if (request.getPathInfo() != null) {
                String pathParam = request.getPathInfo().replace("/", "");
                user.setId(Integer.parseInt(pathParam));
                //List<ControlPoint> controlPoints = controlPointService.getControlPointById(Integer.parseInt(pathParam));
                userService.updateUser(user);
                processRequest(txtResponse, 200, response);
            } else {
                
                String result = "Es necesario el id del usuario para actualizarlo";
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
        
        String txtResponse = "User deleted";
        try {
            
            if (request.getPathInfo() != null) {
                String pathParam = request.getPathInfo().replace("/", "");
                userService.deleteUserById(Integer.parseInt(pathParam));
                processRequest(txtResponse, 200, response);
            } else {
                
                String result = "Es necesario el id del usuario para eliminarlo";
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
