/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.service;

import com.ipc2.proyecto1.exceptions.BadRequestException;
import com.ipc2.proyecto1.model.PackageN;
import com.ipc2.proyecto1.model.User;
import com.ipc2.proyecto1.repository.PackageRepository;
import com.ipc2.proyecto1.utils.Verifications;
import java.util.List;

/**
 *
 * @author carlos
 */
public class PackageService {
    
    private final PackageRepository packageRepository;

    public PackageService() {
        this.packageRepository = new PackageRepository();
    }
    
    public void addPackage(PackageN packagen) {
        isValidPackage(packagen);
        packageRepository.addPackage(packagen);
    }
    
    public List<PackageN> getPackages() {
        return packageRepository.getPackages();
    }
    
    public List<PackageN> getPackageById(int id){
        return packageRepository.getPackageById(id);
    }
    
    public List<PackageN> getPackageByStatus(String status){
        return packageRepository.getPackageByStatus(status);
    }

    public void updatePackage(PackageN packageN) {

        packageRepository.updatePackage(packageN);
    }
    
    public void isValidPackage (PackageN packagen) {
        
        /*if (!packagen.getStatus().equals("enBodega")
                && !packagen.getStatus().equals("enRuta")
                && !packagen.getStatus().equals("enDestino")
                && !packagen.getStatus().equals("entregado")) {
            return false;
        }*/
        if (!Verifications.isValidDate(packagen.getBeginDate())) {
            throw new BadRequestException("invalid begin date");
        }
        if (packagen.getEndDate() != null) {
            throw new BadRequestException("endDate must be null");
        }
    }
}
