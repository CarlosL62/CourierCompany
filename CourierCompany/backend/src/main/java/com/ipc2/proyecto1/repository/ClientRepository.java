/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.repository;

import com.ipc2.proyecto1.model.Client;
import com.ipc2.proyecto1.utils.DataBaseUtils;
import java.sql.SQLException;

/**
 *
 * @author carlos
 */
public class ClientRepository {
    
    private DataBaseUtils dataBaseUtils;
    
    public ClientRepository() {
        this.dataBaseUtils = new DataBaseUtils();
    }
    
    public void addClient(Client client) {
        String query = String.format("INSERT INTO Clients (ClientID, ClientName, ClientPhoneNumber) VALUES (\"%s\", \"%s\", \"%s\")", client.getId(), client.getName(), client.getPhoneNumber());
        this.dataBaseUtils.insert(query);
        System.out.println("query:"+query);
    }
}
