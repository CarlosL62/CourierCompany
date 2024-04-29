/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.service;

import com.ipc2.proyecto1.model.Route;
import com.ipc2.proyecto1.model.RoutesControlPoint;
import com.ipc2.proyecto1.model.User;
import com.ipc2.proyecto1.repository.RoutesControlPointRepository;

import java.util.List;

/**
 *
 * @author carlos
 */
public class RoutesControlPointService {
    
    private final RoutesControlPointRepository routeControlPointRepository;

    public RoutesControlPointService() {
        this.routeControlPointRepository = new RoutesControlPointRepository();
    }

    public List<RoutesControlPoint> getRoutesControlPoints() {
        return routeControlPointRepository.getRoutesControlPoints();
    }

    public List<RoutesControlPoint> getRoutesControlPointsById(RoutesControlPoint routesControlPoint) {
        return routeControlPointRepository.getRouteControlPointById(routesControlPoint);
    }

    public void addRoutesControlPoint(RoutesControlPoint routeControlPoint) {
        routeControlPointRepository.addRoutesControlPoint(routeControlPoint);
    }

    public void deleteRoutesControlPointById(RoutesControlPoint routeControlPoint) {
        routeControlPointRepository.deleteRoutesControlPointById(routeControlPoint);
    }
}
