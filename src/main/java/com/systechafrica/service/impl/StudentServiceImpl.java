package com.systechafrica.service.impl;

import com.systechafrica.db.DatabaseHandler;
import com.systechafrica.model.Book;
import com.systechafrica.model.Student;
import com.systechafrica.service.StudentService;
import com.systechafrica.util.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class StudentServiceImpl implements StudentService {

    private final DatabaseHandler databaseHandler;

    private final Logger logger;

    public StudentServiceImpl(DatabaseHandler databaseHandler, Logger logger) {
        this.databaseHandler = databaseHandler;
        this.logger = logger;
        //create table if not exists
        setupDatabase();
    }

    public void setupDatabase() {
        logger.info("initializing database table students...");
        try (Connection connection = databaseHandler.connect(Config.CONNECTION_URL, Config.DB_USER, Config.DB_PASSWORD); Statement statement = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS students(id int not null auto_increment primary key,reg_no " +
                    "varchar(50) not null unique,name varchar(100) not null);";
            statement.executeUpdate(query);
        } catch (ClassNotFoundException | SQLException e) {
            logger.severe("Failed database operation: " + e.getMessage());
        }
    }

    @Override
    public boolean saveStudent(Student student)  throws RuntimeException {
        logger.info("saving student with name: " + student.getStudentName());
        String query = "insert into students(reg_no,name)values(?,?);";
        try (Connection connection = databaseHandler.connect(Config.CONNECTION_URL, Config.DB_USER, Config.DB_PASSWORD); PreparedStatement preparedStatement =
                connection.prepareStatement(query)) {
            preparedStatement.setString(1, student.getRegNo());
            preparedStatement.setString(2, student.getStudentName());
            long noOfRowsInserted = preparedStatement.executeUpdate();
            return noOfRowsInserted > 0;
        } catch (ClassNotFoundException | SQLException e) {
            logger.severe("Failed database operation: " + e.getMessage());
            return false;
        }
    }
}
