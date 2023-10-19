package com.systechafrica.service.impl;

import com.systechafrica.db.DatabaseHandler;
import com.systechafrica.model.Book;
import com.systechafrica.model.BorrowedBook;
import com.systechafrica.model.Student;
import com.systechafrica.service.BookService;
import com.systechafrica.service.BorrowedBookService;
import com.systechafrica.service.StudentService;
import com.systechafrica.util.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class BorrowedBookServiceImpl implements BorrowedBookService {
    private final DatabaseHandler databaseHandler;

    private final Logger logger;

    private final StudentService studentService;
    private final BookService bookService;

    public BorrowedBookServiceImpl(DatabaseHandler databaseHandler, Logger logger, StudentService studentService, BookService bookService) {
        this.databaseHandler = databaseHandler;
        this.logger = logger;
        this.studentService = studentService;
        this.bookService = bookService;
        //create table if not exists
        setupDatabase();
    }

    public void setupDatabase() {
        logger.info("initializing database table books...");
        try (Connection connection = databaseHandler.connect(Config.CONNECTION_URL, Config.DB_USER, Config.DB_PASSWORD); Statement statement = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS borrowed_books(id int not null auto_increment primary key,book_id int not null,student_id int not null,borrowing_date date,return_date date,FOREIGN KEY (book_id) REFERENCES books(id),FOREIGN KEY (student_id) REFERENCES students(id));";
            statement.executeUpdate(query);
        } catch (ClassNotFoundException | SQLException e) {
            logger.severe("Failed database operation: " + e.getMessage());
        }
    }
    @Override
    public List<BorrowedBook> getBorrowedBooks(String regNo) {
        return null;
    }

    @Override
    public boolean borrowBook(Book book, Student student) {
        Student dbStudentObject =  studentService.getStudentByRegNo(student.getRegNo());
        Book dbBookObject =  bookService.getBookByIsbn(book.getIsbn());

        BorrowedBook borrowedBook = new BorrowedBook(
                dbBookObject,
                dbStudentObject,
                LocalDate.now(),
                null
        );

        logger.info("saving borrowed book with title: " + dbBookObject.getTitle());
        String query = "insert into borrowed_books(book_id,student_id,borrowing_date)values(?,?,?);";
        try (Connection connection = databaseHandler.connect(Config.CONNECTION_URL, Config.DB_USER, Config.DB_PASSWORD); PreparedStatement preparedStatement =
                connection.prepareStatement(query)) {
            preparedStatement.setInt(1, borrowedBook.getBook().getId());
            preparedStatement.setInt(2, borrowedBook.getStudent().getId());
            preparedStatement.setString(3, borrowedBook.getBorrowingDate().toString());

            long noOfRowsInserted = preparedStatement.executeUpdate();
            return noOfRowsInserted > 0;
        } catch (ClassNotFoundException | SQLException e) {
            logger.severe("Failed database operation: " + e.getMessage());
            return false;
        }



    }

    @Override
    public boolean returnBook(Book book) {
        return false;
    }
}
