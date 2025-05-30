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
public class AurthorNotFoundExceptionMapper implements ExceptionMapper<AurthorNotFoundException>{

    private static final Logger logger = LoggerFactory.getLogger(AurthorNotFoundExceptionMapper.class);
    
    @Override
    public Response toResponse(AurthorNotFoundException exception) {
        logger.error("Aurthor not found: {}", exception.getMessage(), exception);
        return Response.status(Response.Status.NOT_FOUND)
                       .entity(exception.getMessage())
                       .type(MediaType.TEXT_PLAIN)
                       .build();
    }
    
}
