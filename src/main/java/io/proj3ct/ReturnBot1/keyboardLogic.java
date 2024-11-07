package io.proj3ct.ReturnBot1;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.List;

/**
 * Контролирует выбор клавиатур
 */
public class keyboardLogic {
    /**
     * Обрабатывает сообщение и выбирает клавиатуру, отправляющуюся вместе с сообщением
     */
    public SendMessage keyboards(SendMessage message, String data) {

        if (data.equals("/work")) {
            InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                    .builder()
                    .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                            .builder()
                            .text("Математика, Механика и Компьютерные Науки")
                            .callbackData("ИЕНИМ")
                            .build())
                    )
                    .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                            .builder()
                            .text("Радиоэлектроника и Информационные Технологии")
                            .callbackData("РТФ")
                            .build())
                    )
                    .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                            .builder()
                            .text("Химико Технический")
                            .callbackData("ХТИ")
                            .build())
                    )
                    .build();
            message.setReplyMarkup(keyboard);
        }
        if (data.equals("ИЕНИМ")) {
            InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                    .builder()
                    .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                            .builder()
                            .text("Компьютерные Науки")
                            .callbackData("1")
                            .build())
                    )
                    .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                            .builder()
                            .text("Администрирование Информационных Систем")
                            .callbackData("2")
                            .build())
                    )
                    .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                            .builder()
                            .text("Фундаментальная Информатика")
                            .callbackData("3")
                            .build())
                    )
                    .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                            .builder()
                            .text("Математика и механика")
                            .callbackData("4")
                            .build())
                    )
                    .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                            .builder()
                            .text("Компьютерная Безопасность")
                            .callbackData("5")
                            .build())
                    )
                    .build();
            message.setReplyMarkup(keyboard);
        }
        if (data.equals("РТФ")) {
            InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                    .builder()
                    .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                            .builder()
                            .text("Информатика и Вычислительная Техника")
                            .callbackData("6")
                            .build())
                    )
                    .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                            .builder()
                            .text("Прикладная Информатика")
                            .callbackData("7")
                            .build())
                    )
                    .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                            .builder()
                            .text("Алгоритмы Искусственного Интеллекта")
                            .callbackData("8")
                            .build())
                    )
                    .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                            .builder()
                            .text("Безопасность Компьютерных Систем")
                            .callbackData("9")
                            .build())
                    )
                    .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                            .builder()
                            .text("Радиотехника")
                            .callbackData("10")
                            .build())
                    )
                    .build();
            message.setReplyMarkup(keyboard);
        }
        if (data.equals("ХТИ")) {
            InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                    .builder()
                    .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                            .builder()
                            .text("Биотехнология")
                            .callbackData("11")
                            .build())
                    )
                    .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                            .builder()
                            .text("Химическая технология веществ")
                            .callbackData("12")
                            .build())
                    )
                    .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                            .builder()
                            .text("Фармация")
                            .callbackData("13")
                            .build())
                    )
                    .build();
            message.setReplyMarkup(keyboard);
        }
        if (data.equals("/testAbit")) {
            InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                    .builder()
                    .keyboardRow(new InlineKeyboardRow(
                            InlineKeyboardButton
                                    .builder()
                                    .text("Математика")
                                    .callbackData("300")
                                    .build(),
                            InlineKeyboardButton
                                    .builder()
                                    .text("Информатика")
                                    .callbackData("200")
                                    .build(),
                            InlineKeyboardButton
                                    .builder()
                                    .text("Физика")
                                    .callbackData("100")
                                    .build())
                    )
                    .build();
            message.setReplyMarkup(keyboard);
        }
        return message;
    }
    /**
     * Создает клавиатуры для тестирования ABI.
     * @param message Сообщение, к которому добавляется клавиатура.
     * @param list_with_dataBD Список данных для настройки клавиатуры.
     * @return Изменённое сообщение с установленной клавиатурой.
     */
    public SendMessage keyboardforTestABI(SendMessage message, List<String> list_with_dataBD) {
        if (list_with_dataBD.get(3).equals("-")) {
            InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                    .builder()
                    .keyboardRow(new InlineKeyboardRow(
                            InlineKeyboardButton
                                    .builder()
                                    .text(list_with_dataBD.get(1))
                                    .callbackData(list_with_dataBD.get(4))
                                    .build(),
                            InlineKeyboardButton
                                    .builder()
                                    .text(list_with_dataBD.get(2))
                                    .callbackData(list_with_dataBD.get(5))
                                    .build())
                    )
                    .build();
            message.setReplyMarkup(keyboard);
            return message;
        } else {
            InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                    .builder()
                    .keyboardRow(new InlineKeyboardRow(
                            InlineKeyboardButton
                                    .builder()
                                    .text(list_with_dataBD.get(1))
                                    .callbackData(list_with_dataBD.get(4))
                                    .build(),
                            InlineKeyboardButton
                                    .builder()
                                    .text(list_with_dataBD.get(2))
                                    .callbackData(list_with_dataBD.get(5))
                                    .build(),
                            InlineKeyboardButton
                                    .builder()
                                    .text(list_with_dataBD.get(3))
                                    .callbackData("-")
                                    .build())
                    )
                    .build();
            message.setReplyMarkup(keyboard);
            return message;
        }
    }
}
