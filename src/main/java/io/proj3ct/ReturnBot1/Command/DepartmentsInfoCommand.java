package io.proj3ct.ReturnBot1.Command;

import io.proj3ct.ReturnBot1.DepartmentsInfo;
import io.proj3ct.ReturnBot1.MessageConstants;

import java.util.ArrayList;
import java.util.List;
/**
 * Команда для получения информации о департаменте на основе сообщения пользователя.
 */
public class DepartmentsInfoCommand implements Command{
    private final DepartmentsInfo departmentsInfo = new DepartmentsInfo();
    /**
     * Проверяет, какую информацию вернуть в зависимости от названия департамента.
     *
     * @param data название департамента
     * @return строка с информацией о департаменте или исходное название
     */
    private String checkWhatTodo(String data) {
        return switch (data) {
            case "ИЕНИМ" -> MessageConstants.INST_IENIM_COMMAND_RESPONSE;
            case "РТФ" -> MessageConstants.INST_RTF_COMMAND_RESPONSE;
            case "ХТИ" -> MessageConstants.INST_CHTI_COMMAND_RESPONSE;
            default -> data;
        };
    }
    /**
     * Выполняет команду для получения информации о департаменте.
     *
     * @param userId ID пользователя, отправившего сообщение
     * @param messageText текст сообщения с названием департамента
     * @param flagForKeyboard флаг для отображения клавиатуры
     * @return Список строк с информацией о департаменте или оригинальным названием
     */
    public List<String> execute(long userId, String messageText, boolean flagForKeyboard){
        List<String> response = new ArrayList<>();
        if(flagForKeyboard){
            if (departmentsInfo.extract(messageText) == null){
                response.add(checkWhatTodo(messageText));
                response.add(messageText);
            }
            else{
                response.add(departmentsInfo.extract(messageText));
                response.add(messageText);
            }
        }
        return response;
    }
}
