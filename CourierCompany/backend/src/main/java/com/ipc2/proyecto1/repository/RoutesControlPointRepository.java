/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.repository;

import com.ipc2.proyecto1.model.Route;
import com.ipc2.proyecto1.model.RoutesControlPoint;
import com.ipc2.proyecto1.model.User;
import com.ipc2.proyecto1.utils.DataBaseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos
 */
public class RoutesControlPointRepository {
    
    private DataBaseUtils dataBaseUtils;
    private List<String> fieldNames = new ArrayList<>();

    public RoutesControlPointRepository() {
        this.dataBaseUtils = new DataBaseUtils();
        fieldNames.add("RouteID");
        fieldNames.add("ControlPointID");
    }

    public List<RoutesControlPoint> getRoutesControlPoints() {

        String query = String.format("SELECT %s FROM RoutesControlPoints ORDER BY RouteID;", String.join(",", fieldNames));
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<RoutesControlPoint> routesControlPoints = new ArrayList();
        for (Object[] row : result) {

            RoutesControlPoint routesControlPointRow = new RoutesControlPoint();

            routesControlPointRow.setRouteId((Integer) row[0]);
            routesControlPointRow.setControlPointId((Integer) row[1]);

            routesControlPoints.add(routesControlPointRow);
        }
        return routesControlPoints;
    }

    public List<RoutesControlPoint> getRouteControlPointById(RoutesControlPoint routeControlPoint) {

        String query = String.format("SELECT %s FROM RoutesControlPoints WHERE RouteID = %s && ControlPointID: %s;", String.join(",", fieldNames), routeControlPoint.getRouteId() ,routeControlPoint.getControlPointId());
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<RoutesControlPoint> routesControlPoints = new ArrayList();
        for (Object[] row : result) {

            RoutesControlPoint routesControlPointRow = new RoutesControlPoint();

            routesControlPointRow.setRouteId((Integer) row[0]);
            routesControlPointRow.setControlPointId((Integer) row[1]);

            routesControlPoints.add(routesControlPointRow);
        }
        return routesControlPoints;
    }
    
    public void addRoutesControlPoint(RoutesControlPoint routesControlPoint) {
        String query = String.format("Insert INTO RoutesControlPoints (RouteID, ControlPointID) VALUES (\"%s\", \"%s\")", routesControlPoint.getRouteId(), routesControlPoint.getControlPointId());
        dataBaseUtils.insert(query);
        System.out.println("query:"+query);
    }

    public void deleteRoutesControlPointById(RoutesControlPoint routeControlPoint) {
        String query = String.format("DELETE FROM RoutesControlPoints WHERE RouteID = %s && ControlPointID = %s;", routeControlPoint.getRouteId(), routeControlPoint.getControlPointId());
        dataBaseUtils.delete(query);
        System.out.println("query:"+query);
    }
}
