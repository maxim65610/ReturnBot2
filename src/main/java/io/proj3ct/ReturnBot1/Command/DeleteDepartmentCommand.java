package io.proj3ct.ReturnBot1.Command;

import io.proj3ct.ReturnBot1.keybords.LogicForDeleteDepartment;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для удаления department
 */
public class DeleteDepartmentCommand implements Command{
    private final LogicForDeleteDepartment logicForDeleteDepartment = new LogicForDeleteDepartment();
    /**
     * Выполняет удаление отдела.
     *
     * @param userId Идентификатор пользователя.
     * @param messageText Текст команды.
     * @param flagForKeyboard Флаг для отображения клавиатуры.
     * @return Список ответов на команду.
     */
    public List<String> execute(long userId, String messageText, boolean flagForKeyboard) {
        List<String> response = new ArrayList<>();
        if ("/delete_department_data".equals(messageText) ||
                (!logicForDeleteDepartment.getUserStatesForDeleteDepartment(userId).equals("0"))) {
            response.add(logicForDeleteDepartment.worksWithDeleteDepartment(messageText, userId));
        }
        return response;
    }
}
