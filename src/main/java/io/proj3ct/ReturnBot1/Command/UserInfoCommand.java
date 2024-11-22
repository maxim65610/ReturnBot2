package io.proj3ct.ReturnBot1.Command;

import io.proj3ct.ReturnBot1.DatabaseConnection;
import io.proj3ct.ReturnBot1.MessageConstants;
import io.proj3ct.ReturnBot1.UsersData;

import java.util.ArrayList;
import java.util.List;
/**
 * Команда для получения информации о пользователе.
 */
public class UserInfoCommand implements Command {
    private final UsersData usersData = new UsersData();
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    /**
     * Выполняет команду для получения данных о пользователе.
     *
     * @param userId ID пользователя, отправившего сообщение
     * @param messageText текст сообщения
     * @param flagForKeyboard флаг для отображения клавиатуры
     * @return Список строк с информацией о пользователе или сообщением об отсутствии данных
     */
    @Override
    public List<String> execute(long userId, String messageText, boolean flagForKeyboard) {
        List<String> response = new ArrayList<>();
        if ("/userinfo".equals(messageText)) {
            if (usersData.checkUserIdExistsInRegistrationDataTable(userId, databaseConnection)) {
                response.add(usersData.getRegistrationData(userId, databaseConnection));
            } else {
                response.add(MessageConstants.NO_REGISTRATION);
            }
        }
        return response;
    }
}
