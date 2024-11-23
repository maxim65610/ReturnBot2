package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;

import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.dispatch.DispatchData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Тесты для проверки функциональности включения и выключения рассылки сообщений.
 */
public class DispatchOnOffTest {
    private DispatchData dispatchData;
    private DatabaseConnection databaseConnection;
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
     * Тестирует функциональность включения рассылки.
     * Проверяет, что при активации рассылки возвращается правильное сообщение
     * и данные о пользователе с включенной рассылкой.
     *
     * @throws SQLException если возникла ошибка при работе с базой данных
     */
    @Test
    public void dispatchOn() throws SQLException {
        String answerCommandDispatchOn = MessageConstants.DISPATCH_ON_COMMAND_RESPONSE;
        String expectedMessageDispatchOn = """
        Вы подписались на рассылку,
        бот будет отправлять Вам важную информацию""";

        String[][] expectedData = {
                {"1", "on"}
        };

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Один результат
        when(resultSet.getString("id_chat")).thenReturn("1");
        when(resultSet.getString("dispatch")).thenReturn("on");

        String[][] actualData = dispatchData.getUserIdAndDispatchOnOrOff(databaseConnection);

        assertArrayEquals(expectedData, actualData);
        assertEquals(expectedMessageDispatchOn, answerCommandDispatchOn);
    }

    /**
     * Тестирует функциональность выключения рассылки.
     * Проверяет, что при деактивации рассылки возвращается правильное сообщение
     * и данные о пользователе с выключенной рассылкой.
     *
     * @throws SQLException если возникла ошибка при работе с базой данных
     */
    @Test
    public void dispatchOff() throws SQLException {
        String answerCommandDispatchOff = MessageConstants.DISPATCH_OFF_COMMAND_RESPONSE;
        String expectedMessageDispatchOff = "Вы отписались от рассылки";

        String[][] expectedData = {
                {"1", "off"}
        };

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Один результат
        when(resultSet.getString("id_chat")).thenReturn("1");
        when(resultSet.getString("dispatch")).thenReturn("off");

        String[][] actualData = dispatchData.getUserIdAndDispatchOnOrOff(databaseConnection);

        assertArrayEquals(expectedData, actualData);
        assertEquals(expectedMessageDispatchOff, answerCommandDispatchOff);
    }
}
