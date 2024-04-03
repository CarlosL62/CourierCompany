/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.service;

import com.ipc2.proyecto1.model.RoutesControlPoint;
import com.ipc2.proyecto1.repository.RoutesControlPointRepository;

/**
 *
 * @author carlos
 */
public class RoutesControlPointService {
    
    private final RoutesControlPointRepository routeControlPointRepository;

    public RoutesControlPointService() {
        this.routeControlPointRepository = new RoutesControlPointRepository();
    }
    
    public void addRoutesControlPoint(RoutesControlPoint routeControlPoint) {
        
        routeControlPointRepository.addRoutesControlPoint(routeControlPoint);
    }
}
