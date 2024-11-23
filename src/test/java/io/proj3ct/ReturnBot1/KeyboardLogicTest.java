package io.proj3ct.ReturnBot1;


import io.proj3ct.ReturnBot1.keybords.KeyboardLogic;
import org.junit.jupiter.api.BeforeEach;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс для тестирования логики работы клавиатуры в Telegram-боте.
 * Содержит тесты для проверки правильности формирования клавиатур в зависимости от команд,
 * введенных пользователем.
 */
public class KeyboardLogicTest {
    SendMessage message;
    /**
     * Конструктор для TextForMessageTest.
     */
    private void KeyboardLogicTest() {
        message = SendMessage // Create a message object
                .builder()
                .chatId("1234")
                .text("test")
                .build();
    }
    /**
     * Создание объекта для тестов KeyboardLogicTest.
     */
    @BeforeEach
    void setUp() {
        KeyboardLogicTest();
    }
    /**
     * Тест для проверки работы клавиатуры при вводе команды /work.
     * Проверяет, что клавиатура содержит три кнопки.
     */
    @Test
    public void testKeyboardsWorkCommand() {
        KeyboardLogic logic = new KeyboardLogic();

        String data = "/work";
        SendMessage resultMessage = logic.keyboards(message, data);

        InlineKeyboardMarkup markup = (InlineKeyboardMarkup) resultMessage.getReplyMarkup();
        assertEquals(3, markup.getKeyboard().size());
    }

    /**
     * Тест для проверки работы клавиатуры при вводе команды /testAbit.
     * Проверяет, что клавиатура содержит один ряд кнопок.
     */
    @Test
    public void testKeyboardsTestAbitCommand() {
        KeyboardLogic logic = new KeyboardLogic();

        String data = "/test_abit";
        SendMessage resultMessage = logic.keyboards(message, data);

        InlineKeyboardMarkup markup = (InlineKeyboardMarkup) resultMessage.getReplyMarkup();
        assertEquals(1, markup.getKeyboard().size());
    }
}

