package com.ipc2.proyecto1.service;

import com.ipc2.proyecto1.exceptions.BadRequestException;
import com.ipc2.proyecto1.model.User;
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
public class UserService {

    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public List<User> getUsers() {
        
        return userRepository.getUsers();
    }
    
    public List<User> getUserById(int id) {
        
        return userRepository.getUserById(id);
    }
    
    public void addUser(User user) {
        isValidUser(user);
        userRepository.addUser(user);
    }
    
    public void updateUser(User user) {
        
        userRepository.updateUser(user);
    }
    
    public void deleteUserById(int id) {
        
        userRepository.deleteUserById(id);
    }

    public void isValidUser(User user) {
        /*if (!Verifications.isInt(user.getId())) {
            throw new BadRequestException("invalid id");
        }*/
        if (!Verifications.isText(user.getName())) {
            throw new BadRequestException("invalid name");
        }
        if (!user.getType().equals("administrador")
                && !user.getType().equals("operador")
                && !user.getType().equals("recepcionista")) {
            throw new BadRequestException("invalid type");
        }
        if (!user.getStatus().equals("activo")
                && !user.getStatus().equals("inactivo")) {
            throw new BadRequestException("invalid status");

        }
    }
}
