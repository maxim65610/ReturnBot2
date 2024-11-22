package io.proj3ct.ReturnBot1.Command;

import io.proj3ct.ReturnBot1.LogicForRegistrationUsers;
import io.proj3ct.ReturnBot1.MessageConstants;
import io.proj3ct.ReturnBot1.UsersData;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для удаления данных пользователя.
 */
public class UserDataDeleteCommand implements Command {
    private final UsersData usersData= new UsersData();
    private final LogicForRegistrationUsers logicForRegistrationUsers = new LogicForRegistrationUsers();
    /**
     * Выполняет команду для удаления данных пользователя.
     *
     * @param userId ID пользователя, отправившего сообщение
     * @param messageText текст сообщения
     * @param flagForKeyboard флаг для отображения клавиатуры
     * @return Список строк с результатом удаления данных
     */
    @Override
    public List<String> execute(long userId, String messageText, boolean flagForKeyboard) {
        List<String> response = new ArrayList<>();
        if ("/userDataDell".equals(messageText)) {
            usersData.deleteData(userId, logicForRegistrationUsers.getDatabaseConnection());
            response.add(MessageConstants.DATA_DELETED);
        }
        return response;
    }
}