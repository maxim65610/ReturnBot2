package io.proj3ct.ReturnBot1.Command;

import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;

import java.util.ArrayList;
import java.util.List;
/**
 * Реализация команды по умолчанию, которая возвращает стандартный ответ.
 */
public class DefaultCommand implements Command {
    /**
     * Выполняет команду и возвращает стандартный ответ с текстом сообщения.
     *
     * @param userId ID пользователя, отправившего сообщение
     * @param messageText текст сообщения
     * @param flagForKeyboard флаг для отображения клавиатуры
     * @return Список строк с ответом по умолчанию и исходным текстом сообщения
     */
    @Override
    public List<String> execute(long userId, String messageText, boolean flagForKeyboard) {
        List<String> response = new ArrayList<>();
        response.add(MessageConstants.DEFAULT_RESPONSE);
        response.add(messageText);
        return response;
    }
}
