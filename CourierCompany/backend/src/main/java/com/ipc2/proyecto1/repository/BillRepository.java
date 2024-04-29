/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.repository;

import com.ipc2.proyecto1.model.Bill;
import com.ipc2.proyecto1.model.BillDetail;
import com.ipc2.proyecto1.model.BillDetailsByBillId;
import com.ipc2.proyecto1.model.User;
import com.ipc2.proyecto1.utils.DataBaseUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos
 */
public class BillRepository {
    
    private final DataBaseUtils dataBaseUtils;
    private final List<String> fieldNames = new ArrayList<>();
    private final List<String> fieldNamesBillDetails = new ArrayList<>();

    public BillRepository() {
        this.dataBaseUtils = new DataBaseUtils();
        fieldNames.add("BillID");
        fieldNames.add("BillDate");
        fieldNames.add("ClientID");

        fieldNamesBillDetails.add("BillID");
        fieldNamesBillDetails.add("BillDate");
        fieldNamesBillDetails.add("ClientID");
        fieldNamesBillDetails.add("PackageID");
        fieldNamesBillDetails.add("DestinationCost");
        fieldNamesBillDetails.add("WeigthCost");
        fieldNamesBillDetails.add("TotalCost");
    }

    public List<Bill> getBills() {

        String query = String.format("SELECT %s FROM Bills;", String.join(",", fieldNames));
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<Bill> bills = new ArrayList();
        for (Object[] row : result) {

            Bill billRow = new Bill();

            billRow.setId((Integer) row[0]);
            billRow.setDate((String) row[1]);
            billRow.setClientId((Integer) row[2]);

            bills.add(billRow);
        }
        return bills;
    }

    public List<Bill> getBillById(int id) {

        String query = String.format("SELECT %s FROM Bill WHERE BillID = %s;", String.join(",", fieldNames), id);
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<Bill> bills = new ArrayList();
        for (Object[] row : result) {

            Bill billRow = new Bill();

            billRow.setId((Integer) row[0]);
            billRow.setDate((String) row[1]);
            billRow.setClientId((Integer) row[2]);

            bills.add(billRow);
        }
        return bills;
    }

    public void addBill(Bill bill) {
        String query = String.format("INSERT INTO Bills (BillDate, ClientID) VALUES (\"%s\", %s);", bill.getDate(), bill.getClientId());
        this.dataBaseUtils.insert(query);
        System.out.println("query:"+query);
    }
    
    public void addBillDetail(BillDetail billDetail) {
        String query = String.format("INSERT INTO BillDetails (BillID, PackageID, DestinationCost, WeigthCost) VALUES (%s, %s, %s, %s);", billDetail.getBillId(), billDetail.getPackageId(), billDetail.getDestinationCost(), billDetail.getWeigthCost());
        this.dataBaseUtils.insert(query);
        System.out.println("query:"+query);
    }

    public List<BillDetailsByBillId> getBillDetailsById(BillDetail billDetail) {
        String query = String.format("SELECT Bills.*, BillDetails.PackageID, BillDetails.DestinationCost, BillDetails.WeigthCost, (BillDetails.DestinationCost + BillDetails.WeigthCost) AS TotalCost FROM Bills INNER JOIN BillDetails ON Bills.BillID = BillDetails.BillID WHERE Bills.BillID = %s;", billDetail.getBillId());
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNamesBillDetails);

        List<BillDetailsByBillId> billDetailsByBillId = new ArrayList();
        for (Object[] row : result) {

            BillDetailsByBillId bdrow = new BillDetailsByBillId();

            bdrow.setBillId((Integer) row[0]);
            bdrow.setBillDate((String) row[1]);
            bdrow.setClientId((Integer) row[2]);
            bdrow.setPackageId((Integer) row[3]);
            bdrow.setDestinationCost((Integer) row[4]);
            bdrow.setWeigthCost((Integer) row[5]);
            bdrow.setTotalCost(((Long) row[6]).intValue());

            billDetailsByBillId.add(bdrow);
        }
        return billDetailsByBillId;
    }
}
