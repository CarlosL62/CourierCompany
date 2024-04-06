/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.repository;

import com.ipc2.proyecto1.model.Bill;
import com.ipc2.proyecto1.utils.DataBaseUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos
 */
public class BillRepository {
    
    private DataBaseUtils dataBaseUtils;
    private List<String> fieldNames = new ArrayList<>();

    public BillRepository() {
        this.dataBaseUtils = new DataBaseUtils();
        fieldNames.add("BillID");
        fieldNames.add("BillDate");
        fieldNames.add("ClientID");
    }
    
    public void addBill(Bill bill) {
        String query = String.format("INSERT INTO Bills (BillDate, ClientID) VALUES (\"%s\", %s);", bill.getDate(), bill.getClientId());
        this.dataBaseUtils.insert(query);
        System.out.println("query:"+query);
    }
}
