/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.repository;

import com.ipc2.proyecto1.model.Route;
import com.ipc2.proyecto1.utils.DataBaseUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos
 */
public class RouteRepository {
    
    private DataBaseUtils dataBaseUtils;
    private List<String> fieldNames = new ArrayList<>();

    public RouteRepository() {
        this.dataBaseUtils = new DataBaseUtils();
        fieldNames.add("RouteID");
        fieldNames.add("DestinationID");
        fieldNames.add("PackagesLimit");
        fieldNames.add("Status");
    }
    
    public List<Route> getRoutes() {
        
        String query = String.format("SELECT %s FROM Routes;", String.join(",", fieldNames));
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<Route> routes = new ArrayList();
        for (Object[] row : result) {

            Route routeRow = new Route();
            
            routeRow.setId((Integer) row[0]);
            routeRow.setDestinationId((Integer) row[1]);
            routeRow.setPackagesLimit((Integer) row[2]);
            routeRow.setStatus((String) row[3]);

            routes.add(routeRow);
        }
        return routes;
    }
    
    public List<Route> getRouteById(int id) {
        
        String query = String.format("SELECT %s FROM Routes WHERE RouteID = %s;", String.join(",", fieldNames), id);
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<Route> routes = new ArrayList();
        for (Object[] row : result) {

            Route routeRow = new Route();
            
            routeRow.setId((Integer) row[0]);
            routeRow.setDestinationId((Integer) row[1]);
            routeRow.setPackagesLimit((Integer) row[2]);
            routeRow.setStatus((String) row[3]);

            routes.add(routeRow);
        }
        return routes;
    }
    
    public void addRoute(Route route) {
        String query = String.format("INSERT INTO Routes (destinationID, PackagesLimit) VALUES (\"%s\", \"%s\");", route.getDestinationId(), route.getPackagesLimit());
        this.dataBaseUtils.insert(query);
        System.out.println("query:"+query);
    }
    
    public void updateRoute(Route route) {
        String query = String.format("UPDATE Routes SET DestinationID = %s, PackagesLimit = %s, Status = \"%s\"  WHERE RouteID = %s;", route.getDestinationId(), route.getPackagesLimit(), route.getStatus(), route.getId());
        dataBaseUtils.update(query);
        System.out.println("query:"+query);
    }
    
    public void deleteRouteById(int id) {
        String query = String.format("DELETE FROM Routes WHERE RouteID = %s;", id);
        dataBaseUtils.delete(query);
        System.out.println("query:"+query);
    }
}
