package io.proj3ct.ReturnBot1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Класс тестов для проверки логики работы с данными пользователей в классе UsersData.
 */
public class DeliteUserDataTest {
    private final UsersData usersData;
    private final LogicAndDataForRegistrationUsers logicAndDataForRegistrationUsers;
    private final DatabaseConnection databaseConnection;
    private final Connection connection;
    private final PreparedStatement preparedStatement;
    private final TextForMessage textForMessage;

    /**
     * Конструктор для инициализации зависимостей.
     */
    public DeliteUserDataTest() {
        usersData = new UsersData();
        logicAndDataForRegistrationUsers = mock(LogicAndDataForRegistrationUsers.class);
        databaseConnection = mock(DatabaseConnection.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        textForMessage = new TextForMessage();

    }
    /**
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
     * Тест для команды /userDataDell
     * Проверяет функционал и вывод
     */
    @Test
    public void DeliteUserDataTestCommand() throws SQLException{
        String answerCommandUserDataDell = textForMessage.setTheText("/userDataDell");
        String ExpectedMessageDataDell = "Ваши данные успешно удалены";

        Long userId = 1L;
        usersData.deleteData(userId, databaseConnection);
        verify(preparedStatement).setString(1, userId.toString());
        verify(preparedStatement).executeUpdate();
        assertEquals(ExpectedMessageDataDell, answerCommandUserDataDell);


    }


}