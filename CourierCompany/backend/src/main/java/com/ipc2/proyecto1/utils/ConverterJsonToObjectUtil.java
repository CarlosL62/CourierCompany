/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipc2.proyecto1.exceptions.BadRequestException;
import com.ipc2.proyecto1.exceptions.InternalServerError;
import com.ipc2.proyecto1.model.Client;
import com.ipc2.proyecto1.model.ControlPoint;
import com.ipc2.proyecto1.model.PackageN;
import com.ipc2.proyecto1.model.Route;
import com.ipc2.proyecto1.model.RoutesControlPoint;
import com.ipc2.proyecto1.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author carlos
 */
public class ConverterJsonToObjectUtil {
    
    public static User getUser(String body) {
        try {
            return new ObjectMapper().readValue(body, User.class);
        } catch (JsonProcessingException ex) {
            throw new BadRequestException("json invalido");
        }
    }
    
    public static Client getClient(String body) {
        try {
            return new ObjectMapper().readValue(body, Client.class);
        } catch (JsonProcessingException ex) {
            throw new BadRequestException("json invalido");
        }
    }

    public static PackageN getPackage(String body) {
        try {
            return new ObjectMapper().readValue(body, PackageN.class);
        } catch (JsonProcessingException ex) {
            throw new BadRequestException("json invalido");
        }
    }
    
    public static String jsonFromPackages(List<PackageN> packages) {
            
        try {
            return new ObjectMapper().writeValueAsString(packages);
        } catch (JsonProcessingException ex) {
           throw new InternalServerError("error serializing packages");
        }
       
    }
    
    public static Route getRoute(String body) {
        try {
            return new ObjectMapper().readValue(body, Route.class);
        } catch (JsonProcessingException ex) {
            throw new BadRequestException("json invalido");
        }
    }
    
    public static ControlPoint getControlPoint(String body) {
        try {
            return new ObjectMapper().readValue(body, ControlPoint.class);
        } catch (JsonProcessingException ex) {
            throw new BadRequestException("json invalido");
        }
    }
    
    public static RoutesControlPoint getRoutesControlPoint(String body) {
        try {
            return new ObjectMapper().readValue(body, RoutesControlPoint.class);
        } catch (JsonProcessingException ex) {
            throw new BadRequestException("json invalido");
        }
    }
    
    public static String jsonFromControlPoints(List<ControlPoint> controlPoints) {
        try {
            return new ObjectMapper().writeValueAsString(controlPoints);
        } catch (JsonProcessingException ex) {
           throw new InternalServerError("error serializing packages");
        }
    }
    
    public static String jsonFromRoutes(List<Route> routes) {
        try {
            return new ObjectMapper().writeValueAsString(routes);
        } catch (JsonProcessingException ex) {
           throw new InternalServerError("error serializing packages");
        }
    }
    
}   

