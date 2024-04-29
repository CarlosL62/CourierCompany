/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.service;

import com.ipc2.proyecto1.model.ControlPoint;
import com.ipc2.proyecto1.model.GlobalCost;
import com.ipc2.proyecto1.repository.ControlPointRepository;
import com.ipc2.proyecto1.repository.GlobalCostRepository;

import java.util.List;

/**
 *
 * @author carlos
 */
public class ControlPointService {

    private final GlobalCostRepository globalCostRepository;
    private final ControlPointRepository controlPointRepository;

    public ControlPointService() {
        this.controlPointRepository = new ControlPointRepository();
        this.globalCostRepository = new GlobalCostRepository();
    }
    
    public void addControlPoint(ControlPoint controlPoint){
        if (controlPoint.getOperationCost() == 0) {
            //Global Cost Data
            List<GlobalCost> globalCost = globalCostRepository.getGlobalCost();
            controlPoint.setOperationCost(globalCost.get(0).getControlPointCost());
        }
        controlPointRepository.addControlPoint(controlPoint);
    }
    
    public List<ControlPoint> getControlPoints() {
        
        return controlPointRepository.getControlPoints();
    }
    
    public List<ControlPoint> getControlPointById(int id) {
        
        return controlPointRepository.getControlPointById(id);
    }
    
    public void deleteControlPointById(int id) {
        
        controlPointRepository.deleteControlPointById(id);
    }
    
    public void updateControlPoint(ControlPoint controlPoint) {
        
        controlPointRepository.updateControlPoint(controlPoint);
    }
    
}
