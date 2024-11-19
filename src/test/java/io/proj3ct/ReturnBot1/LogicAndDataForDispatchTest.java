package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.baseClasses.TextForMessage;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.datebase.DatebaseTables;
import io.proj3ct.ReturnBot1.dispatch.DispatchData;
import io.proj3ct.ReturnBot1.dispatch.LogicAndDataForDispatch;
import io.proj3ct.ReturnBot1.registration.UsersData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/**
 * Тестовый класс для проверки логики работы класса.
 * Включает тесты для различных методов, таких как активация функции диспетчеризации,
 * проверка даты для диспетча и обработки состояний при создании нового диспетча.
 */
public class LogicAndDataForDispatchTest {

    @Mock
    private DatabaseConnection mockDatabaseConnection;
    @Mock
    private TextForMessage mockTextForMessage;
    @Mock
    private DispatchData mockDispatchData;
    @Mock
    private UsersData mockUsersData;
    @Mock
    private DatebaseTables mockDatebaseTables;

    private LogicAndDataForDispatch logicAndDataForDispatch;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Создаем объект с моками
        logicAndDataForDispatch = new LogicAndDataForDispatch(mockDatabaseConnection, mockTextForMessage,
                mockDispatchData, mockUsersData, mockDatebaseTables);
    }
    /**
     * Тестирует поведение метода LogicAndDataForDispatch.dispatchOn(Long) в случае, когда пользователь не зарегистрирован.
     * Проверяется, что возвращается правильное сообщение, если пользователь не зарегистрирован.
     */
    @Test
    public void testDispatchOn_userNotRegistered() {
        // Arrange
        Long userId = 123L;
        when(mockUsersData.checkUserIdExistsInRegistrationDataTable(userId, mockDatabaseConnection))
                .thenReturn(false);

        // Act
        String result = logicAndDataForDispatch.dispatchOn(userId);

        // Assert
        assertEquals("Эта функция недоступна, пока вы не зарегистрируетесь", result);
    }
    /**
     * Тестирует поведение метода LogicAndDataForDispatch.dispatchOn(Long) в случае, когда пользователь зарегистрирован.
     * Проверяется, что статус пользователя меняется, а также возвращается правильное сообщение.
     */
    @Test
    public void testDispatchOn_userRegistered() {
        // Arrange
        Long userId = 123L;
        when(mockUsersData.checkUserIdExistsInRegistrationDataTable(userId, mockDatabaseConnection))
                .thenReturn(true);
        when(mockTextForMessage.setTheText("/dispatchOn")).thenReturn("Dispatch enabled");

        // Act
        String result = logicAndDataForDispatch.dispatchOn(userId);

        // Assert
        verify(mockUsersData).changeDispatchStatusOn(userId, mockDatabaseConnection);
        assertEquals("Dispatch enabled", result);
    }
    /**
     * Тестирует метод LogicAndDataForDispatch.checkDateForDispatch() на отсутствие диспетчей на текущую дату.
     * Проверяется, что если в базе данных нет диспетчей на сегодня, возвращается пустой результат.
     */
    @Test
    public void testCheckDateForDispatch_noDispatchForToday() {
        String[][] dispatchDataFromDb = {
                {"1", "Text", "18.11.2024", "category", "department"},
                {"2", "Text", "17.11.2024", "category", "department"}
        };
        when(mockDispatchData.getAllDispatchData(mockDatabaseConnection)).thenReturn(dispatchDataFromDb);

        String[][] result = logicAndDataForDispatch.checkDateForDispatch();

        assertEquals(0, result.length);
    }
    /**
     * Тестирует метод LogicAndDataForDispatch.checkDateForDispatch() на наличие диспетчей на текущую дату.
     * Проверяется, что если в базе данных есть диспетчи на сегодня, возвращается правильный результат.
     */
    @Test
    public void testCheckDateForDispatch_dispatchForToday() {
        String[][] dispatchDataFromDb = {
                {"1", "Text", "19.11.2024", "category", "department"},
                {"2", "Text", "19.11.2024", "category", "department"}
        };
        when(mockDispatchData.getAllDispatchData(mockDatabaseConnection)).thenReturn(dispatchDataFromDb);
        String[][] registrationData = {
                {"1", "True"},
                {"2", "True"}
        };
        when(mockDispatchData.getUserIdAndDispatchOnOrOff(mockDatabaseConnection)).thenReturn(registrationData);

        when(mockDispatchData.getUserYearEndSchool(anyLong(), eq(mockDatabaseConnection))).thenReturn("2024");
        when(mockDispatchData.getUserResultTest(anyLong(), eq(mockDatabaseConnection))).thenReturn("-");


        String[][] result = logicAndDataForDispatch.checkDateForDispatch();

        assertEquals(4, result.length);
        assertEquals("1", result[0][0]);
        assertEquals("Text", result[0][1]);
    }

    /**
     * Тестирует метод LogicAndDataForDispatch.worksWithNewDispatch(String, Long) в случае, когда пользователь находится в состоянии нового диспетча.
     * Проверяется, что при вводе команды "/newDispatсh" состояние пользователя изменяется, и возвращается правильный текст.
     */
    @Test
    public void testWorksWithNewDispatch_NewDispatchState() {
        Long userId = 1L;
        String messageText = "/newDispatсh";
        when(mockTextForMessage.setTheText("newDispatсh")).thenReturn("newDispatсh");

        String result = logicAndDataForDispatch.worksWithNewDispatch(messageText, userId);

        assertEquals("newDispatсh", result);
        assertEquals("awaiting_dispatchPassword", logicAndDataForDispatch.getUserStatesForNewDispatch(userId));
    }
}
