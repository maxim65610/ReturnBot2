package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.dispatch.DispatchData;
import io.proj3ct.ReturnBot1.dispatch.DispatchDataStorage;
import io.proj3ct.ReturnBot1.dispatch.LogicForNewDispatch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Тест для проверки функциональности DispatchData.
 */
public class DispatchDataTest {

    private DispatchData dispatchData;
    private DatabaseConnection databaseConnection;
    private LogicForNewDispatch logicForNewDispatch;
    private DispatchDataStorage dispatchDataStorage;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    /**
     * Выполняется перед каждым тестом.
     * Настраивает моки и инициализирует необходимые объекты.
     *
     * @throws SQLException если возникла ошибка при работе с базой данных
     */
    @BeforeEach
    public void setUp() throws SQLException {
        dispatchData = new DispatchData();
        databaseConnection = mock(DatabaseConnection.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        when(databaseConnection.connect()).thenReturn(connection);
    }
    /**
     * Тестирует метод insertData класса DispatchData.
     * Проверяет, что данные корректно вставляются в базу данных.
     *
     * @throws SQLException если возникла ошибка при работе с базой данных
     */
    @Test

    public void testInsertData() throws SQLException {
        Long userId = 1L;

        // Создаем мок для DispatchDataStorage
        dispatchDataStorage = mock(DispatchDataStorage.class);

        // Подготовка ожидаемых значений
        when(dispatchDataStorage.getDispatchText(userId)).thenReturn("Test Text");
        when(dispatchDataStorage.getDispatchTime(userId)).thenReturn("01.01.2025");
        when(dispatchDataStorage.getDispatchCategory(userId)).thenReturn("Test Category");
        when(dispatchDataStorage.getDispatchDepartment(userId)).thenReturn("Test Department");

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Вызов метода для вставки данных
        dispatchData.insertData(userId, dispatchDataStorage, databaseConnection, userId);

        // Проверка вызовов методов
        verify(preparedStatement).setLong(1, userId);
        verify(preparedStatement).setString(2, "Test Text");
        verify(preparedStatement).setString(3, "01.01.2025");
        verify(preparedStatement).setString(4, "Test Category");
        verify(preparedStatement).setString(5, "Test Department");
        verify(preparedStatement).executeUpdate();
    }

    /**
     * Тестирует метод generateNewId класса DispatchData.
     * Проверяет, что метод корректно генерирует новый ID.
     *
     * @throws SQLException если возникла ошибка при работе с базой данных
     */
    @Test
    public void testGenerateNewId() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("maxId")).thenReturn(5L);


        Long newId = dispatchData.generateNewId(databaseConnection);

        assertEquals(6L, newId);
    }
    /**
     * Тестирует метод getAllDispatchData класса DispatchData.
     * Проверяет, что метод корректно получает все данные о диспетчах.
     *
     * @throws SQLException если возникла ошибка при работе с базой данных
     */
    @Test
    public void testGetAllDispatchData() throws SQLException {
        String[][] expectedData = {
                {"1", "Test Text", "01.01.2025", "Test Category", "Test Department"}
        };

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Один результат
        when(resultSet.getString("id")).thenReturn("1");
        when(resultSet.getString("text")).thenReturn("Test Text");
        when(resultSet.getString("time")).thenReturn("01.01.2025");
        when(resultSet.getString("category")).thenReturn("Test Category");
        when(resultSet.getString("department")).thenReturn("Test Department");

        String[][] actualData = dispatchData.getAllDispatchData(databaseConnection);

        assertArrayEquals(expectedData, actualData);
    }
    /**
     * Тестирует метод getUser YearEndSchool класса DispatchData.
     * Проверяет, что метод корректно получает год окончания школы для пользователя.
     *
     * @throws SQLException если возникла ошибка при работе с базой данных
     */
    @Test
    public void testGetUserYearEndSchool() throws SQLException {
        Long userId = 1L;
        String expectedYear = "2025";


        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("year_end_school")).thenReturn(expectedYear);

        String actualYear = dispatchData.getUserYearEndSchool(userId, databaseConnection);

        assertEquals(expectedYear, actualYear);
    }
    /**
     * Тестирует метод getUser ResultTest класса DispatchData.
     * Проверяет, что метод корректно получает результат теста для пользователя.
     *
     * @throws SQLException если возникла ошибка при работе с базой данных
     */
    @Test
    public void testGetUserResultTest() throws SQLException {
        Long userId = 1L;
        String expectedResult = "Test Department";

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("department")).thenReturn(expectedResult);

        String actualResult = dispatchData.getUserResultTest(userId, databaseConnection);

        assertEquals(expectedResult, actualResult);
    }
}
