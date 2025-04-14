/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.exception;

/**
 *
 * @author alekm
 */
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException>{
    private static final Logger logger = LoggerFactory.getLogger(CartNotFoundExceptionMapper.class);
    
    @Override
    public Response toResponse(InvalidInputException exception) {
       logger.error("invalid input: {}", exception.getMessage(), exception);
        return Response.status(Response.Status.NOT_FOUND)
                       .entity(exception.getMessage())
                       .type(MediaType.TEXT_PLAIN)
                       .build();
    }
    
}
