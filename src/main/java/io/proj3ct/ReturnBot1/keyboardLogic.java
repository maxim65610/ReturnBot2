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
            List keyboard = new ArrayList<>();
            List<InlineKeyboardButton> row1 = new ArrayList<>();

            row1.add(new InlineKeyboardButton().builder().text("Математика, Механика и Компьютерные Науки").callbackData("ИЕНИМ").build());
            keyboard.add(row1);

            List<InlineKeyboardButton> row2 = new ArrayList<>();
            row2.add(new InlineKeyboardButton().builder().text("Радиоэлектроника и Информационные Технологии").callbackData("РТФ").build());
            keyboard.add(row2);

            List<InlineKeyboardButton> row3 = new ArrayList<>();
            row3.add(new InlineKeyboardButton().builder().text("Химико Технический").callbackData("ХТИ").build());
            keyboard.add(row3);

            markup.setKeyboard(keyboard);
            message.setReplyMarkup(markup);
        }
        if (key2 == 1) {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

// Создание и добавление кнопок

            List<InlineKeyboardButton> row1 = new ArrayList<>();
            row1.add(new InlineKeyboardButton().builder().text("Компьютерные Науки").callbackData("1").build());
            keyboard.add(row1);

            List<InlineKeyboardButton> row2 = new ArrayList<>();
            row2.add(new InlineKeyboardButton().builder().text("Администрирование Информационных Систем").callbackData("2").build());
            keyboard.add(row2);

            List<InlineKeyboardButton> row3 = new ArrayList<>();
            row3.add(new InlineKeyboardButton().builder().text("Фундаментальная Информатика").callbackData("3").build());
            keyboard.add(row3);

            List<InlineKeyboardButton> row4 = new ArrayList<>();
            row4.add(new InlineKeyboardButton().builder().text("Математика и механика").callbackData("4").build());
            keyboard.add(row4);

            List<InlineKeyboardButton> row5 = new ArrayList<>();
            row5.add(new InlineKeyboardButton().builder().text("Компьютерная Безопасность").callbackData("5").build());
            keyboard.add(row5);

            markup.setKeyboard(keyboard);
            message.setReplyMarkup(markup);
        }

        if (key2 == 2) {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

            List<InlineKeyboardButton> row1 = new ArrayList<>();
            row1.add(new InlineKeyboardButton().builder().text("Информатика и Вычислительная Техника").callbackData("6").build());
            keyboard.add(row1);

            List<InlineKeyboardButton> row2 = new ArrayList<>();
            row2.add(new InlineKeyboardButton().builder().text("Прикладная Информатика").callbackData("7").build());
            keyboard.add(row2);

            List<InlineKeyboardButton> row3 = new ArrayList<>();
            row3.add(new InlineKeyboardButton().builder().text("Алгоритмы Искусственного Интеллекта").callbackData("8").build());
            keyboard.add(row3);

            List<InlineKeyboardButton> row4 = new ArrayList<>();
            row4.add(new InlineKeyboardButton().builder().text("Безопасность Компьютерных Систем").callbackData("9").build());
            keyboard.add(row4);

            List<InlineKeyboardButton> row5 = new ArrayList<>();
            row5.add(new InlineKeyboardButton().builder().text("Радиотехника").callbackData("10").build());
            keyboard.add(row5);

            markup.setKeyboard(keyboard);
            message.setReplyMarkup(markup);
        }

        if (key2 == 3) {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

            List<InlineKeyboardButton> row1 = new ArrayList<>();
            row1.add(new InlineKeyboardButton().builder().text("Биотехнология").callbackData("11").build());
            keyboard.add(row1);

            List<InlineKeyboardButton> row2 = new ArrayList<>();
            row2.add(new InlineKeyboardButton().builder().text("Химическая технология веществ").callbackData("12").build());
            keyboard.add(row2);

            List<InlineKeyboardButton> row3 = new ArrayList<>();
            row3.add(new InlineKeyboardButton().builder().text("Фармация").callbackData("13").build());
            keyboard.add(row3);

            markup.setKeyboard(keyboard);
            message.setReplyMarkup(markup);

        }
        return message;
    }
}
