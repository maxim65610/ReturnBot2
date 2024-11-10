package io.proj3ct.ReturnBot1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс тестов для проверки логики работы с данными пользователей в классе UsersData.
 */
public class UsersDataTest {
    private final UsersData usersData;
    private final LogicAndDataForRegistrationUsers logicAndDataForRegistrationUsers;
    private final DatabaseConnection databaseConnection;
    private final Connection connection;
    private final PreparedStatement preparedStatement;
    private final ResultSet resultSet;

    /**
     * Конструктор для инициализации зависимостей.
     */
    public UsersDataTest() {
        usersData = new UsersData();
        logicAndDataForRegistrationUsers = mock(LogicAndDataForRegistrationUsers.class);
        databaseConnection = mock(DatabaseConnection.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
    }

    /**
     * Метод, который выполняется перед каждым тестом.
     * Настраивает заглушки для подключения к базе данных и данных пользователя.
     */
    @Before
    public void setUp() throws SQLException {
        when(databaseConnection.connect()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(logicAndDataForRegistrationUsers.getNameUser (anyLong())).thenReturn("qqqq");
        when(logicAndDataForRegistrationUsers.getSurnameUser (anyLong())).thenReturn("wwww");
        when(logicAndDataForRegistrationUsers.getSchoolClassUser (anyLong())).thenReturn("10");
        when(logicAndDataForRegistrationUsers.getMailUser (anyLong())).thenReturn("test@example.com");
    }

    /**
     * Тест для проверки вставки данных пользователя в таблицу.
     * Проверяет, что все параметры установлены корректно и запрос выполнен.
     */
    @Test
    public void testInsertData() throws SQLException {
        Long userId = 1L;
        usersData.insertData(userId, logicAndDataForRegistrationUsers, databaseConnection);
        verify(preparedStatement).setString(1, userId.toString());
        verify(preparedStatement).setString(2, "qqqq");
        verify(preparedStatement).setString(3, "wwww");
        verify(preparedStatement).setString(4, "10");
        verify(preparedStatement).setString(5, "test@example.com");
        verify(preparedStatement).executeUpdate();
    }

    /**
     * Тест для проверки изменения имени пользователя.
     * Проверяет, что имя установлено корректно и запрос выполнен.
     */
    @Test
    public void testChangeName() throws SQLException {
        Long userId = 1L;
        String newName = "NewName";
        usersData.changeName(userId, databaseConnection, newName);
        verify(preparedStatement).setString(1, newName);
        verify(preparedStatement).setString(2, userId.toString());
        verify(preparedStatement).executeUpdate();
    }

    /**
     * Тест для проверки изменения фамилии пользователя.
     * Проверяет, что фамилия установлена корректно и запрос выполнен.
     */
    @Test
    public void testChangeSurname() throws SQLException {
        Long userId = 1L;
        String newSurname = "NewSurname";
        usersData.changeSurname(userId, databaseConnection, newSurname);
        verify(preparedStatement).setString(1, newSurname);
        verify(preparedStatement).setString(2, userId.toString());
        verify(preparedStatement).executeUpdate();
    }

    /**
     * Тест для проверки изменения класса пользователя.
     * Проверяет, что класс установлен корректно и запрос выполнен.
     */
    @Test
    public void testChangeClass() throws SQLException {
        Long userId = 1L;
        String newClass = "11B";
        usersData.changeClass(userId, databaseConnection, newClass);
        verify(preparedStatement).setString(1, newClass);
        verify(preparedStatement).setString(2, userId.toString());
        verify(preparedStatement).executeUpdate();
    }

    /**
     * Тест для проверки изменения почты пользователя.
     * Проверяет, что почта установлена корректно и запрос выполнен.
     */
    @Test
    public void testChangeMail() throws SQLException {
        Long userId = 1L;
        String newMail = "newmail@example.com";
        usersData.changeMail(userId, databaseConnection, newMail);
        verify(preparedStatement).setString(1, newMail);
        verify(preparedStatement).setString(2, userId.toString());
        verify(preparedStatement).executeUpdate();
    }

    /**
     * Тест для проверки существования пользователя в таблице регистрации.
     * Проверяет, что метод возвращает true, если пользователь существует.
     */
    @Test
    public void testCheckUserIdExistsInRegistrationDataTable() throws SQLException {
        Long userId = 1L;
        when(resultSet.next()).thenReturn(true);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        boolean exists = usersData.checkUserIdExistsInRegistrationDataTable(userId, databaseConnection);
        verify(preparedStatement).setString(1, userId.toString());
        assertTrue(exists);
    }

    /**
     * Тест для проверки удаления данных пользователя из таблицы.
     * Проверяет, что запрос на удаление выполнен корректно.
     */
    @Test
    public void testDeleteData() throws SQLException {
        Long userId = 1L;
        usersData.deleteData(userId, databaseConnection);
        verify(preparedStatement).setString(1, userId.toString());
        verify(preparedStatement).executeUpdate();
    }

    /**
     * Тест для получения данных пользователя из таблицы.
     * Проверяет, что данные пользователя возвращаются корректно.
     */
    @Test
    public void testTakeData() throws SQLException {
        Long userId = 1L;
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("name")).thenReturn("qqqq");
        when(resultSet.getString("surname")).thenReturn("wwww");
        when(resultSet.getString("school_сlass")).thenReturn("10");
        when(resultSet.getString("mail")).thenReturn("test@example.com");
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        String data = usersData.takeData(userId, databaseConnection);
        assertEquals("Ваше имя: qqqq\nВаша фамилия: wwww\nВаш класс: 10\nВаша почта: test@example.com", data);
    }

    /**
     * Тест для получения почты пользователя из таблицы.
     * Проверяет, что почта возвращается корректно.
     */
    @Test
    public void testGetUserMail() throws SQLException {
        Long userId = 1L;
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("mail")).thenReturn("test@example.com");
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        String email = usersData.getUserMail(userId, databaseConnection);
        assertEquals("test@example.com", email);
    }
}
