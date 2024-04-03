package com.ipc2.proyecto1.service;

import com.ipc2.proyecto1.exceptions.BadRequestException;
import com.ipc2.proyecto1.model.User;
import com.ipc2.proyecto1.repository.UserRepository;
import com.ipc2.proyecto1.utils.Verifications;

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

    public void addUser(User user) {
        isValidUser(user);
        userRepository.addUser(user);
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
