package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.baseClasses.TextForMessage;
import io.proj3ct.ReturnBot1.dispatch.LogicAndDataForDispatch;
import io.proj3ct.ReturnBot1.registration.UsersData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Тестовый класс для проверки логики и данных, связанных с подписками на рассылку.
 */
public class LogicAndDataForDispatchTest {
    private LogicAndDataForDispatch logicAndDataForDispatch;
    private DatabaseConnection databaseConnection;
    private UsersData usersData;
    private TextForMessage textForMessage;

    /**
     * Метод, выполняемый перед каждым тестом.
     * Здесь создаются моки и инициализируется объект LogicAndDataForDispatch.
     */
    @BeforeEach
    public void setUp() {
        databaseConnection = mock(DatabaseConnection.class);
        usersData = mock(UsersData.class);
        textForMessage = mock(TextForMessage.class);

        logicAndDataForDispatch = new LogicAndDataForDispatch(databaseConnection, usersData, textForMessage);
    }

    /**
     * Тестирует поведение метода dispatchOn, когда пользователь не зарегистрирован.
     */
    @Test
    public void testDispatchOnUserNotRegistered() {
        Long userId = 1L;
        when(usersData.checkUserIdExistsInRegistrationDataTable(userId, databaseConnection)).thenReturn(false);
        String result = logicAndDataForDispatch.dispatchOn(userId);
        assertEquals("Эта функция недоступна, пока вы не зарегистрируетесь", result);
    }

    /**
     * Тестирует поведение метода dispatchOn, когда пользователь зарегистрирован.
     */
    @Test
    public void testDispatchOnUserRegistered() {
        Long userId = 1L;
        when(usersData.checkUserIdExistsInRegistrationDataTable(userId, databaseConnection)).thenReturn(true);
        when(textForMessage.setTheText("/dispatchOn")).thenReturn("Вы подписались на рассылку, бот будет отправлять Вам важную информацию");

        String result = logicAndDataForDispatch.dispatchOn(userId);
        assertEquals("Вы подписались на рассылку, бот будет отправлять Вам важную информацию", result);
        verify(usersData).changeDispatchStatusOn(userId, databaseConnection);
    }

    /**
     * Тестирует поведение метода dispatchOff, когда пользователь не зарегистрирован.
     */
    @Test
    public void testDispatchOffUserNotRegistered() {
        Long userId = 1L;
        when(usersData.checkUserIdExistsInRegistrationDataTable(userId, databaseConnection)).thenReturn(false);
        String result = logicAndDataForDispatch.dispatchOff(userId);
        assertEquals("Эта функция недоступна, пока вы не зарегистрируетесь", result);
    }

    /**
     * Тестирует поведение метода dispatchOff, когда пользователь зарегистрирован.
     */
    @Test
    public void testDispatchOffUserRegistered() {
        Long userId = 1L;
        when(usersData.checkUserIdExistsInRegistrationDataTable(userId, databaseConnection)).thenReturn(true);
        when(textForMessage.setTheText("/dispatchOff")).thenReturn("Вы отписались от рассылки");

        String result = logicAndDataForDispatch.dispatchOff(userId);
        assertEquals("Вы отписались от рассылки", result);
        verify(usersData).changeDispatchStatusOff(userId, databaseConnection);
    }
}
