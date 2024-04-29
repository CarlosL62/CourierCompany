/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.repository;

import com.ipc2.proyecto1.model.PackageN;
import com.ipc2.proyecto1.model.User;
import com.ipc2.proyecto1.utils.DataBaseUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos
 */
public class PackageRepository {

    private DataBaseUtils dataBaseUtils;
    private List<String> fieldNames = new ArrayList<>();

    public PackageRepository() {
        this.dataBaseUtils = new DataBaseUtils();
        fieldNames.add("PackageID");
        fieldNames.add("ClientID");
        fieldNames.add("PackageWeigth");
        fieldNames.add("PackageDescription");
        fieldNames.add("PackageStatus");
        fieldNames.add("PackageBeginDate");
        fieldNames.add("PackageEndDate");
    }

    public void addPackage(PackageN packagen) {
        String query = String.format("INSERT INTO Packages (ClientID, PackageWeigth, PackageDescription, PackageBeginDate) VALUES (%s, %s, \"%s\", \"%s\")", packagen.getClientId(), packagen.getWeigth(), packagen.getDescription(), packagen.getBeginDate());
        this.dataBaseUtils.insert(query);
        System.out.println("query:" + query);
    }

    public List<PackageN> getPackages() {
        

        String query = String.format("SELECT %s FROM Packages;", String.join(",", fieldNames));
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<PackageN> packages = new ArrayList();
        for (Object[] row : result) {

            PackageN packageRow = new PackageN();
            packageRow.setId((Integer) row[0]);
            packageRow.setClientId((Integer) row[1]);
            packageRow.setWeigth((Integer) row[2]);
            packageRow.setDescription((String) row[3]);
            packageRow.setStatus((String) row[4]);
            packageRow.setBeginDate((String) row[5]);
            packageRow.setEndDate((String) row[6]);

            packages.add(packageRow);
        }
        return packages;

    }

    public List<PackageN> getPackageById(int id) {
        String query = String.format("SELECT %s FROM Packages WHERE PackageID = %s;", String.join(",", fieldNames), id);
        
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<PackageN> packages = new ArrayList();
        for (Object[] row : result) {

            PackageN packageRow = new PackageN();
            packageRow.setId((Integer) row[0]);
            packageRow.setClientId((Integer) row[1]);
            packageRow.setWeigth((Integer) row[2]);
            packageRow.setDescription((String) row[3]);
            packageRow.setStatus((String) row[4]);
            packageRow.setBeginDate((String) row[5]);
            packageRow.setEndDate((String) row[6]);

            packages.add(packageRow);
        }
        return packages;
    }
    
    public List<PackageN> getPackageByStatus(String status) {
        String query = String.format("SELECT %s FROM Packages WHERE PackageStatus = \"%s\";", String.join(",", fieldNames), status);
        
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<PackageN> packages = new ArrayList();
        for (Object[] row : result) {

            PackageN packageRow = new PackageN();
            packageRow.setId((Integer) row[0]);
            packageRow.setClientId((Integer) row[1]);
            packageRow.setWeigth((Integer) row[2]);
            packageRow.setDescription((String) row[3]);
            packageRow.setStatus((String) row[4]);
            packageRow.setBeginDate((String) row[5]);
            packageRow.setEndDate((String) row[6]);

            packages.add(packageRow);
        }
        return packages;
    }

    public void updatePackage(PackageN packageN) {
        String query = String.format("UPDATE Packages SET ClientID = \"%s\", PackageWeigth = \"%s\", PackageDescription = \"%s\", PackageStatus = \"%s\", PackageBeginDate = \"%s\", PackageEndDate = \"%s\"  WHERE PackageID = %s;", packageN.getClientId(), packageN.getWeigth(), packageN.getDescription(), packageN.getStatus(), packageN.getBeginDate(), packageN.getEndDate(), packageN.getId());
        dataBaseUtils.update(query);
        System.out.println("query:"+query);
    }
}
