/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.service;

import com.ipc2.proyecto1.model.Route;
import com.ipc2.proyecto1.repository.RouteRepository;
import java.util.List;

/**
 *
 * @author carlos
 */
public class RouteService {
    
    private final RouteRepository routeRepository;

    public RouteService() {
        this.routeRepository = new RouteRepository();
    }
    
    public void addRoute(Route route) {
        
        routeRepository.addRoute(route);
    }
    
    public void isValidRoute() {
        
    }
    
    public List<Route> getRoutes() {
        
        return routeRepository.getRoutes();
    }
    
    public List<Route> getRouteById(int id) {
        
        return routeRepository.getRouteById(id);
    }
    
    public void updateRoute(Route route) {
        
        routeRepository.updateRoute(route);
    }
    
    public void deleteRouteById(int id) {
        
        routeRepository.deleteRouteById(id);
    }
}
