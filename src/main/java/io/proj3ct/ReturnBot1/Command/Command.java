package io.proj3ct.ReturnBot1.Command;

import java.util.List;
/**
 * Интерфейс для выполнения команд, обрабатывающих сообщения пользователей.
 */
public interface Command {
    /**
     * Выполняет команду и возвращает список строк с результатами.
     *
     * @param userId ID пользователя, отправившего сообщение
     * @param messageText текст сообщения
     * @param flagForKeyboard флаг для отображения клавиатуры
     * @return Список строк с результатами команды
     */
    List<String> execute(long userId, String messageText, boolean flagForKeyboard);
}
