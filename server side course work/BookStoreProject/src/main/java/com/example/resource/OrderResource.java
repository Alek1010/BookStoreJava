/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

/**
 *
 * @author alekm
 */
import com.example.model.*;
import com.example.exception.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
     private static List<CartItem> carts = new ArrayList<>();
    
     @POST
     @Path("/items")
     public Response addItem(@PathParam("customerId") int customerid,CartItem item){
         if(!CustomerResource.getAllCustomersStatic().contains(customerid)) 
             throw new CustomerNotFoundException("customer not found");
         
         //Book book = BookResource.getAllBooksStatic().get(item.getBook().getId());
     }
}
