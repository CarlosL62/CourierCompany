/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.repository;

import com.ipc2.proyecto1.model.ControlPoint;
import com.ipc2.proyecto1.utils.DataBaseUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos
 */
public class ControlPointRepository {
    
    private DataBaseUtils dataBaseUtils;
    private List<String> fieldNames = new ArrayList<>();
    
    public ControlPointRepository() {
        this.dataBaseUtils = new DataBaseUtils();
        fieldNames.add("ControlPointID");
        fieldNames.add("OperatorID");
        fieldNames.add("PackagesLimit");
        fieldNames.add("OperationCost");
    }
    
    public List<ControlPoint> getControlPoints() {
        
        String query = String.format("SELECT %s FROM ControlPoints;", String.join(",", fieldNames));
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<ControlPoint> controlPoints = new ArrayList();
        for (Object[] row : result) {

            ControlPoint controlPointRow = new ControlPoint();
            
            controlPointRow.setId((Integer) row[0]);
            controlPointRow.setOperatorId((Integer) row[1]);
            controlPointRow.setPackagesLimit((Integer) row[2]);
            controlPointRow.setOperationCost((Integer) row[3]);

            controlPoints.add(controlPointRow);
        }
        return controlPoints;
    }
    
    public List<ControlPoint> getControlPointById(int id) {
        
        String query = String.format("SELECT %s FROM ControlPoints WHERE ControlPointID = %s;", String.join(",", fieldNames), id);
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<ControlPoint> controlPoints = new ArrayList();
        for (Object[] row : result) {

            ControlPoint controlPointRow = new ControlPoint();
            
            controlPointRow.setId((Integer) row[0]);
            controlPointRow.setOperatorId((Integer) row[1]);
            controlPointRow.setPackagesLimit((Integer) row[2]);
            controlPointRow.setOperationCost((Integer) row[3]);

            controlPoints.add(controlPointRow);
        }
        return controlPoints;
    }
    
    public void addControlPoint(ControlPoint controlPoint) {
        String query = String.format("INSERT INTO ControlPoints (OperatorID, PackagesLimit, OperationCost) VALUES (\"%s\", \"%s\", \"%s\");", controlPoint.getOperatorId(), controlPoint.getPackagesLimit(), controlPoint.getOperationCost());
        dataBaseUtils.insert(query);
        System.out.println("query:"+query);
    }
    
    public void deleteControlPointById(int id) {
        String query = String.format("DELETE FROM ControlPoints WHERE ControlPointID = %s;", id);
        dataBaseUtils.delete(query);
        System.out.println("query:"+query);
    }
    
    public void updateControlPoint(ControlPoint controlPoint) {
        String query = String.format("UPDATE ControlPoints SET OperatorID = %s, PackagesLimit = %s, OperationCost = %s  WHERE ControlPointID = %s;", controlPoint.getOperatorId(), controlPoint.getPackagesLimit(), controlPoint.getOperationCost(), controlPoint.getId());
        dataBaseUtils.update(query);
        System.out.println("query:"+query);
    }
    
}
