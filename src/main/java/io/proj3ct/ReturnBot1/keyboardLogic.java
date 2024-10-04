package io.proj3ct.ReturnBot1;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class keyboardLogic {
    public SendMessage keyboards(SendMessage message, int key1,int key2){
        if (key1 == 1) {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

            // Создание и добавление кнопок

            List<InlineKeyboardButton> row = new ArrayList<>();

            row.add(new InlineKeyboardButton().builder().text("ИЕНИМ").callbackData("ИЕНИМ").build());
            row.add(new InlineKeyboardButton().builder().text("РТФ").callbackData("РТФ").build());
            row.add(new InlineKeyboardButton().builder().text("ХТИ").callbackData("ХТИ").build());
            keyboard.add(row);

            markup.setKeyboard(keyboard);
            message.setReplyMarkup(markup);
        }
        if (key2 == 1) {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

            // Создание и добавление кнопок

            List<InlineKeyboardButton> row = new ArrayList<>();

            row.add(new InlineKeyboardButton().builder().text("КН").callbackData("error_msg_text1").build());
            row.add(new InlineKeyboardButton().builder().text("МО").callbackData("error_msg_text1").build());
            row.add(new InlineKeyboardButton().builder().text("ФИИТ").callbackData("error_msg_text1").build());
            row.add(new InlineKeyboardButton().builder().text("МИМ").callbackData("error_msg_text1").build());
            row.add(new InlineKeyboardButton().builder().text("КБ").callbackData("error_msg_text1").build());

            keyboard.add(row);

            markup.setKeyboard(keyboard);
            message.setReplyMarkup(markup);
        }
        if (key2 == 2) {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

            // Создание и добавление кнопок

            List<InlineKeyboardButton> row = new ArrayList<>();

            row.add(new InlineKeyboardButton().builder().text("ИВТ").callbackData("error_msg_text1").build());
            row.add(new InlineKeyboardButton().builder().text("ПИ").callbackData("error_msg_text1").build());
            row.add(new InlineKeyboardButton().builder().text("АИИ").callbackData("error_msg_text1").build());
            row.add(new InlineKeyboardButton().builder().text("БКС").callbackData("error_msg_text1").build());
            row.add(new InlineKeyboardButton().builder().text("РДТ").callbackData("error_msg_text1").build());

            keyboard.add(row);

            markup.setKeyboard(keyboard);
            message.setReplyMarkup(markup);
        }
        if (key2 == 3) {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

            // Создание и добавление кнопок

            List<InlineKeyboardButton> row = new ArrayList<>();

            row.add(new InlineKeyboardButton().builder().text("БТХ").callbackData("error_msg_text1").build());
            row.add(new InlineKeyboardButton().builder().text("ХТП").callbackData("error_msg_text1").build());
            row.add(new InlineKeyboardButton().builder().text("ФАРМ").callbackData("error_msg_text1").build());

            keyboard.add(row);

            markup.setKeyboard(keyboard);
            message.setReplyMarkup(markup);
        }

        return message;
    }
}
