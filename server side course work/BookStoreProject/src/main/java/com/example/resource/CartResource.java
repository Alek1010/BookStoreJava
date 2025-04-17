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
import javax.ws.rs.core.Response;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {
    private static List<Cart> carts = new ArrayList<>();
    
    private Cart findOrCreateCart(int customerId){
        for (Cart cart:carts){
            if(cart.getCustomerId()==customerId){
                return cart;
            }
        }
        Cart newCart = new Cart(customerId);
        carts.add(newCart);
        return newCart;
            
        
    }
    
    private Cart findCart(int customerId){
        return carts.stream().filter(c -> c.getCustomerId() == customerId)
                .findFirst()
                .orElse(null);
    }
    
    public static List<Cart> getCartStatic() {
        return carts;
    }
    
     @POST
     @Path("/items")
     public Response addItem(@PathParam("customerId") int customerid,CartItem item){
         if(!CustomerResource.getAllCustomersStatic().contains(customerid)) 
             throw new CustomerNotFoundException("customer not found");
         
         Book book = BookResource.getAllBooksStatic().get(item.getBookId());
         if(book == null) throw new BookNotFoundException("book not found");
         
         if(item.getQuantity() > book.getStock()) throw new BookNotFoundException("book out of stock");
         
         Cart cart = findOrCreateCart(customerid);
         cart.addItem(item);
         return Response.status(Response.Status.CREATED).entity(item).build();
         
     }
     
     @GET
     public List<CartItem> viewCart(@PathParam("cutomerId") int customerId){
         Cart cart = findCart(customerId);
         if(cart == null ) throw new CartNotFoundException("cart not found");
         return cart.getItems();
     }
     
     @PUT
     @Path("/items/{bookId}")
     public Response updateItem(@PathParam("customerId") int customerId,
             @PathParam("bookId") int bookId,
             CartItem updatedItem){
         Cart cart = findCart(customerId);
         if(cart==null) throw new CartNotFoundException("cart not found");
         
         for(CartItem item:cart.getItems()){
             if(item.getBookId()== bookId){
                 item.setQuantity(updatedItem.getQuantity());
                 return Response.ok(item).build();
             }
         }
         throw new BookNotFoundException("BOOK not in cart");
     }
     
     @DELETE
     @Path("/items/{bookId}")
     public Response removeItem(@PathParam("customerId") int customerId,
             @PathParam("bookId") int bookId){
         Cart cart = findCart(customerId);
         if(cart == null) throw new CartNotFoundException("Cart not found");
         
         cart.removeItem(bookId);
         return Response.noContent().build();
     }
     
     
     
}
