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
public class CustomerResource {
    private static List<Customer> customers = new ArrayList<>();
    private static int idCounter = 0;
    
    @POST
    public Response createCustomer(Customer customer){
        customer.setId(idCounter++);
        customers.add(customer.getId(),customer);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }
    
    public static List<Customer> getAllCustomersStatic() {
        return customers;
    }
    
    @GET
    public Collection<Customer> getAllCustomers(){
        return customers;
    }
    
    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") int id) {
        
        Customer customer = customers.stream().filter(c -> c.getId()== id).findFirst().orElseThrow(() -> new CustomerNotFoundException("customer not found"));
        return Response.ok(customer).build();
    }
    
    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer updatedCustomer){
        Customer existing = customers.get(id);
        if(existing == null) throw new CustomerNotFoundException("customer not found");
         updatedCustomer.setId(id);
        customers.add(id, updatedCustomer);
        return Response.ok(updatedCustomer).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        if (customers.remove(id) == null)
            throw new CustomerNotFoundException("Customer not found");
        return Response.noContent().build();
    }
}
