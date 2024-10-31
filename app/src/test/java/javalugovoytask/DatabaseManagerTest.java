package javalugovoytask;
import javalugovoytask.dto.Book;
import javalugovoytask.dto.Reader;


import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

        dbManager.deleteReader(1);

        dbManager.getAllReaders();
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
