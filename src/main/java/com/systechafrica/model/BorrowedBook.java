package com.systechafrica.model;

import java.time.LocalDate;
import java.util.Objects;

public class BorrowedBook {
    private Book book;
    private Student student;

    private LocalDate borrowingDate;
    private LocalDate returnDate;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDate getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(LocalDate borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public BorrowedBook(Book book, Student student, LocalDate borrowingDate, LocalDate returnDate) {
        this.book = book;
        this.student = student;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
    }

    public BorrowedBook() {
    }

    @Override
    public String toString() {
        return "BorrowedBook{" +
                "book=" + book +
                ", student=" + student +
                ", borrowingDate=" + borrowingDate +
                ", returnDate=" + returnDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowedBook that = (BorrowedBook) o;
        return Objects.equals(book, that.book) && Objects.equals(student, that.student) && Objects.equals(borrowingDate, that.borrowingDate) && Objects.equals(returnDate, that.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, student, borrowingDate, returnDate);
    }
}
