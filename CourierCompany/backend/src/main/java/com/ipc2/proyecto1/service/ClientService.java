/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.service;

import com.ipc2.proyecto1.exceptions.BadRequestException;
import com.ipc2.proyecto1.model.Client;
import com.ipc2.proyecto1.model.User;
import com.ipc2.proyecto1.repository.ClientRepository;
import com.ipc2.proyecto1.utils.Verifications;

import java.util.List;

/**
 *
 * @author carlos
 */
public class ClientService {
    
    private ClientRepository clientRepository;

    public ClientService() {
        this.clientRepository = new ClientRepository();
    }

    public List<Client> getClients() {

        return clientRepository.getClients();
    }

    public List<Client> getClientById(int id) {

        return clientRepository.getClientById(id);
    }

    public void addClient(Client client) {
        isValidClient(client);
        clientRepository.addClient(client);
    }

    public void updateClient(Client client) {

        clientRepository.updateClient(client);
    }

    public void isValidClient(Client client){
        /*if (!Verifications.isInt(client.getId())) {
            return false;
        }*/
        if (!Verifications.isText(client.getName())) {
            throw new BadRequestException("invalid name");
        }
        /*if (!Verifications.isPhoneNumber(client.getPhoneNumber())) {
            return false;
        }*/
    }
    
}
