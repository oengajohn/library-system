package com.systechafrica.service;

import com.systechafrica.model.Book;
import com.systechafrica.model.BorrowedBook;
import com.systechafrica.model.Student;
import java.util.List;

public interface BorrowedBookService {
    List<BorrowedBook> getBorrowedBooks(String regNo);
    boolean borrowBook(Book book, Student student);

    boolean returnBook(Book book);
}
