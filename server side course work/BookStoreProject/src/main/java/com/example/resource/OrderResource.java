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
import java.util.stream.Collectors;

@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
     private static List<Order> allOrders = new ArrayList<>();
     private static int orderIdCounter = 1;
     
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response placeOrder(@PathParam("customerId") int customerId){
         Cart cart = CartResource.getCartStatic().stream().filter(c -> c.getCustomerId() == customerId)
                 .findFirst()
                 .orElseThrow(()-> new CartNotFoundException("cart not found"));
         
         if(cart.getItems().isEmpty()){
             throw new CartNotFoundException("cart is empty");
         }
         
         for(CartItem item:cart.getItems()){
             Book book = BookResource.getAllBooksStatic().stream()
                     .filter(b -> b.getId()==item.getBookId())
                     .findFirst()
                     .orElseThrow(()-> new BookNotFoundException("book not found"));
             if(book.getStock()<item.getQuantity()){
                 throw new OutOfStockException("not enough stock for book "+ book.getTitle());
             }
             book.setStock(book.getStock()-item.getQuantity());
             
         }
         
         Order order = new Order(orderIdCounter++,customerId, new ArrayList<>(cart.getItems()));
         allOrders.add(order);
         
         
         return Response.status(Response.Status.CREATED).entity(order).build();
         
     }
     
     @GET
     public List<Order> getAllOrders(@PathParam("customerId") int customerId){
         return allOrders.stream()
                 .filter(order -> order.getCustomerId() == customerId)
                 .collect(Collectors.toList());
     }
     
     @GET
     @Path("/{orderId}")
     public Response getOrderById(@PathParam("customerId") int customerId,
             @PathParam("orderId") int orderId){
        Order foundOrder = allOrders.stream()
                 .filter(order -> order.getCustomerId() == customerId && order.getOrderId() == orderId)
                 .findFirst()
                 .orElseThrow(()-> new InvalidInputException("order not found"));
        
        return Response.ok().entity(foundOrder).build();
     }
}
