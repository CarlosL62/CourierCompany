/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.exceptions;

/**
 *
 * @author carlos
 */
public class BadRequestException extends HttpException{
    
    private static int HTTP_CODE=400;

    public BadRequestException() {
        super(HTTP_CODE);
    }

    public BadRequestException(String message) {
        super(HTTP_CODE,message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(HTTP_CODE,message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(HTTP_CODE,cause);
    }

    public BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(HTTP_CODE,message, cause, enableSuppression, writableStackTrace);
    } 
}
