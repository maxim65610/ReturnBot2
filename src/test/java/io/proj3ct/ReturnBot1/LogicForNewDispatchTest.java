package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.dispatch.LogicForNewDispatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Тестовый класс для проверки логики и данных нового диспетча.
 *
 * Этот класс содержит тесты для методов класса LogicAndDataForNewDispatch,
 * включая обработку сценариев с неверным паролем и неверным форматом времени.
 */
public class LogicForNewDispatchTest {

    private LogicForNewDispatch logicForNewDispatch;
    private DatabaseConnection databaseConnection;
    private Connection connection;
    /**
     * Метод, который выполняется перед каждым тестом.
     * Он инициализирует моки и создает экземпляр класса LogicAndDataForNewDispatch.
     *
     * @throws SQLException Если возникла ошибка при подключении к базе данных.
     */
    @BeforeEach
    public void setUp() throws SQLException {
        databaseConnection = mock(DatabaseConnection.class);
        logicForNewDispatch = mock(LogicForNewDispatch.class);
        connection = mock(Connection.class);
        when(databaseConnection.connect()).thenReturn(connection);
    }
    /**
     * Тестирует поведение системы при вводе неверного пароля.
     *
     * Проверяет, что при вводе неправильного пароля возвращается соответствующее сообщение об ошибке.
     */
    @Test
    public void testInvalidPassword() {
        Long userId = 1L;
        logicForNewDispatch.worksWithNewDispatch("/new_dispatch", userId);
        when(logicForNewDispatch.worksWithNewDispatch("wrong_password", userId))
                .thenReturn(MessageConstants.BAD_PASSWORD_NEW_DISPATCH);
        String response = logicForNewDispatch.worksWithNewDispatch("wrong_password", userId);
        assertEquals(MessageConstants.BAD_PASSWORD_NEW_DISPATCH, response);
    }
    /**
     * Тестирует поведение системы при вводе неверного формата времени.
     *
     * Проверяет, что при вводе неверного формата даты возвращается соответствующее сообщение об ошибке.
     */
    @Test
    public void testInvalidTimeFormat() {
        Long userId = 1L;

        // Пользователь начинает новый диспетч
        logicForNewDispatch.worksWithNewDispatch("/new_dispatch", userId);
        logicForNewDispatch.worksWithNewDispatch("correct_password", userId);
        logicForNewDispatch.worksWithNewDispatch("Test Text", userId);

        // Настройка поведения для неверного формата даты
        when(logicForNewDispatch.worksWithNewDispatch("invalid_date", userId))
                .thenReturn(MessageConstants.DISPATCH_TIME_BAD);

        String response = logicForNewDispatch.worksWithNewDispatch("invalid_date", userId);
        assertEquals(MessageConstants.DISPATCH_TIME_BAD, response);
    }

}
