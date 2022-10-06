package com.estudos.bookservice.controllers;

import com.estudos.bookservice.model.Book;
import com.estudos.bookservice.proxy.CambioProxy;
import com.estudos.bookservice.repositories.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book endpoint")
@RestController
@RequestMapping("book-service")
public class BookController {

    @Autowired
    private Environment environment;

    @Autowired
    private BookRepository repository;

    @Autowired
    private CambioProxy proxy;

    @Operation(summary = "Find a specific book by your ID")
    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ) {
        var bookOptional = repository.findById(id);

        if (bookOptional.isEmpty()){
            throw new RuntimeException("Book not found");
        }

        var book = bookOptional.get();
        var port = environment.getProperty("local.server.port");
        var cambio = proxy.getCambio(book.getPrice(), "USD", currency);

        book.setEnvironment(port);
        book.setPrice(cambio.getConvertedValue());
        return book;
    }
}
