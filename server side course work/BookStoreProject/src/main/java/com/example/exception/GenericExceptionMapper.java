/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.exception;

/**
 *
 * @author alekm
 */
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import java.util.Map;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<RuntimeException>{

    @Override
    public Response toResponse(RuntimeException e) {
        Response.Status status = switch (e.getClass().getSimpleName()){
            case  "BookNotFoundException", "AuthorNotFoundException", "CustomerNotFoundException", "CartNotFoundException" -> Response.Status.NOT_FOUND;
            case "InvalidInputException", "OutOfStockException" -> Response.Status.BAD_REQUEST;
            default -> Response.Status.INTERNAL_SERVER_ERROR;
        };
        return Response.status(status).entity(Map.of("error",e.getClass().getSimpleName(),
                "message",e.getMessage())).build();
    }
    
}
