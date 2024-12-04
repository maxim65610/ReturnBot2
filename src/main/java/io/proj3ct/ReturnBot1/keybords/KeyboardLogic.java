package io.proj3ct.ReturnBot1.keybords;

import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.List;

/**
 * Контролирует выбор клавиатур
 */
public class KeyboardLogic {
    private final DepartmentsInfo departmentsInfo = new DepartmentsInfo();
    /**
     * Обрабатывает сообщение и выбирает соответствующую клавиатуру, которая будет отправлена вместе с сообщением.
     * В зависимости от переданных данных метод генерирует одну из нескольких клавиатур с кнопками.
     *
     * @param message Сообщение, к которому добавляется клавиатура.
     * @param data Данные, которые определяют, какая клавиатура будет сгенерирована.
     * @return Изменённое сообщение с установленной клавиатурой.
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
            int numberOfButtons = departmentsInfo.countOfInst("ИЕНИМ");
            String[] names = departmentsInfo.getNames("ИЕНИМ");
            String[] ids = departmentsInfo.getIdDeparts("ИЕНИМ");

            InlineKeyboardMarkup.InlineKeyboardMarkupBuilder<?, ?> keyboardBuilder = InlineKeyboardMarkup.builder();
            for (int i = 0; i < numberOfButtons; i++) {
                InlineKeyboardRow row = new InlineKeyboardRow(InlineKeyboardButton
                        .builder()
                        .text(String.valueOf(names[i]))
                        .callbackData(String.valueOf(ids[i]))
                        .build());
                keyboardBuilder.keyboardRow(row);
            }
            InlineKeyboardMarkup keyboard = keyboardBuilder.build();
            message.setReplyMarkup(keyboard);
        }
        if (data.equals("РТФ")) {
            int numberOfButtons = departmentsInfo.countOfInst("РТФ");
            String[] names = departmentsInfo.getNames("РТФ");
            String[] ids = departmentsInfo.getIdDeparts("РТФ");
            InlineKeyboardMarkup.InlineKeyboardMarkupBuilder<?, ?> keyboardBuilder = InlineKeyboardMarkup.builder();
            for (int i = 0; i < numberOfButtons; i++) {
                InlineKeyboardRow row = new InlineKeyboardRow(InlineKeyboardButton
                        .builder()
                        .text(String.valueOf(names[i]))
                        .callbackData(String.valueOf(ids[i]))
                        .build());
                keyboardBuilder.keyboardRow(row);
            }
            InlineKeyboardMarkup keyboard = keyboardBuilder.build();
            message.setReplyMarkup(keyboard);
        }
        if (data.equals("ХТИ")) {
            int numberOfButtons = departmentsInfo.countOfInst("ХТИ");
            String[] names = departmentsInfo.getNames("ХТИ");
            String[] ids = departmentsInfo.getIdDeparts("ХТИ");

            InlineKeyboardMarkup.InlineKeyboardMarkupBuilder<?, ?> keyboardBuilder = InlineKeyboardMarkup.builder();
            for (int i = 0; i < numberOfButtons; i++) {
                InlineKeyboardRow row = new InlineKeyboardRow(InlineKeyboardButton
                        .builder()
                        .text(String.valueOf(names[i]))
                        .callbackData(String.valueOf(ids[i]))
                        .build());
                keyboardBuilder.keyboardRow(row);
            }
            InlineKeyboardMarkup keyboard = keyboardBuilder.build();
            message.setReplyMarkup(keyboard);
        }
        if (data.equals("/test_abit")) {
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
