package io.proj3ct.ReturnBot1.Command;

import io.proj3ct.ReturnBot1.mail.EmailSender;
import io.proj3ct.ReturnBot1.registration.LogicForChangeDataUsers;

import java.util.ArrayList;
import java.util.List;
/**
 * Команда для обработки изменения данных пользователя.
 */
public class UserDataChangeCommand implements Command {
    private final LogicForChangeDataUsers logicForChangeDataUsers = new LogicForChangeDataUsers();
    private final EmailSender emailSender;
    /**
     * Конструктор для инициализации команды с отправителем электронной почты.
     *
     * @param emailSender отправитель электронной почты
     */
    public UserDataChangeCommand(EmailSender emailSender) {
        this.emailSender = emailSender;
    }
    /**
     * Выполняет команду для изменения данных пользователя.
     *
     * @param userId ID пользователя, отправившего сообщение
     * @param messageText текст сообщения
     * @param flagForKeyboard флаг для отображения клавиатуры
     * @return Список строк с результатом обработки запроса на изменение данных
     */
    @Override
    public List<String> execute(long userId, String messageText, boolean flagForKeyboard) {
        List<String> response = new ArrayList<>();
        if ("/user_data_change".equals(messageText) ||
                !logicForChangeDataUsers.getUserStatesForChangeData(userId).equals("0")) {
            response.add(logicForChangeDataUsers.worksWithChangeData(messageText, userId, emailSender));

        }
        return response;
    }
}
