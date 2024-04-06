/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.utils;

import com.ipc2.proyecto1.exceptions.InternalServerError;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos
 */
public class DataBaseUtils {

    public Connection connect() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Conexion con la base de datos
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CourierDB", "miniroot", "621700");            
            System.out.println("Connection to MySQL has been established.");
        } catch (SQLException|ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insert(String query) {
        System.out.println("insert query: "+query);
        Connection conn = this.connect();
        try {
            Statement stmt = conn.createStatement();
           // Execute a query
            stmt.executeUpdate(query);
            System.out.println("Inserted records into the table...");
            conn.close();  
        } catch (SQLException e) {
            throw new InternalServerError(e.getMessage());
        }
    }
    
    public void delete(String query) {
        System.out.println("insert query: "+query);
        Connection conn = this.connect();
        try {
            Statement stmt = conn.createStatement();
           // Execute a query
            stmt.executeUpdate(query);
            System.out.println("Deleted records from the table...");
            conn.close();  
        } catch (SQLException e) {
            throw new InternalServerError(e.getMessage());
        }
    }
    
    public void update(String query) {
        System.out.println("insert query: "+query);
        Connection conn = this.connect();
        try {
            Statement stmt = conn.createStatement();
           // Execute a query
            stmt.executeUpdate(query);
            System.out.println("Updated records into the table...");
            conn.close();  
        } catch (SQLException e) {
            throw new InternalServerError(e.getMessage());
        }
    }
    
    public List<Object[]> select(String query, List<String> fieldNames) {
        System.out.println("select query: "+query);
        Connection conn = this.connect();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            List<Object[]> result=new ArrayList<>();
            while ( rs.next() ) {
                Object[] row=new Object[fieldNames.size()];
                for(int i=0;i<fieldNames.size();i++){
                   row[i]=rs.getObject(fieldNames.get(i));
                }
                result.add(row);
            }
            conn.close();
            return result;
        } catch (Exception e) {
            throw new InternalServerError("error getting values from data base");
        }
    }
}
