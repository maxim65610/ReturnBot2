package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.keybords.KeyboardsData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

/**
 * Тестовый класс для проверки функциональности класса KeyboardsData.
 * Этот класс использует Mockito для создания моков объектов,
 * чтобы протестировать методы вставки, генерации нового ID, обновления и удаления данных.
 */
public class KeyboardsDataTest {
    private KeyboardsData keyboardsData;
    private DatabaseConnection mockDatabaseConnection;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    /**
     * Метод, выполняемый перед каждым тестом.
     * Создает экземпляры необходимых классов и настраивает моки.
     */
    @BeforeEach
    public void setUp() throws SQLException {
        keyboardsData = new KeyboardsData();
        mockDatabaseConnection = Mockito.mock(DatabaseConnection.class);
        mockConnection = Mockito.mock(Connection.class);
        mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        mockResultSet = Mockito.mock(ResultSet.class);

        // Настройка моков
        when(mockDatabaseConnection.connect()).thenReturn(mockConnection);
    }
    /**
     * Тестирует метод insertData класса KeyboardsData.
     * Проверяет, что параметры устанавливаются правильно и вызывается метод executeUpdate.
     */
    @Test
    public void testInsertData() throws SQLException {
        // Настройка
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Вызов метода
        keyboardsData.insertData(1L, mockDatabaseConnection, 1L);

        // Проверка
        verify(mockPreparedStatement).setLong(1, 1L);
        verify(mockPreparedStatement).setString(2, "1");
        verify(mockPreparedStatement).setString(3, "1");
        verify(mockPreparedStatement).setString(4, "1");
        verify(mockPreparedStatement).executeUpdate();
    }
    /**
     * Тестирует метод generateNewId класса KeyboardsData.
     * Проверяет, что новый ID генерируется правильно на основе максимального ID в базе данных.
     */
    @Test
    public void testGenerateNewId() throws SQLException {
        // Настройка
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong("maxId")).thenReturn(5L);

        // Вызов метода
        Long newId = keyboardsData.generateNewId(mockDatabaseConnection);

        // Проверка
        verify(mockPreparedStatement).executeQuery();
        assert(newId).equals(6L); // 5 + 1
    }
    /**
     * Тестирует метод updateData класса KeyboardsData.
     * Проверяет, что параметры устанавливаются правильно и вызывается метод executeUpdate.
     */
    @Test
    public void testUpdateData() throws SQLException {
        // Настройка
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Вызов метода
        keyboardsData.updateData(1L, mockDatabaseConnection);

        // Проверка
        verify(mockPreparedStatement).setString(1, "1");
        verify(mockPreparedStatement).setString(2, "1");
        verify(mockPreparedStatement).setString(3, "1");
        verify(mockPreparedStatement).setLong(4, 1L);
        verify(mockPreparedStatement).executeUpdate();
    }
    /**
     * Тестирует метод deleteData класса KeyboardsData.
     * Проверяет, что параметр устанавливается правильно и вызывается метод executeUpdate.
     */
    @Test
    public void testDeleteData() throws SQLException {
        // Настройка
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Вызов метода
        keyboardsData.deleteData(1L, mockDatabaseConnection);

        // Проверка
        verify(mockPreparedStatement).setLong(1, 1L);
        verify(mockPreparedStatement).executeUpdate();
    }
}
