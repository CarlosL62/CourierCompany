/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.service;

import com.ipc2.proyecto1.model.GlobalCost;
import com.ipc2.proyecto1.repository.GlobalCostRepository;
import java.util.List;

/**
 *
 * @author carlos
 */
public class GlobalCostService {
    
    private GlobalCostRepository globalCostRepository;

    public GlobalCostService() {
        this.globalCostRepository = new GlobalCostRepository();
    }
    
    public List<GlobalCost> getGlobalCost() {
        return globalCostRepository.getGlobalCost();
    }
    
    public void updateGlobalCost(GlobalCost globalCost) {
        
        globalCostRepository.updateGlobalCost(globalCost);
    }
    
}
