/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipc2.proyecto1.exceptions;

/**
 *
 * @author carlos
 */
public class HttpException extends RuntimeException{
    private final int httpStatus;

    public HttpException(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpException(int httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpException(int httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public HttpException(int httpStatus, Throwable cause) {
        super(cause);
        this.httpStatus = httpStatus;
    }

    public HttpException(int httpStatus, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
    
}
