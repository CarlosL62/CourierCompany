/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.repository;

import com.ipc2.proyecto1.model.User;
import com.ipc2.proyecto1.utils.DataBaseUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos
 */
public class UserRepository {
    
    private DataBaseUtils dataBaseUtils;
    private List<String> fieldNames = new ArrayList<>();
    
    public UserRepository() {
        this.dataBaseUtils = new DataBaseUtils();
        fieldNames.add("UserID");
        fieldNames.add("UserName");
        fieldNames.add("UserType");
        fieldNames.add("UserStatus");
    }
    
    public List<User> getUsers() {
        
        String query = String.format("SELECT %s FROM Users;", String.join(",", fieldNames));
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<User> users = new ArrayList();
        for (Object[] row : result) {

            User userRow = new User();
            
            userRow.setId((Integer) row[0]);
            userRow.setName((String) row[1]);
            userRow.setType((String) row[2]);
            userRow.setStatus((String) row[3]);

            users.add(userRow);
        }
        return users;
    }
    
    public List<User> getUserById(int id) {
        
        String query = String.format("SELECT %s FROM Users WHERE UserID = %s;", String.join(",", fieldNames), id);
        List<Object[]> result = this.dataBaseUtils.select(query, fieldNames);

        List<User> users = new ArrayList();
        for (Object[] row : result) {

            User userRow = new User();
            
            userRow.setId((Integer) row[0]);
            userRow.setName((String) row[1]);
            userRow.setType((String) row[2]);
            userRow.setStatus((String) row[3]);

            users.add(userRow);
        }
        return users;
    }
    
    public void addUser(User user)  {
        String query = String.format("INSERT INTO Users (UserID, UserName, UserType, UserStatus) VALUES (\"%s\", \"%s\", \"%s\", \"%s\")",  user.getId(), user.getName(), user.getType(), user.getStatus());
        this.dataBaseUtils.insert(query);
        System.out.println("query:"+query);
    }
    
    public void updateUser(User user) {
        String query = String.format("UPDATE Users SET UserName = \"%s\", UserType = \"%s\", UserStatus = \"%s\"  WHERE UserID = %s;", user.getName(), user.getType(), user.getStatus(), user.getId());
        dataBaseUtils.update(query);
        System.out.println("query:"+query);
    }
    
    public void deleteUserById(int id) {
        String query = String.format("DELETE FROM Users WHERE UserID = %s;", id);
        dataBaseUtils.delete(query);
        System.out.println("query:"+query);
    }
}
