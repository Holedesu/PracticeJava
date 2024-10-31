package javalugovoytask.dao;

import javalugovoytask.dto.Reader;
import java.sql.SQLException;
import java.util.List;

public interface ReaderDAO {
    void addReader(Reader reader) throws SQLException;
    List<Reader> getAllReaders() throws SQLException;
    Reader findReaderByEmail(String email) throws SQLException;
    void deleteReader(int id) throws SQLException;
}

