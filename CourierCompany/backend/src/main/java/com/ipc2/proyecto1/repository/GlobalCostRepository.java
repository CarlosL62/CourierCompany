/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.repository;

import com.ipc2.proyecto1.model.GlobalCost;
import com.ipc2.proyecto1.utils.DataBaseUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos
 */
public class GlobalCostRepository {
    
    private DataBaseUtils dataBaseUtils;
    private List<String> fieldNames = new ArrayList<>();

    public GlobalCostRepository() {
        this.dataBaseUtils = new DataBaseUtils();
        fieldNames.add("ControlPointCost");
        fieldNames.add("WeigthCost");
    }
    
    public List<GlobalCost> getGlobalCost() {
        
        String query = String.format("SELECT %s FROM GlobalCosts;", String.join(",", fieldNames));
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<GlobalCost> globalCost = new ArrayList();
        for (Object[] row : result) {

            GlobalCost globalCostRow = new GlobalCost();
            
            globalCostRow.setControlPointCost((Integer) row[0]);
            globalCostRow.setWeigthCost((Integer) row[1]);

            globalCost.add(globalCostRow);
        }
        return globalCost;
    }
    
    public void updateGlobalCost(GlobalCost globalCost) {
        String query = String.format("UPDATE GlobalCosts SET ControlPointCost = %s, WeigthCost = %s WHERE ID = 1;", globalCost.getControlPointCost(), globalCost.getWeigthCost());
        dataBaseUtils.update(query);
        System.out.println("query:"+query);
    }
    
}
