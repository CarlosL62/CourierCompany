/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.service;

import com.ipc2.proyecto1.model.Bill;
import com.ipc2.proyecto1.model.BillDetail;
import com.ipc2.proyecto1.repository.BillRepository;

/**
 *
 * @author carlos
 */
public class BillService {
    
    private BillRepository billRepository;

    public BillService() {
        this.billRepository = new BillRepository();
    }
    
    public void addBill(Bill bill) {
        
        billRepository.addBill(bill);
    }
    
    public void addBillDetail(BillDetail billDetail) {
        
        billRepository.addBillDetail(billDetail);
    }
}
