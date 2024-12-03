package io.proj3ct.ReturnBot1.Command;

import io.proj3ct.ReturnBot1.keybords.LogicForEditDepartmentData;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для редактирования данных Department
 */
public class EditDepartmentCommand implements Command{
    private final LogicForEditDepartmentData logicForEditDepartmentData = new LogicForEditDepartmentData();
    /**
     * Выполняет редактирование данных отдела.
     *
     * @param userId Идентификатор пользователя.
     * @param messageText Текст команды.
     * @param flagForKeyboard Флаг для отображения клавиатуры.
     * @return Список ответов на команду.
     */
    public List<String> execute(long userId, String messageText, boolean flagForKeyboard) {
        List<String> response = new ArrayList<>();
        if ("/editDepartmentData".equals(messageText) ||
                (!logicForEditDepartmentData.getUserStatesForEditDepartment(userId).equals("0"))) {
            response.add(logicForEditDepartmentData.worksWithEditDepartment(messageText, userId));
        }
        return response;
    }
}

