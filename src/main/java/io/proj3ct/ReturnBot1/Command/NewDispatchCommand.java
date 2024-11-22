package io.proj3ct.ReturnBot1.Command;

import io.proj3ct.ReturnBot1.dispatch.LogicAndDataForDispatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для обработки новых диспетчеризаций.
 * Реализует интерфейс Command и обрабатывает команду "/new_dispatch".
 */
public class NewDispatchCommand implements Command {
    private final LogicAndDataForDispatch logicAndDataForDispatch = new LogicAndDataForDispatch();
    /**
     * Выполняет команду для обработки новых диспетчеризаций.
     *
     * @param userId Идентификатор пользователя, который отправил команду.
     * @param messageText Текст сообщения пользователя.
     * @param flagForKeyboard Флаг, указывающий, требуется ли отображение клавиатуры.
     * @return Список строк, содержащий ответ на команду.
     */
    @Override
    public List<String> execute(long userId, String messageText, boolean flagForKeyboard) {
        List<String> response = new ArrayList<>();
        if ("/new_dispatch".equals(messageText) ||
                (!logicAndDataForDispatch.getUserStatesForNewDispatch(userId).equals("0"))) {
            response.add(logicAndDataForDispatch.worksWithNewDispatch(messageText, userId));
        }
        return response;
    }
}
