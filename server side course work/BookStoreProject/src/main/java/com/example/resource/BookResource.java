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
    
    
    private static List<Book> books = new ArrayList<>();
    private static AtomicInteger bookIdCounter = new AtomicInteger(1);
    
    static{
        books.add(new Book(1,12,"testing","1234",2020,23.0,50));
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) throws InvalidPropertiesFormatException{
        if (book.getPublicationYear()>Calendar.getInstance().get(Calendar.YEAR)){
            throw new InvalidPropertiesFormatException("Publicatio year cannot be in the future");
    }
        int id = bookIdCounter.getAndIncrement();
        book.setId(id);
        books.add(id, book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    
    }
    
    public static List<Book> getAllBooksStatic() {
        return books;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Book> getAllBooks(){
        return books;
    }
    
    
    
    @GET
    @Path("/{id")
    public Book getBook(@PathParam("id") int id){
         return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(()-> new BookNotFoundException("book ID " + id + " not found"));
 
        
    }
    
    
    @PUT
    @Path("/{id}")
    public Book updateBook(@PathParam("id") int id, Book updatedBook){
        boolean found = false;
        updatedBook.setId(id);

        // Replace the book in the list
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                books.set(i, updatedBook);
                break;
            }
        }
        if (!found) {
        throw new BookNotFoundException("Book with ID " + id + " not found");
        }


        return updatedBook;
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id){
       Book bookToDelete = books.stream()
            .filter(book -> book.getId() == id)
            .findFirst()
            .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));

    books.remove(bookToDelete);
    return Response.noContent().build();
    }
}
