/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.repository;

import com.ipc2.proyecto1.model.Client;
import com.ipc2.proyecto1.model.User;
import com.ipc2.proyecto1.utils.DataBaseUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos
 */
public class ClientRepository {
    
    private DataBaseUtils dataBaseUtils;
    private List<String> fieldNames = new ArrayList<>();
    
    public ClientRepository() {
        this.dataBaseUtils = new DataBaseUtils();
        fieldNames.add("ClientID");
        fieldNames.add("ClientName");
        fieldNames.add("ClientPhoneNumber");
    }
    
    public void addClient(Client client) {
        String query = String.format("INSERT INTO Clients (ClientID, ClientName, ClientPhoneNumber) VALUES (\"%s\", \"%s\", \"%s\")", client.getId(), client.getName(), client.getPhoneNumber());
        this.dataBaseUtils.insert(query);
        System.out.println("query:"+query);
    }


    public List<Client> getClients() {

        String query = String.format("SELECT %s FROM Clients;", String.join(",", fieldNames));
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<Client> clients = new ArrayList();
        for (Object[] row : result) {

            Client clientRow = new Client();

            clientRow.setId((Integer) row[0]);
            clientRow.setName((String) row[1]);
            clientRow.setPhoneNumber((Integer) row[2]);

            clients.add(clientRow);
        }
        return clients;
    }

    public List<Client> getClientById(int id) {

        String query = String.format("SELECT %s FROM Clients WHERE ClientID = %s;", String.join(",", fieldNames), id);
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<Client> clients = new ArrayList();
        for (Object[] row : result) {

            Client clientRow = new Client();

            clientRow.setId((Integer) row[0]);
            clientRow.setName((String) row[1]);
            clientRow.setPhoneNumber((Integer) row[2]);

            clients.add(clientRow);
        }
        return clients;
    }

    public void updateClient(Client client) {
        String query = String.format("UPDATE Clients SET ClientName = \"%s\", ClientPhoneNumber = \"%s\" WHERE ClientID = %s;", client.getName(), client.getPhoneNumber(), client.getId());
        dataBaseUtils.update(query);
        System.out.println("query:"+query);
    }
}
