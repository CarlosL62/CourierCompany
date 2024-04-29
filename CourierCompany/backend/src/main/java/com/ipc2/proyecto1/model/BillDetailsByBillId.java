/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.model;

/**
 *
 * @author carlos
 */
public class BillDetailsByBillId {
    
    private int billId;
    private String billDate;
    private int clientId;
    private int packageId;
    private int destinationCost;
    private int weigthCost;
    private int totalCost;

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getDestinationCost() {
        return destinationCost;
    }

    public void setDestinationCost(int destinationCost) {
        this.destinationCost = destinationCost;
    }

    public int getWeigthCost() {
        return weigthCost;
    }

    public void setWeigthCost(int weigthCost) {
        this.weigthCost = weigthCost;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}
