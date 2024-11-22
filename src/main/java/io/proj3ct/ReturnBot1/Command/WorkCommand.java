package io.proj3ct.ReturnBot1.Command;

import io.proj3ct.ReturnBot1.MessageConstants;

import java.util.ArrayList;
import java.util.List;
/**
 * Команда для обработки запроса команды /work
 */
public class WorkCommand implements Command{
    /**
     * Выполняет команду для получения информации.
     *
     * @param userId ID пользователя, отправившего сообщение
     * @param messageText текст сообщения
     * @param flagForKeyboard флаг для отображения клавиатуры
     * @return Список строк с ответом на запрос
     */
    public List<String> execute(long userId, String messageText, boolean flagForKeyboard) {
        List<String> response = new ArrayList<>();
        if ("/work".equals(messageText)) {
            response.add(MessageConstants.WORK_COMMAND_RESPONSE);
            response.add(messageText);
        }
        return response;
    }
}
