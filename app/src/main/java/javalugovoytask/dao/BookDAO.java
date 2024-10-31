package javalugovoytask.dao;


import javalugovoytask.dto.Book;
import java.sql.SQLException;
import java.util.List;

public interface BookDAO {
    void addBook(Book book) throws SQLException;
    List<Book> getAllBooks() throws SQLException;
    Book findBookByTitle(String title) throws SQLException;
    void deleteBook(int id) throws SQLException;
}
