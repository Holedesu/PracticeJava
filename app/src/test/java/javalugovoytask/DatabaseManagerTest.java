package javalugovoytask;
import javalugovoytask.dto.Book;
import javalugovoytask.dto.Reader;


import java.sql.Date;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseManagerTest {

    @Test
    public void testAddBook() throws SQLException {
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.connect();

        // Тестируем добавление книги
        dbManager.addBook(new Book("1984", "George Orwell", new Date(System.currentTimeMillis()), "123-456-789"));

        // Например, проверяем, что книга добавлена успешно
        String bookTitle = dbManager.findBookByTitle("1984").getTitle();
        assertEquals("1984", bookTitle);

        dbManager.disconnect();
    }
}
