package io.proj3ct.ReturnBot1.Command;

import io.proj3ct.ReturnBot1.dispatch.LogicForOnOffDispatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для включения диспетчеризации.
 * Реализует интерфейс Command и обрабатывает команду "/dispatch_on".
 */
public class DispatchOnCommand implements Command {
    private final LogicForOnOffDispatch logicForOnOffDispatch = new LogicForOnOffDispatch();
    /**
     * Выполняет команду включения диспетчеризации.
     *
     * @param userId Идентификатор пользователя, который отправил команду.
     * @param messageText Текст сообщения пользователя.
     * @param flagForKeyboard Флаг, указывающий, требуется ли отображение клавиатуры.
     * @return Список строк, содержащий ответ на команду.
     */
    @Override
    public List<String> execute(long userId, String messageText, boolean flagForKeyboard) {
        List<String> response = new ArrayList<>();
        if ("/dispatch_on".equals(messageText)) {
            response.add(logicForOnOffDispatch.dispatchOn(userId));
        }
        return response;
    }
}
