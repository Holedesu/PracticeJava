package javalugovoytask;
import javalugovoytask.dto.Book;
import javalugovoytask.dto.Reader;


import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseManagerTest {
    private Connection connection;
    private DatabaseManager dbManager;

    @BeforeEach
    public void setupDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/library_db", "postgres", "30092001");
        dbManager = new DatabaseManager();
        dbManager.connect();

        try (Statement stmt = connection.createStatement()) {
            // Дропаем таблицы, если они существуют
            stmt.execute("DROP TABLE IF EXISTS readers");
            stmt.execute("DROP TABLE IF EXISTS books");

            // Пересоздаём таблицы
            stmt.execute("CREATE TABLE books (" +
                    "id SERIAL PRIMARY KEY, " +
                    "title VARCHAR(255) NOT NULL, " +
                    "author VARCHAR(255) NOT NULL, " +
                    "published_date DATE, " +
                    "isbn VARCHAR(20) UNIQUE" +
                    ")");
            stmt.execute("CREATE TABLE readers (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "email VARCHAR(255) UNIQUE NOT NULL" +
                    ")");
        }
    }

    @Test
    public void testReader() throws SQLException {

        dbManager.addReader(new Reader("John Doe", "john.doe@example.com"));
        dbManager.addReader(new Reader("Mark Doe", "mark.doe@example.com"));

        String readerEmail = dbManager.findReaderByEmail("john.doe@example.com").getEmail();
        assertEquals("john.doe@example.com", readerEmail);

        dbManager.getAllReaders();
        assertEquals(2, dbManager.getAllReaders().size());

        dbManager.deleteReader(1);

        dbManager.getAllReaders();
        assertEquals(1, dbManager.getAllReaders().size());
    }

    @Test
    public void testBook() throws SQLException {
        Calendar cal = Calendar.getInstance();
        cal.set(2023, Calendar.OCTOBER, 28);
        Date publishedDate = new Date(cal.getTimeInMillis());

        dbManager.addBook(new Book("Book Title1", "Mark Author", publishedDate, "11111111111111"));
        dbManager.addBook(new Book("Book Title2", "Bark Author", publishedDate, "789"));

        Book book = dbManager.findBookByTitle("Book Title1");
        assertEquals("Book Title1", book.getTitle());
        dbManager.getAllBooks();
        assertEquals(2, dbManager.getAllBooks().size());
        dbManager.deleteBook(1);
        dbManager.getAllBooks();
        assertEquals(1, dbManager.getAllBooks().size());
    }


    // Дополнительные тесты...

    // Метод завершения тестов, чтобы закрывать соединение
    @AfterEach
    public void tearDown() throws SQLException {
        dbManager.disconnect();
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
