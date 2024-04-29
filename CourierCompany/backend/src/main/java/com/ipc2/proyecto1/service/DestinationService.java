package com.ipc2.proyecto1.service;

import com.ipc2.proyecto1.exceptions.BadRequestException;
import com.ipc2.proyecto1.model.Destination;
import com.ipc2.proyecto1.model.User;
import com.ipc2.proyecto1.repository.DestinationRepository;
import com.ipc2.proyecto1.repository.UserRepository;
import com.ipc2.proyecto1.utils.Verifications;

import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author carlos
 */
public class DestinationService {

    private DestinationRepository destinationRepository;

    public DestinationService() {
        this.destinationRepository = new DestinationRepository();
    }

    public List<Destination> getDestinations() {
        
        return destinationRepository.getDestinations();
    }
    
    public List<Destination> getDestinationById(int id) {
        
        return destinationRepository.getDestinationById(id);
    }
    
    public void addDestination(Destination destination) {
        destinationRepository.addDestination(destination);
    }
    
    public void updateDestination(Destination destination) {
        
        destinationRepository.updateDestination(destination);
    }
    
    public void deleteDestinationById(int id) {
        
        destinationRepository.deleteDestinationById(id);
    }
}
