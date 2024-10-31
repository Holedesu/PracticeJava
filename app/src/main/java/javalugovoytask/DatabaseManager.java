package javalugovoytask;

import javalugovoytask.dto.Book;
import javalugovoytask.dto.Reader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/library_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "30092001";
    private Connection connection;

    // Установка соединения с базой данных

    public void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connected to PostgreSQL database!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to PostgreSQL database.");
        }
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from PostgreSQL database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author, published_date, isbn) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setDate(3, book.getPublishedDate());
            statement.setString(4, book.getIsbn());
            statement.executeUpdate();
            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add book.");
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                Date publishedDate = resultSet.getDate("published_date");
                String isbn = resultSet.getString("isbn");
                Book book = new Book(title, author, publishedDate, isbn);
                books.add(book);
            }
        }
        return books;
    }

    public void deleteBook(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Book deleted successfully.");
        }
    }

    public Book findBookByTitle(String title) throws SQLException {
        String sql = "SELECT * FROM books WHERE title = ?";
        Book book = new Book();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String author = resultSet.getString("author");
                Date publishedDate = resultSet.getDate("published_date");
                String isbn = resultSet.getString("isbn");
                book = new Book(title, author, publishedDate, isbn);
                System.out.println(book);
            }
        }
        return book;
    }

    public void addReader(Reader reader) throws SQLException {
        String sql = "INSERT INTO readers (name, email) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, reader.getName());
            statement.setString(2, reader.getEmail());
            statement.executeUpdate();
            System.out.println("Reader added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add reader.");
        }
    }

    public List<Reader> getAllReaders() throws SQLException {
        List<Reader> readers = new ArrayList<>();
        String sql = "SELECT * FROM readers";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                Reader reader = new Reader(name, email);
                readers.add(reader);
            }
        }
        for (Reader reader : readers) {
            System.out.println("The readers are: ");
            System.out.println(reader.getName());

        }
        return readers;
    }

    public void deleteReader(int id) throws SQLException {
        String sql = "DELETE FROM readers WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Reader deleted successfully.");
        }
    }

    public Reader findReaderByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM readers WHERE email = ?";
        Reader reader = new Reader();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                reader = new Reader(name, email);

            }
        }
        System.out.println(reader);
        return reader;
    }

    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        try {
            dbManager.connect();
//            dbManager.addBook("Book Title", "Book Author", new Date(System.currentTimeMillis()), "123-456-789");
//            dbManager.addReader(new Reader("John Doe", "YJ0sX@example.com"));
        } finally {dbManager.disconnect();
            }
        }
}
