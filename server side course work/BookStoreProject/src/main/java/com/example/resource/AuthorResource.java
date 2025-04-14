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
import java.util.concurrent.atomic.AtomicInteger;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {
    private static Map<Integer,Author> authors = new HashMap<>();
    private static AtomicInteger authorIdCounter = new AtomicInteger(1);
    
    @POST
    public Response createAuthor(Author author){
        int id  = authorIdCounter.getAndIncrement();
        author.setId(id);
        authors.put(id, author);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }
    
    @GET
    public Collection<Author> getAllAuthors(){
        return authors.values();
    }
    
    @GET
    @Path("/{id}")
    public Author getAuthor(@PathParam("id") int id) {
        Author author = authors.get(id);
        if (author == null) throw new AurthorNotFoundException("Author with ID " + id + " not found.");
        return author;
    }
    
    @PUT
    @Path("/{id}")
    public Author updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        if (!authors.containsKey(id)) throw new AurthorNotFoundException("Author with ID " + id + " not found.");
        updatedAuthor.setId(id);
        authors.put(id, updatedAuthor);
        return updatedAuthor;
    }
    
     @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        if (!authors.containsKey(id)) throw new AurthorNotFoundException("Author with ID " + id + " not found.");
        authors.remove(id);
        return Response.noContent().build();
    }
    
    @GET
    @Path("/{id}/books")
    public List<Book> getBooksByAuthor(@PathParam("id") int id) {
        if(!authors.containsKey(id)) throw new AurthorNotFoundException("not found");
       return BookResource.getAllStudentsStatic().stream()
               .filter(book ->book.getAuthorId() == id).
               toList();
       
}
}
