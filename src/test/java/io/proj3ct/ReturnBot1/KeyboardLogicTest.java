package io.proj3ct.ReturnBot1;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeyboardLogicTest {



        @Test
        public void testKeyboardsWorkCommand() {
            keyboardLogic logic = new keyboardLogic();
            SendMessage message = new SendMessage();

            String data = "/work";
            SendMessage resultMessage = logic.keyboards(message, data);

            InlineKeyboardMarkup markup = (InlineKeyboardMarkup) resultMessage.getReplyMarkup();
            assertEquals(3, markup.getKeyboard().size());
        }

        @Test
        public void testKeyboardsIENIMCommand() {
            keyboardLogic logic = new keyboardLogic();
            SendMessage message = new SendMessage();

            String data = "ИЕНИМ";
            SendMessage resultMessage = logic.keyboards(message, data);

            InlineKeyboardMarkup markup = (InlineKeyboardMarkup) resultMessage.getReplyMarkup();
            assertEquals(5, markup.getKeyboard().size());
        }
    @Test
    public void testKeyboardsRTFCommand() {
        keyboardLogic logic = new keyboardLogic();
        SendMessage message = new SendMessage();

        String data = "РТФ";
        SendMessage resultMessage = logic.keyboards(message, data);

        InlineKeyboardMarkup markup = (InlineKeyboardMarkup) resultMessage.getReplyMarkup();
        assertEquals(5, markup.getKeyboard().size());
    }

    @Test
    public void testKeyboardsHTICommand() {
        keyboardLogic logic = new keyboardLogic();
        SendMessage message = new SendMessage();

        String data = "ХТИ";
        SendMessage resultMessage = logic.keyboards(message, data);

        InlineKeyboardMarkup markup = (InlineKeyboardMarkup) resultMessage.getReplyMarkup();
        assertEquals(3, markup.getKeyboard().size());
    }
}
