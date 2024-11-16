package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.baseClasses.LogicController;
import io.proj3ct.ReturnBot1.baseClasses.TextForMessage;
import io.proj3ct.ReturnBot1.departmentsAndTest.LogicForTestABI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;


import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Тестовый класс для проверки функциональности LogicСontroller.
 */
public class LogicControllerTest {
    private final LogicController logicController;
    private final LogicForTestABI mockLogicForTestABI;
    private final TextForMessage mockTextForMessage;

    /**
     * Конструктор дял LogicControllerTest.
     */
    private LogicControllerTest() {
        mockLogicForTestABI = Mockito.mock(LogicForTestABI.class);
        mockTextForMessage = Mockito.mock(TextForMessage.class);
        logicController = new LogicController();
    }
    /**
     * Устанавливает моки для полей с помощью рефлексии.
     */
    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Заменяем логику контроллера на мок
        Field logicForTestABIField = LogicController.class.getDeclaredField("logicForTestABI");
        logicForTestABIField.setAccessible(true);
        logicForTestABIField.set(logicController, mockLogicForTestABI);

        Field textForMessageField = LogicController.class.getDeclaredField("textForMessage");
        textForMessageField.setAccessible(true);
        textForMessageField.set(logicController, mockTextForMessage);
    }

    /**
     * Проверяет, что метод возвращает ожидаемый список строк
     * на основании данных, полученных из мока.
     */
    @Test
    public void testGetListStringWithTextToSendAndOptionForKeyboard_WithCallbackQuery() {
        Update update = Mockito.mock(Update.class);
        CallbackQuery callbackQuery = Mockito.mock(CallbackQuery.class);
        User user = Mockito.mock(User.class);

        when(update.hasCallbackQuery()).thenReturn(true);
        when(update.getCallbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.getFrom()).thenReturn(user);
        when(user.getId()).thenReturn(12345L);

        when(mockLogicForTestABI.getUserStatesForTest(12345L)).thenReturn("awaiting_testABI_1");
        when(mockLogicForTestABI.getDataBd("", 12345L, "someData")).thenReturn(List.of("response"));
        when(callbackQuery.getData()).thenReturn("someData");

        List<String> result = logicController.handleMessage(12345L, "someData",true);

        assertEquals(List.of("response"), result);
        verify(mockLogicForTestABI).getDataBd("", 12345L, "someData");
    }

    /**
     * Проверяет, что метод возвращает ожидаемый список строк
     * на основании данных, полученных из мока.
     */
    @Test
    public void testGetListStringWithTextToSendAndOptionForKeyboard_WithMessage() {
        Update update = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(messageMock);
        when(messageMock.getText()).thenReturn("/testAbit");

        when(mockTextForMessage.setTheText("/testAbit")).thenReturn("Тест начат");

        long userId = 0L;
        when(mockLogicForTestABI.getDataBd("/testAbit", userId, "100"))
                .thenReturn(List.of("Тест начат", "/testAbit"));

        List<String> result = logicController.handleMessage(0L, "/testAbit",false);

        assertEquals(List.of("Тест начат", "/testAbit"), result);
        verify(mockLogicForTestABI).getDataBd("/testAbit", userId, "100");
    }
}

