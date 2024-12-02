package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.datebase.DatebaseTables;
import io.proj3ct.ReturnBot1.testAbit.RetrieveData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Тестовый класс для проверки функциональности класса RetrieveData.
 */
public class RetrieveDataTest {

    private RetrieveData retrieveData;
    private DatabaseConnection mockDatabaseConnection;
    private DatebaseTables mockDatebaseTables;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    /**
     * Метод, который выполняется перед каждым тестом.
     * Здесь мы инициализируем моки и создаем экземпляр RetrieveData.
     */
    @BeforeEach
    public void setUp() throws SQLException {
        mockDatabaseConnection = Mockito.mock(DatabaseConnection.class);
        mockDatebaseTables = Mockito.mock(DatebaseTables.class);
        mockConnection = Mockito.mock(Connection.class);
        mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        mockResultSet = Mockito.mock(ResultSet.class);

        // Настройка моков
        when(mockDatabaseConnection.connect()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Используем конструктор для создания экземпляра RetrieveData
        retrieveData = new RetrieveData(mockDatabaseConnection, mockDatebaseTables);
    }
    /**
     * Тест, который проверяет, что метод getDataById возвращает правильный ответ,
     * когда данные успешно извлечены из базы данных.
     */
    @Test
    public void testGetDataByIdReturnsCorrectAnswer() throws SQLException {
        int questionId = 1;
        String expectedAnswer = "Answer 1";

        // Настройка поведения моков
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("answer1")).thenReturn(expectedAnswer);

        String actualAnswer = retrieveData.getDataById(questionId, "answer1");

        assertEquals(expectedAnswer, actualAnswer);
        verify(mockPreparedStatement).setInt(1, questionId);
    }
    /**
     * Тест, который проверяет, что метод getDataById возвращает пустую строку,
     * когда данные не найдены для указанного идентификатора вопроса.
     */
    @Test
    public void testGetDataByIdReturnsEmptyStringWhenNoDataFound() throws SQLException {
        int questionId = 2;

        // Настройка поведения моков
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        String actualAnswer = retrieveData.getDataById(questionId, "answer1");

        assertEquals("", actualAnswer);
        verify(mockPreparedStatement).setInt(1, questionId);
    }
    /**
     * Тест, который проверяет, что метод getDataById корректно обрабатывает
     * SQLException и возвращает пустую строку в случае ошибки извлечения данных.
     */
    @Test
    public void testGetDataByIdHandlesSQLException() throws SQLException {
        int questionId = 3;

        // Настройка поведения моков
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        String actualAnswer = retrieveData.getDataById(questionId, "answer1");

        assertEquals("", actualAnswer);
        verify(mockPreparedStatement).setInt(1, questionId);
    }
}