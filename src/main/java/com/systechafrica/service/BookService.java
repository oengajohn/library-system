package com.systechafrica.service;

import com.systechafrica.exception.BookNotFoundException;
import com.systechafrica.model.Book;
import java.util.List;

public interface BookService {
    List<Book> getAllBooks() throws RuntimeException ;
    Book getBookByIsbn(String isbn) throws BookNotFoundException;

    boolean saveBook(Book book) throws RuntimeException;


}
