/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.repository;

import com.ipc2.proyecto1.model.RoutesControlPoint;
import com.ipc2.proyecto1.utils.DataBaseUtils;

/**
 *
 * @author carlos
 */
public class RoutesControlPointRepository {
    
    private DataBaseUtils dataBaseUtils;

    public RoutesControlPointRepository() {
        this.dataBaseUtils = new DataBaseUtils();
    }
    
    public void addRoutesControlPoint(RoutesControlPoint routesControlPoint) {
        String query = String.format("Insert INTO RoutesControlPoints (RouteID, ControlPointID) VALUES (\"%s\", \"%s\")", routesControlPoint.getRouteId(), routesControlPoint.getControlPointId());
        dataBaseUtils.insert(query);
        System.out.println("query:"+query);
    }
}
