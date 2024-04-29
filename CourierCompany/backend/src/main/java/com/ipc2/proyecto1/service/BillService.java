/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.service;

import com.ipc2.proyecto1.model.*;
import com.ipc2.proyecto1.repository.*;
import java.util.List;

/**
 *
 * @author carlos
 */
public class BillService {

    private DestinationRepository destinationRepository;
    private GlobalCostRepository globalCostRepository;
    private PackageRepository packageRepository;
    private BillRepository billRepository;

    public BillService() {
        this.billRepository = new BillRepository();
        this.packageRepository = new PackageRepository();
        this.globalCostRepository = new GlobalCostRepository();
        this.destinationRepository = new DestinationRepository();
    }

    public List<Bill> getBills() {

        return billRepository.getBills();
    }

    public List<Bill> getBillById(int id) {

        return billRepository.getBillById(id);
    }

    public void addBill(Bill bill) {
        
        billRepository.addBill(bill);
    }
    
    public void addBillDetail(BillDetail billDetail) {
        //Package Data
        List<PackageN> packageN = packageRepository.getPackageById(billDetail.getPackageId());
        //Global Cost Data
        List<GlobalCost> globalCost = globalCostRepository.getGlobalCost();
        //Destination Data
        List<Destination> destination = destinationRepository.getDestinationById(packageN.get(0).getDestinationId());

        //Costs set
        billDetail.setDestinationCost(destination.get(0).getCost());
        billDetail.setWeigthCost(globalCost.get(0).getWeigthCost() * packageN.get(0).getWeigth());

        billRepository.addBillDetail(billDetail);
    }

    public List<BillDetailsByBillId> getBillDetailsById(BillDetail billDetail) {

        return billRepository.getBillDetailsById(billDetail);
    }

}
