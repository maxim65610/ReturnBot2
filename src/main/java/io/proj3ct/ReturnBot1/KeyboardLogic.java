package io.proj3ct.ReturnBot1;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
/**
 * Класс для контроля над клавиатурами
 */
public class KeyboardLogic {
    /**
     * Метод, который обрабатывает клавиатуры отправляющиеся вместе с сообщением
     */
    public SendMessage keyboards(SendMessage message, String data){

        if (data.equals("/work")) {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List keyboard = new ArrayList<>();
            List<InlineKeyboardButton> row1 = new ArrayList<>();
            row1.add(new InlineKeyboardButton().builder().text("Математика, Механика и Компьютерные Науки").
                    callbackData("ИЕНИМ").build());
            keyboard.add(row1);

            List<InlineKeyboardButton> row2 = new ArrayList<>();
            row2.add(new InlineKeyboardButton().builder().text("Радиоэлектроника и Информационные Технологии").
                    callbackData("РТФ").build());
            keyboard.add(row2);

            List<InlineKeyboardButton> row3 = new ArrayList<>();
            row3.add(new InlineKeyboardButton().builder().text("Химико Технический").callbackData("ХТИ").build());
            keyboard.add(row3);

            markup.setKeyboard(keyboard);
            message.setReplyMarkup(markup);
        }
        if (data.equals("ИЕНИМ")) {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();


            List<InlineKeyboardButton> row1 = new ArrayList<>();
            row1.add(new InlineKeyboardButton().builder().text("Компьютерные Науки").callbackData("1").build());
            keyboard.add(row1);

            List<InlineKeyboardButton> row2 = new ArrayList<>();
            row2.add(new InlineKeyboardButton().builder().text("Администрирование Информационных Систем").
                    callbackData("2").build());
            keyboard.add(row2);

            List<InlineKeyboardButton> row3 = new ArrayList<>();
            row3.add(new InlineKeyboardButton().builder().text("Фундаментальная Информатика").
                    callbackData("3").build());
            keyboard.add(row3);

            List<InlineKeyboardButton> row4 = new ArrayList<>();
            row4.add(new InlineKeyboardButton().builder().text("Математика и механика").
                    callbackData("4").build());
            keyboard.add(row4);

            List<InlineKeyboardButton> row5 = new ArrayList<>();
            row5.add(new InlineKeyboardButton().builder().text("Компьютерная Безопасность").
                    callbackData("5").build());
            keyboard.add(row5);

            markup.setKeyboard(keyboard);
            message.setReplyMarkup(markup);
        }

        if (data.equals("РТФ")) {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

            List<InlineKeyboardButton> row1 = new ArrayList<>();
            row1.add(new InlineKeyboardButton().builder().text("Информатика и Вычислительная Техника").
                    callbackData("6").build());
            keyboard.add(row1);

            List<InlineKeyboardButton> row2 = new ArrayList<>();
            row2.add(new InlineKeyboardButton().builder().text("Прикладная Информатика").
                    callbackData("7").build());
            keyboard.add(row2);

            List<InlineKeyboardButton> row3 = new ArrayList<>();
            row3.add(new InlineKeyboardButton().builder().text("Алгоритмы Искусственного Интеллекта").
                    callbackData("8").build());
            keyboard.add(row3);

            List<InlineKeyboardButton> row4 = new ArrayList<>();
            row4.add(new InlineKeyboardButton().builder().text("Безопасность Компьютерных Систем").
                    callbackData("9").build());
            keyboard.add(row4);

            List<InlineKeyboardButton> row5 = new ArrayList<>();
            row5.add(new InlineKeyboardButton().builder().text("Радиотехника").callbackData("10").build());
            keyboard.add(row5);

            markup.setKeyboard(keyboard);
            message.setReplyMarkup(markup);
        }
        if (data.equals("ХТИ")) {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

            List<InlineKeyboardButton> row1 = new ArrayList<>();
            row1.add(new InlineKeyboardButton().builder().text("Биотехнология").callbackData("11").build());
            keyboard.add(row1);

            List<InlineKeyboardButton> row2 = new ArrayList<>();
            row2.add(new InlineKeyboardButton().builder().text("Химическая технология веществ").
                    callbackData("12").build());
            keyboard.add(row2);

            List<InlineKeyboardButton> row3 = new ArrayList<>();
            row3.add(new InlineKeyboardButton().builder().text("Фармация").callbackData("13").build());
            keyboard.add(row3);

            markup.setKeyboard(keyboard);
            message.setReplyMarkup(markup);

        }
        if (data.equals("/testAbit")) {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<InlineKeyboardButton> row = new ArrayList<>();

            row.add(new InlineKeyboardButton().builder().text("Математика").callbackData("300").build());
            row.add(new InlineKeyboardButton().builder().text("Информатика").callbackData("200").build());
            row.add(new InlineKeyboardButton().builder().text("Физика").callbackData("100").build());

            List keyboard = new ArrayList<>();
            keyboard.add(row);

            markup.setKeyboard(keyboard);
            message.setReplyMarkup(markup);
        }
        return message;
    }
    /**
     * Метод для создания клавиатуры для тестирования ABI.
     *
     * @param message         Сообщение, к которому добавляется клавиатура.
     * @param list_with_dataBD Список данных для настройки клавиатуры.
     * @return Изменённое сообщение с установленной клавиатурой.
     */
    public SendMessage keyboardforTestABI(SendMessage message, List<String> list_with_dataBD ){
        if (list_with_dataBD.get(3).equals("-"))
        {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List<InlineKeyboardButton> row = new ArrayList<>();


            row.add(new InlineKeyboardButton().builder().text(list_with_dataBD.get(1)).
                    callbackData(list_with_dataBD.get(4)).build());
            row.add(new InlineKeyboardButton().builder().text(list_with_dataBD.get(2)).
                    callbackData(list_with_dataBD.get(5)).build());

            List keyboard = new ArrayList<>();
            keyboard.add(row);

            markup.setKeyboard(keyboard);
            message.setReplyMarkup(markup);

        }
        else{
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            List keyboard = new ArrayList<>();
            List<InlineKeyboardButton> row = new ArrayList<>();

            row.add(new InlineKeyboardButton().builder().text(list_with_dataBD.get(1)).
                    callbackData(list_with_dataBD.get(4)).build());


            row.add(new InlineKeyboardButton().builder().text(list_with_dataBD.get(2)).
                    callbackData(list_with_dataBD.get(5)).build());


            row.add(new InlineKeyboardButton().builder().text(list_with_dataBD.get(3)).
                    callbackData("-").build());
            keyboard.add(row);

            markup.setKeyboard(keyboard);
            message.setReplyMarkup(markup);
        }
        return message;
    }
}

