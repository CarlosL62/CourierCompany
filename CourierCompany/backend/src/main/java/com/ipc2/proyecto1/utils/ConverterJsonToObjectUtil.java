/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipc2.proyecto1.exceptions.BadRequestException;
import com.ipc2.proyecto1.exceptions.InternalServerError;
import com.ipc2.proyecto1.model.*;

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

    public static String jsonFromDestinations(List<Destination> destinations) {
        try {
            return new ObjectMapper().writeValueAsString(destinations);
        } catch (JsonProcessingException ex) {
            throw new InternalServerError("error serializing packages");
        }
    }

    public static Destination getDestination(String body) {
        try {
            return new ObjectMapper().readValue(body, Destination.class);
        } catch (JsonProcessingException ex) {
            throw new BadRequestException("json invalido");
        }
    }

    public static String jsonFromClients(List<Client> clients) {
        try {
            return new ObjectMapper().writeValueAsString(clients);
        } catch (JsonProcessingException ex) {
            throw new InternalServerError("error serializing packages");
        }
    }

    public static RoutesControlPoint getRoutesControlPoint(String body) {
        try {
            return new ObjectMapper().readValue(body, RoutesControlPoint.class);
        } catch (JsonProcessingException ex) {
            throw new BadRequestException("json invalido");
        }
    }

    public static String jsonFromRoutesControlPoint(List<RoutesControlPoint> routesControlPoints) {
        try {
            return new ObjectMapper().writeValueAsString(routesControlPoints);
        } catch (JsonProcessingException ex) {
            throw new InternalServerError("error serializing packages");
        }
    }

    public static String jsonFromBills(List<Bill> bills) {
        try {
            return new ObjectMapper().writeValueAsString(bills);
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

    public static String jsonFromBillDetails(List<BillDetail> billDetails) {
        try {
            return new ObjectMapper().writeValueAsString(billDetails);
        } catch (JsonProcessingException ex) {
            throw new InternalServerError("error serializing packages");
        }
    }

    public static BillDetail getBillDetail(String body) {
        try {
            return new ObjectMapper().readValue(body, BillDetail.class);
        } catch (JsonProcessingException ex) {
            throw new BadRequestException("json invalido");
        }
    }

    public static String jsonFromBillDetailsById(List<BillDetailsByBillId> billDetailsByBillIds) {
        try {
            return new ObjectMapper().writeValueAsString(billDetailsByBillIds);
        } catch (JsonProcessingException ex) {
            throw new InternalServerError("error serializing packages");
        }
    }
}   

