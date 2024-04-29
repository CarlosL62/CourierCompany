/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.repository;

import com.ipc2.proyecto1.model.Destination;
import com.ipc2.proyecto1.model.User;
import com.ipc2.proyecto1.utils.DataBaseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos
 */
public class DestinationRepository {

    private DataBaseUtils dataBaseUtils;
    private List<String> fieldNames = new ArrayList<>();

    public DestinationRepository() {
        this.dataBaseUtils = new DataBaseUtils();
        fieldNames.add("destinationID");
        fieldNames.add("destinationName");
        fieldNames.add("destinationCost");
        fieldNames.add("destinationStatus");
    }
    
    public List<Destination> getDestinations() {
        
        String query = String.format("SELECT %s FROM Destinations;", String.join(",", fieldNames));
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<Destination> destinations = new ArrayList();
        for (Object[] row : result) {

            Destination destinationRow = new Destination();
            
            destinationRow.setId((Integer) row[0]);
            destinationRow.setName((String) row[1]);
            destinationRow.setCost((Integer) row[2]);
            destinationRow.setStatus((String) row[3]);

            destinations.add(destinationRow);
        }
        return destinations;
    }
    
    public List<Destination> getDestinationById(int id) {
        
        String query = String.format("SELECT %s FROM Destinations WHERE DestinationID = %s;", String.join(",", fieldNames), id);
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<Destination> destinations = new ArrayList();
        for (Object[] row : result) {

            Destination destinationRow = new Destination();
            
            destinationRow.setId((Integer) row[0]);
            destinationRow.setName((String) row[1]);
            destinationRow.setCost((Integer) row[2]);
            destinationRow.setStatus((String) row[3]);

            destinations.add(destinationRow);
        }
        return destinations;
    }
    
    public void addDestination(Destination destination)  {
        String query = String.format("INSERT INTO Destinations (destinationID, destinationName, destinationCost, destinationStatus) VALUES (\"%s\", \"%s\", \"%s\", \"%s\")",  destination.getId(), destination.getName(), destination.getCost(), destination.getStatus());
        this.dataBaseUtils.insert(query);
        System.out.println("query:"+query);
    }
    
    public void updateDestination(Destination destination) {
        String query = String.format("UPDATE Destinations SET destinationName = \"%s\", destinationCost = \"%s\", destinationStatus = \"%s\"  WHERE destinationID = %s;", destination.getName(), destination.getCost(), destination.getStatus(), destination.getId());
        dataBaseUtils.update(query);
        System.out.println("query:"+query);
    }
    
    public void deleteDestinationById(int id) {
        String query = String.format("DELETE FROM Destinations WHERE DestinationID = %s;", id);
        dataBaseUtils.delete(query);
        System.out.println("query:"+query);
    }
}
