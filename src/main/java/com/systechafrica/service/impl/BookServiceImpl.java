package com.systechafrica.service.impl;

import com.systechafrica.db.DatabaseHandler;
import com.systechafrica.exception.BookNotFoundException;
import com.systechafrica.model.Book;
import com.systechafrica.model.Category;
import com.systechafrica.service.BookService;
import com.systechafrica.util.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BookServiceImpl implements BookService {

    private final DatabaseHandler databaseHandler;

    private final Logger logger;

    public BookServiceImpl(DatabaseHandler databaseHandler, Logger logger) {
        this.databaseHandler = databaseHandler;
        this.logger = logger;
        //create table if not exists
        setupDatabase();
    }

    public void setupDatabase() {
        logger.info("initializing database table books...");
        try (Connection connection = databaseHandler.connect(Config.CONNECTION_URL, Config.DB_USER, Config.DB_PASSWORD); Statement statement = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS books(id int not null auto_increment primary key,isbn varchar(50) not null unique,title varchar(100) not null,author varchar" +
                    "(50) not null ,book_edition varchar(20) not null,category ENUM('FICTION', 'NON_FICTION') NOT NULL);";
            statement.executeUpdate(query);
        } catch (ClassNotFoundException | SQLException e) {
            logger.severe("Failed database operation: " + e.getMessage());
        }
    }

    @Override
    public List<Book> getAllBooks() throws RuntimeException {
        logger.info("getting all books");
        List<Book> books = new ArrayList<>();
        try (Connection connection = databaseHandler.connect(Config.CONNECTION_URL, Config.DB_USER, Config.DB_PASSWORD); Statement statement = connection.createStatement()) {
            String query = "select * from books;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String isbn = resultSet.getString("isbn");
                String author = resultSet.getString("author");
                String title = resultSet.getString("title");
                String edition = resultSet.getString("book_edition");
                Category category = Category.valueOf(resultSet.getString("category"));
                Book book = new Book(id, isbn, author, title, edition, category);
                books.add(book);
            }

        } catch (ClassNotFoundException | SQLException e) {
            logger.severe("Failed database operation: " + e.getMessage());
        }
        return books;
    }

    @Override
    public Book getBookByIsbn(String isbn) throws BookNotFoundException {
        logger.info("getting book by isbn: " + isbn);

        String query = "select * from books where isbn=?;";
        try (Connection connection = databaseHandler.connect(Config.CONNECTION_URL, Config.DB_USER, Config.DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String author = resultSet.getString("author");
                String title = resultSet.getString("title");
                String edition = resultSet.getString("book_edition");
                Category category = Category.valueOf(resultSet.getString("category"));
                return new Book(id, isbn, author, title, edition, category);
            }

        } catch (ClassNotFoundException | SQLException e) {
            logger.severe("Failed database operation: " + e.getMessage());
        }
        throw new BookNotFoundException("Book not found with the isbn: " + isbn);
    }

    @Override
    public boolean saveBook(Book book) throws RuntimeException {
        logger.info("saving book with title: " + book.getTitle());
        String query = "insert into books(isbn,title,author,book_edition,category)values(?,?,?,?,?);";
        try (Connection connection = databaseHandler.connect(Config.CONNECTION_URL, Config.DB_USER, Config.DB_PASSWORD); PreparedStatement preparedStatement =
                connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getEdition());
            preparedStatement.setString(5, book.getCategory().name());
            long noOfRowsInserted = preparedStatement.executeUpdate();
            return noOfRowsInserted > 0;
        } catch (ClassNotFoundException | SQLException e) {
            logger.severe("Failed database operation: " + e.getMessage());
            return false;
        }
    }
}
