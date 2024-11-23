package io.proj3ct.ReturnBot1;


import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.datebase.DatebaseTables;
import io.proj3ct.ReturnBot1.keybords.DepartmentsInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тестовый класс для проверки функциональности класса DepartmentsInfo.
 * Этот класс содержит тесты для методов извлечения информации о факультетах,
 * подсчета количества факультетов в институте и получения идентификаторов и названий факультетов.
 */
public class DepartmentsInfoTest {
    private DepartmentsInfo departmentsInfo;
    private DatebaseTables mockDatebaseTables;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    /**
     * Выполняется перед каждым тестом.
     * Здесь создаются моки для всех зависимостей и настраивается их поведение.
     */
    @BeforeEach
    public void setUp() throws SQLException {
        DatabaseConnection mockDatabaseConnection = mock(DatabaseConnection.class);
        mockDatebaseTables = mock(DatebaseTables.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockDatabaseConnection.connect()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        departmentsInfo = new DepartmentsInfo();
        departmentsInfo = new DepartmentsInfo(mockDatabaseConnection, mockDatebaseTables);
    }
    /**
     * Тестирует метод extract, когда факультет существует в базе данных.
     * Ожидается, что метод вернет информацию о факультете.
     */
    @Test
    public void testExtract_FacultyExists() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("info")).thenReturn("Faculty Info");

        String result = departmentsInfo.extract("1");
        assertEquals("Faculty Info", result);
    }
    /**
     * Тестирует метод extract, когда факультет не найден в базе данных.
     * Ожидается, что метод вернет null.
     */
    @Test
    public void testExtract_FacultyDoesNotExist() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        String result = departmentsInfo.extract("1");
        assertNull(result);
    }
    /**
     * Тестирует метод countOfInst, проверяя, что он правильно считает количество факультетов в институте.
     * Ожидается, что метод вернет количество факультетов.
     */
    @Test
    public void testCountOfInst() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("inst")).thenReturn("1").thenReturn("1");

        Integer count = departmentsInfo.countOfInst("1");
        assertEquals(2, count);
    }
    /**
     * Тестирует метод getIdDeparts, проверяя, что он возвращает массив идентификаторов факультетов в обратном порядке.
     * Ожидается, что массив будет перевернут.
     */
    @Test
    public void testGetIdDeparts() throws SQLException {
        List<String> ids = new ArrayList<>();
        ids.add("1");
        ids.add("2");

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("id_depart")).thenReturn("1").thenReturn("2");

        String[] result = departmentsInfo.getIdDeparts("1");
        assertArrayEquals(new String[]{"2", "1"}, result); // Проверяем, что массив перевернут
    }
    /**
     * Тестирует метод getNames, проверяя, что он возвращает массив названий факультетов в обратном порядке.
     * Ожидается, что массив будет перевернут.
     */
    @Test
    public void testGetNames() throws SQLException {
        List<String> names = new ArrayList<>();
        names.add("Math");
        names.add("Physics");

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("name")).thenReturn("Math").thenReturn("Physics");

        String[] result = departmentsInfo.getNames("1");
        assertArrayEquals(new String[]{"Physics", "Math"}, result); // Проверяем, что массив перевернут
    }
}
