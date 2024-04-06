/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipc2.proyecto1.exceptions.BadRequestException;
import com.ipc2.proyecto1.exceptions.InternalServerError;
import com.ipc2.proyecto1.model.Bill;
import com.ipc2.proyecto1.model.BillDetail;
import com.ipc2.proyecto1.model.Client;
import com.ipc2.proyecto1.model.ControlPoint;
import com.ipc2.proyecto1.model.GlobalCost;
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
    
    public static String jsonFromUsers(List<User> users) {
        try {
            return new ObjectMapper().writeValueAsString(users);
        } catch (JsonProcessingException ex) {
           throw new InternalServerError("error serializing packages");
        }
    }
    
    public static GlobalCost getGlobalCost(String body) {
        try {
            return new ObjectMapper().readValue(body, GlobalCost.class);
        } catch (JsonProcessingException ex) {
            throw new BadRequestException("json invalido");
        }
    }
    
    public static String jsonFromGlobalCost(List<GlobalCost> globalCost) {
        try {
            return new ObjectMapper().writeValueAsString(globalCost);
        } catch (JsonProcessingException ex) {
           throw new InternalServerError("error serializing packages");
        }
    }
    
    public static Bill getBill(String body) {
        try {
            return new ObjectMapper().readValue(body, Bill.class);
        } catch (JsonProcessingException ex) {
            throw new BadRequestException("json invalido");
        }
    }
    
    public static BillDetail getBillDetail(String body) {
        try {
            return new ObjectMapper().readValue(body, BillDetail.class);
        } catch (JsonProcessingException ex) {
            throw new BadRequestException("json invalido");
        }
    }
}   

