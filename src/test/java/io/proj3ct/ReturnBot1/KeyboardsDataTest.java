package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.keybords.DataForDepartment;
import io.proj3ct.ReturnBot1.keybords.KeyboardsData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 Класс тестов для проверки функциональности класса KeyboardsData.
 */
class KeyboardsDataTest {
    private KeyboardsData keyboardsData;
    private DatabaseConnection databaseConnection;
    private DataForDepartment dataForDepartment;
    private Connection connection;
    private PreparedStatement preparedStatement;
    /**
     Метод, который выполняется перед каждым тестом.
     Здесь мы создаем заглушки для зависимостей, необходимых для тестирования.
     */
    @BeforeEach
    public void setUp() throws SQLException {
        keyboardsData = new KeyboardsData();
        databaseConnection = mock(DatabaseConnection.class);
        dataForDepartment = mock(DataForDepartment.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        when(databaseConnection.connect()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }
    /**
     Тестирует метод insertData на корректное добавление данных в базу.
     Проверяет, что метод executeUpdate() вызывается один раз.
     */
    @Test
    public void testInsertData() throws SQLException {
        Long userId = 1L;
        Long newId = 1L;

        when(dataForDepartment.getTextForNewDepartment(userId)).thenReturn("Test Info");
        when(dataForDepartment.getNameForNewDepartment(userId)).thenReturn("Test Name");
        when(dataForDepartment.getInstituteForNewDepartment(userId)).thenReturn("Test Institute");

        keyboardsData.insertData(userId, databaseConnection, newId, dataForDepartment);

        // Проверяем, что executeUpdate был вызван один раз
        verify(preparedStatement, times(1)).executeUpdate();
    }
    /**
     Тестирует метод generateNewId на корректное создание нового уникального идентификатора.
     Проверяет, что возвращаемый идентификатор больше 0.
     */
    @Test
    public void testGenerateNewId() throws SQLException {
        // Настраиваем поведение ResultSet для возврата значений
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("id_depart")).thenReturn("1");

        // Настраиваем поведение для PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Long newId = keyboardsData.generateNewId(databaseConnection);
        assertTrue(newId > 0, "ID должен быть больше 0");
    }
    /**
     Тестирует метод updateData на корректное обновление данных в базе.
     Проверяет, что метод executeUpdate() вызывается один раз.
     */
    @Test
    public void testUpdateData() throws SQLException {
        Long userId = 1L;

        when(dataForDepartment.getTextForEditDepartment(userId)).thenReturn("Updated Info");
        when(dataForDepartment.getNameForEditDepartment(userId)).thenReturn("Updated Name");
        when(dataForDepartment.getNumberForEditDepartment(userId)).thenReturn("1");

        keyboardsData.updateData(userId, databaseConnection, dataForDepartment);

        // Проверяем, что executeUpdate был вызван один раз
        verify(preparedStatement, times(1)).executeUpdate();
    }
    /**
     Тестирует метод deleteData на корректное удаление данных из базы.
     Проверяет, что метод executeUpdate() вызывается один раз.
     */
    @Test
    public void testDeleteData() throws SQLException {
        Long userId = 1L;

        when(dataForDepartment.getNumberForDeleteDepartment(userId)).thenReturn("1");

        keyboardsData.deleteData(userId, databaseConnection, dataForDepartment);

        // Проверяем, что executeUpdate был вызван один раз
        verify(preparedStatement, times(1)).executeUpdate();
    }
}
