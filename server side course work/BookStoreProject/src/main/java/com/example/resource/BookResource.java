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


@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    
    private static Map<Integer,Book> books = new HashMap<>();
    private static AtomicInteger bookIdCounter = new AtomicInteger(1);
    
    @POST
    public Response createBook(Book book) throws InvalidPropertiesFormatException{
        if (book.getPublicationYear()>Calendar.getInstance().get(Calendar.YEAR)){
            throw new InvalidPropertiesFormatException("Publicatio year cannot be in the future");
    }
        int id = bookIdCounter.getAndIncrement();
        book.setId(id);
        books.put(id, book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    
    }
    
    @GET
    public Collection<Book> getAllBooks(){
        return books.values();
    }
    
    @GET
    @Path("/{id")
    public Book getBook(@PathParam("id") int id){
        Book book = books.get(id);
        if (book == null) throw new BookNotFoundException("Book with Id "+id+" not found");
        return book;
    }
    
    @PUT
    @Path("/{id}")
    public Book updateBook(@PathParam("id") int id, Book updatedBook){
        Book book = books.get(id);
        if(book == null )throw new BookNotFoundException("book with id "+id+" not found");
        updatedBook.setId(id);
        books.put(id, updatedBook);
        return updatedBook;
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id){
        if(!books.containsKey(id)) throw new BookNotFoundException("book with id "+id+" not found");
        books.remove(id);
        return Response.noContent().build();
    }
}
