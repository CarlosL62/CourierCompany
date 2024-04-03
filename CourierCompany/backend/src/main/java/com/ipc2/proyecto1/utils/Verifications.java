package com.ipc2.proyecto1.utils;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author carlos
 */
public class Verifications {
    
    public static boolean isPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]");
    }
    
    public static boolean isInt(String number) {
        return number.matches("[0-9]+");
    }
    
    public static boolean isText(String text){
        return text.matches("[^0-9]+");
    }
    
    public static boolean isValidDate(String str) {
        return str.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}");
    }
}