package io.proj3ct.ReturnBot1.Command;

import io.proj3ct.ReturnBot1.keybords.LogicForNewDepartmentData;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для создания нового Department
 */
public class NewDepartmentCommand implements Command{
    private final LogicForNewDepartmentData logicForNewDepartmentData= new LogicForNewDepartmentData();
    /**
     * Выполняет создание нового отдела.
     *
     * @param userId Идентификатор пользователя.
     * @param messageText Текст команды.
     * @param flagForKeyboard Флаг для отображения клавиатуры.
     * @return Список ответов на команду.
     */
    public List<String> execute(long userId, String messageText, boolean flagForKeyboard) {
        List<String> response = new ArrayList<>();
        if ("/newDepartmentData".equals(messageText) ||
                (!logicForNewDepartmentData.getUserStatesForNewDepartment(userId).equals("0"))) {
            response.add(logicForNewDepartmentData.worksWithNewDepartment(messageText, userId));
        }
        return response;
    }
}
