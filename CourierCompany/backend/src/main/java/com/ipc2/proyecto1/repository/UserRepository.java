/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.repository;

import com.ipc2.proyecto1.model.User;
import com.ipc2.proyecto1.utils.DataBaseUtils;

/**
 *
 * @author carlos
 */
public class UserRepository {
    
    private DataBaseUtils dataBaseUtils;
    
    public UserRepository() {
        this.dataBaseUtils = new DataBaseUtils();
    }
    
    public void addUser(User user)  {
        String query = String.format("INSERT INTO Users (UserID, UserName, UserType, UserStatus) VALUES (\"%s\", \"%s\", \"%s\", \"%s\")",  user.getId(), user.getName(), user.getType(), user.getStatus());
        this.dataBaseUtils.insert(query);
        System.out.println("query:"+query);
    }
}
