/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.exceptions;

/**
 *
 * @author carlos
 */
public class InternalServerError extends HttpException{

    private static int HTTP_CODE = 500; 
    
    public InternalServerError() {
        super(HTTP_CODE);
    }

    public InternalServerError(String message) {
        super(HTTP_CODE,message);
    }

    public InternalServerError(String message, Throwable cause) {
        super(HTTP_CODE,message, cause);
    }

    public InternalServerError(Throwable cause) {
        super(HTTP_CODE,cause);
    }

    public InternalServerError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(HTTP_CODE,message, cause, enableSuppression, writableStackTrace);
    }
    
    
}
