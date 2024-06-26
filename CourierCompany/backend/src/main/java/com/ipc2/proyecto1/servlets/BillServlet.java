/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ipc2.proyecto1.servlets;

import com.ipc2.proyecto1.exceptions.HttpException;
import com.ipc2.proyecto1.model.Bill;
import com.ipc2.proyecto1.model.BillDetail;
import com.ipc2.proyecto1.model.BillDetailsByBillId;
import com.ipc2.proyecto1.service.BillService;
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
@WebServlet(name = "BillServlet", urlPatterns = {"/bills/*"})
public class BillServlet extends HttpServlet {

    private final BillService billService;

    public BillServlet() {
        this.billService = new BillService();
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

            if (request.getPathInfo() != null) {
                String pathParam = request.getPathInfo().replace("/", "");

                if (pathParam.equals("bill_details")) {
                    String txtResponse = "OK BillDetails";
                    int billIdParam = Integer.parseInt(request.getParameter("bill_id"));
                    BillDetail billDetail = new BillDetail();
                    billDetail.setBillId(billIdParam);

                    //Send it to service
                    try {
                        List<BillDetailsByBillId> billDetailsByBillIds = billService.getBillDetailsById(billDetail);
                        String result = ConverterJsonToObjectUtil.jsonFromBillDetailsById(billDetailsByBillIds);
                        processRequest(result, 200, response);
                    } catch (HttpException e) {
                        processRequest(e.getMessage(), e.getHttpStatus(), response);
                    } catch (Exception e) {
                        processRequest(e.getMessage(), 500, response);
                    }
                    return;
                }

                List<Bill> bills = billService.getBillById(Integer.parseInt(pathParam));
                if (bills.isEmpty()) {
                    processRequest("No se encontró la factura", 404, response);
                    return;
                }
                String result = ConverterJsonToObjectUtil.jsonFromBills(bills);
                processRequest(result, 200, response);
            } else {
                List<Bill> bills = billService.getBills();
                String result = ConverterJsonToObjectUtil.jsonFromBills(bills);
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

        try {

            if (request.getPathInfo() != null) {
                String pathParam = request.getPathInfo().replace("/", "");
                if (pathParam.equals("bill_details")) {
                    String txtResponse = "OK BillDetails";

                    int billIdParam = Integer.parseInt(request.getParameter("bill_id"));
                    int packageIdParam = Integer.parseInt(request.getParameter("package_id"));

                    BillDetail billDetail = new BillDetail();
                    billDetail.setBillId(billIdParam);
                    billDetail.setPackageId(packageIdParam);

                    //Send it to service
                    try {
                        billService.addBillDetail(billDetail);
                        processRequest(txtResponse, 200, response);
                    } catch (HttpException e) {
                        processRequest(e.getMessage(), e.getHttpStatus(), response);
                    } catch (Exception e) {
                        processRequest(e.getMessage(), 500, response);
                    }
                    return;
                }
            }
        } catch (HttpException e) {
            processRequest(e.getMessage(), e.getHttpStatus(), response);
        } catch (Exception e) {
            processRequest(e.getMessage(), 500, response);
        }

        String txtResponse = "OK Bill";
        String billBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("Bill body:" + billBody);

        //Convert body into an object
        Bill bill = ConverterJsonToObjectUtil.getBill(billBody);
        System.out.println(bill.getId());

        //Send it to service
        try {
            billService.addBill(bill);
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
