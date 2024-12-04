package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.dispatch.DispatchData;
import io.proj3ct.ReturnBot1.dispatch.LogicForOnOffDispatch;
import io.proj3ct.ReturnBot1.registration.UsersData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
/**
 * Тестовый для проверки LogicForOnOffDispatch.
 */
public class LogicForOnOffDispatchTest {
    private LogicForOnOffDispatch logicForOnOffDispatch;
    private DatabaseConnection mockDatabaseConnection;
    private UsersData mockUsersData;
    private DispatchData mockDispatchData;

    /**
     * Метод, выполняемый перед каждым тестом.
     * Здесь создаются мок-объекты и экземпляр класса LogicForOnOffDispatch.
     */
    @BeforeEach
    public void setUp() {
        mockDatabaseConnection = Mockito.mock(DatabaseConnection.class);
        mockUsersData = Mockito.mock(UsersData.class);
        mockDispatchData = Mockito.mock(DispatchData.class);

        // Создаем экземпляр класса с замокированными зависимостями
        logicForOnOffDispatch = new LogicForOnOffDispatch(mockDatabaseConnection, mockUsersData, mockDispatchData);
    }
    /**
     * Тест для проверки поведения метода dispatchOn,
     * когда пользователь не зарегистрирован.
     */
    @Test
    public void testDispatchOnUserNotRegistered() {
        Long userId = 1L;
        when(mockUsersData.checkUserIdExistsInRegistrationDataTable(userId, mockDatabaseConnection)).thenReturn(false);

        String response = logicForOnOffDispatch.dispatchOn(userId);

        assertEquals("Эта функция недоступна, пока вы не зарегистрируетесь", response);
        verify(mockUsersData, times(1)).checkUserIdExistsInRegistrationDataTable(userId, mockDatabaseConnection);
        verify(mockUsersData, never()).changeDispatchStatusOn(anyLong(), any());
    }
    /**
     * Тест для проверки поведения метода dispatchOn,
     * когда пользователь зарегистрирован.
     */
    @Test
    public void testDispatchOnUserRegistered() {
        Long userId = 1L;
        when(mockUsersData.checkUserIdExistsInRegistrationDataTable(userId, mockDatabaseConnection)).thenReturn(true);

        String response = logicForOnOffDispatch.dispatchOn(userId);

        assertEquals(MessageConstants.DISPATCH_ON_COMMAND_RESPONSE, response);
        verify(mockUsersData, times(1)).changeDispatchStatusOn(userId, mockDatabaseConnection);
    }
    /**
     * Тест для проверки поведения метода dispatchOff,
     * когда пользователь не зарегистрирован.
     */
    @Test
    public void testDispatchOffUserNotRegistered() {
        Long userId = 1L;
        when(mockUsersData.checkUserIdExistsInRegistrationDataTable(userId, mockDatabaseConnection)).thenReturn(false);

        String response = logicForOnOffDispatch.dispatchOff(userId);

        assertEquals("Эта функция недоступна, пока вы не зарегистрируетесь", response);
        verify(mockUsersData, times(1)).checkUserIdExistsInRegistrationDataTable(userId, mockDatabaseConnection);
        verify(mockUsersData, never()).changeDispatchStatusOff(anyLong(), any());
    }
    /**
     * Тест для проверки поведения метода dispatchOff,
     * когда пользователь зарегистрирован.
     */
    @Test
    public void testDispatchOffUserRegistered() {
        Long userId = 1L;
        when(mockUsersData.checkUserIdExistsInRegistrationDataTable(userId, mockDatabaseConnection)).thenReturn(true);

        String response = logicForOnOffDispatch.dispatchOff(userId);

        assertEquals(MessageConstants.DISPATCH_OFF_COMMAND_RESPONSE, response);
        verify(mockUsersData, times(1)).changeDispatchStatusOff(userId, mockDatabaseConnection);
    }
}
